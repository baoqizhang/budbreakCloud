package com.budbreak.pan.service.pan.impl;

import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.budbreak.pan.common.CookieUtils;
import com.budbreak.pan.common.InvokeResult;
import com.budbreak.pan.common.PassWordCreate;
import com.budbreak.pan.common.PasswordHelper;
import com.budbreak.pan.entity.pan.User;
import com.budbreak.pan.mapper.pan.UserMapper;
import com.budbreak.pan.mapper.verify.CodeMapper;
import com.budbreak.pan.service.pan.UserService;
import com.budbreak.pan.service.pan.shiro.JwtUtils;
import com.budbreak.pan.vo.pan.UserVO;
import com.budbreak.pan.vo.verify.CodeVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: TODO
 * @author:
 * @Createed Date: 2018/11/21-10:26
 * @ModificationHistory: Who  When  What
 * ---------     -------------   --------------------------------------
 **/

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Value("${fileRootPath}")
    public  String fileRootPath;

    @Autowired
    CodeMapper codeMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public InvokeResult login(HttpServletResponse response, String userName, String password) {
        Subject subject = SecurityUtils.getSubject();
        try {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName,password);
            subject.login(usernamePasswordToken);
            UserVO user = (UserVO) subject.getPrincipal();
            String token = JwtUtils.sign(user.getUsername(), user.getAlias(), 3600);
            CookieUtils.addCookie("x-auth-token", token);
            return InvokeResult.success(token);
        }catch (AuthenticationException e){
            return InvokeResult.failure(20001,"用户名或密码错误!");
        }catch (Exception e){
            return InvokeResult.error();
        }
    }

    @Override
    public UserVO selectUserByUserName(String userName) {
        return userMapper.selectUserByUserName(userName);
    }


    @Override
    public void addEntity(User entity) {
        userMapper.insert(entity);
    }

    @Override
    public void updateEntity(User entity) {
        userMapper.updateById(entity);
    }

    @Override
    public InvokeResult register(User user, String registerCode) {
        UserVO userVO = userMapper.selectUserByUserName(user.getUsername());
        if (userVO != null){
            return InvokeResult.failure("用户名已存在，请重新输入");
        }

        //查询注册码是否存在
        CodeVO codeVO = codeMapper.selectByRegisterCode(registerCode, user.getUsername());
        if (codeVO == null){
            return InvokeResult.failure("邀请码不正确，请重新输入");
        }

        user.setLevel("0");
        //加密的用户密码
        PasswordHelper.encryptPassword(user);
        userMapper.insert(user);
        //注册成功发送邮件
        MailUtil.send(user.getEmail(),
                "云网盘注册成功通知",
                "<p>尊敬的用户" + user.getUsername() + "！恭喜您注册成功！<p>",
                true);
        return InvokeResult.success();
    }

    @Override
    public InvokeResult loginOut(HttpServletRequest request, HttpServletResponse response) {
//        if (!isWindows()) {
//            // 非windows环境下要删除用户文件
//            FileUtil.deleteDir(fileRootPath + WebUtil.getUserNameByRequest(request));
//        }
        // 清除cookie
        CookieUtils.removeCookie("token");
        CookieUtils.removeCookie("x-auth-token");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return InvokeResult.success();
    }

    @Override
    public InvokeResult alterPassword(Integer id) {
        String passWord = PassWordCreate.createPassWord(12);
        User user =new User();
        user.setPassword(passWord);
        PasswordHelper.encryptPassword(user);
        userMapper.update(null, new LambdaUpdateWrapper<User>()
                .eq(User::getId, id)
                .set(User::getPassword, user.getPassword())
                .set(User::getAlias, user.getAlias()));
        UserVO userVO = userMapper.selectDetailById(id);
        //注册成功发送邮件
        MailUtil.send(userVO.getEmail(),
                "云网盘密码重置通知",
                "<p>尊敬的用户" + userVO.getUsername() + ",恭喜您密码重置成功! 您的密码为:<a>"+ passWord +"</a></p>",
                true);
        return InvokeResult.success();
    }

}
