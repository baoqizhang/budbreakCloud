package com.budbreak.pan.service.pan.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.budbreak.pan.common.*;
import com.budbreak.pan.entity.pan.MailSendConfig;
import com.budbreak.pan.entity.pan.User;
import com.budbreak.pan.mapper.pan.UserMapper;
import com.budbreak.pan.mapper.verify.CodeMapper;
import com.budbreak.pan.service.WebUtil;
import com.budbreak.pan.service.pan.UserService;
import com.budbreak.pan.service.pan.shiro.JwtUtils;
import com.budbreak.pan.vo.pan.UserVO;
import com.budbreak.pan.vo.verify.CodeVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

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
    public String fileRootPath;

    @Value("${mail.exchange.name}")
    private String mailExchange;

    @Value("${mail.routing.key.name}")
    private String mailRouting;

    @Autowired
    CodeMapper codeMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public InvokeResult login(HttpServletResponse response, String userName, String password) {
        Subject subject = SecurityUtils.getSubject();
        try {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, password);
            subject.login(usernamePasswordToken);
            UserVO user = (UserVO) subject.getPrincipal();
            String token = JwtUtils.sign(user.getUsername(), user.getAlias(), 3600);
            CookieUtils.addCookie("x-auth-token", token);
            return InvokeResult.success(token);
        } catch (AuthenticationException e) {
            return InvokeResult.failure(20001, "用户名或密码错误!");
        } catch (Exception e) {
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
    public InvokeResult updateEntity(User user, HttpServletRequest request) {
        //查询当前用户是否为登陆用户
        String userName = WebUtil.getUserNameByRequest(request);
        User selectUser = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, userName)
                .eq(User::getEmail, user.getEmail()));
        if (null == selectUser) {
            return InvokeResult.failure("修改密码失败！");
        } else {
            PasswordHelper.encryptPassword(user);
            userMapper.update(null, new LambdaUpdateWrapper<User>()
                    .eq(User::getUsername, selectUser.getUsername())
                    .eq(User::getEmail, selectUser.getEmail())
                    .set(User::getPassword, user.getPassword())
                    .set(User::getAlias, user.getAlias()));
            return InvokeResult.success();
        }
    }

    @Override
    public InvokeResult register(User user, String registerCode) {
        UserVO userVO = userMapper.selectUserByUserName(user.getUsername());
        if (userVO != null) {
            return InvokeResult.failure("用户名已存在，请重新输入");
        }

        //查询注册码是否存在
        CodeVO codeVO = codeMapper.selectByRegisterCode(registerCode, user.getUsername());
        if (codeVO == null) {
            return InvokeResult.failure("邀请码不正确，请重新输入");
        }

        user.setLevel("0");
        //加密的用户密码
        PasswordHelper.encryptPassword(user);
        userMapper.insert(user);
        //注册成功发送邮件
        MailSendConfig mailSendConfig = new MailSendConfig();
        mailSendConfig.setTopic("云网盘注册成功通知")
                .setContent("<p>尊敬的用户" + user.getUsername() + "！恭喜您注册成功！<p>")
                .setSendMail(user.getEmail())
                .setHtml(true);
        try {
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(mailExchange);
            rabbitTemplate.setRoutingKey(mailRouting);
            rabbitTemplate.convertAndSend(mailSendConfig, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    MessageProperties messageProperties = message.getMessageProperties();
                    messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, MailSendConfig.class);
                    return message;
                }
            });
            return InvokeResult.success();
        } catch (Exception e) {
            return InvokeResult.failure("发送邮件失败！");
        }
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
        User user = new User();
        user.setPassword(passWord);
        PasswordHelper.encryptPassword(user);
        userMapper.update(null, new LambdaUpdateWrapper<User>()
                .eq(User::getId, id)
                .set(User::getPassword, user.getPassword())
                .set(User::getAlias, user.getAlias()));
        UserVO userVO = userMapper.selectDetailById(id);
        //注册成功发送邮件
        MailSendConfig mailSendConfig = new MailSendConfig();
        mailSendConfig.setTopic("云网盘密码重置通知")
                .setContent("<p>尊敬的用户" + userVO.getUsername() + ",恭喜您密码重置成功! 您的密码为:<a>" + passWord + "</a></p>")
                .setSendMail(userVO.getEmail())
                .setHtml(true);
        try {
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(mailExchange);
            rabbitTemplate.setRoutingKey(mailRouting);
            rabbitTemplate.convertAndSend(mailSendConfig, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    MessageProperties messageProperties = message.getMessageProperties();
                    messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, MailSendConfig.class);
                    return message;
                }
            });
            return InvokeResult.success();
        } catch (Exception e) {
            return InvokeResult.failure("发送邮件失败！");
        }
    }

    @Override
    public InvokeResult sendEmail(String email, String userName) {
        //验证userName和email是否一致
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, userName)
                .eq(User::getEmail, email));
        if (null == user) {
            return InvokeResult.failure("用户名和邮箱不一致！");
        } else {
            String captcha = PassWordCreate.createPassWord(6);
            //redis保存验证码 userName_email_captcha
            //防刷校验: 每分钟仅能获取1次; 获取的验证码5分钟后过期; 每个邮箱每天仅能获取10次验证码
            String count = JedisUtils.getString(Constants.SMS_SEND_SUM + Constants.SPACE + email + "_" + userName);
            //单个邮箱不能发送10次以上的邮箱验证码
            if (!StringUtils.isEmpty(count) && Integer.parseInt(count) >= 10) {
                return InvokeResult.failure("该邮箱已超过验证码发送上限，请明天再尝试!");
            }
            //1分钟内不能连续发送多次
            String value = JedisUtils.getString(Constants.PHONE_SEND_PRE + Constants.SPACE + email + "_" + userName);
            if (!StringUtils.isEmpty(value)) {
                return InvokeResult.failure("您的邮箱验证码已下发，请耐心等待或1分钟后重试!");
            }
            //正常发送, 存储邮箱验证码过期时间和重发过期时间, 并记录当天已发送的次数
            JedisUtils.saveString(Constants.PHONE_SEND_PRE + Constants.SPACE + email + "_" + userName, captcha, 60);
            JedisUtils.saveString(Constants.PHONE_QUERY_PRE + Constants.SPACE + email + "_" + userName, captcha, 60 * 5);
            JedisUtils.incr(Constants.SMS_SEND_SUM + Constants.SPACE + email + "_" + userName);
            //发送上限每日凌晨清零
            JedisUtils.expire(Constants.SMS_SEND_SUM + Constants.SPACE + email + "_" + userName, getRemainSeconds());
            //发送修改密码邮件
            MailSendConfig mailSendConfig = new MailSendConfig();
            mailSendConfig.setTopic("云网盘密码重置获取验证码通知")
                    .setContent("<p>尊敬的用户" + userName + ",您的密码重置验证码为：<a>" + captcha + "</a>,五分钟后过期！</p>")
                    .setSendMail(email)
                    .setHtml(true);
            try {
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(mailExchange);
                rabbitTemplate.setRoutingKey(mailRouting);
                rabbitTemplate.convertAndSend(mailSendConfig, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        MessageProperties messageProperties = message.getMessageProperties();
                        messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, MailSendConfig.class);
                        return message;
                    }
                });
                return InvokeResult.success();
            } catch (Exception e) {
                return InvokeResult.failure("发送邮件失败！");
            }
        }
    }

    @Override
    public InvokeResult forget(User user, String captcha) {
        //验证验证码是否正确
        String redisCaptcha = JedisUtils.getString(Constants.PHONE_QUERY_PRE + Constants.SPACE + user.getEmail() + "_" + user.getUsername());
        if (captcha.equals(redisCaptcha)) {
            //重置密码
            User pwdUser = new User();
            pwdUser.setPassword(user.getPassword());
            PasswordHelper.encryptPassword(pwdUser);
            userMapper.update(null, new LambdaUpdateWrapper<User>()
                    .eq(User::getUsername, user.getUsername())
                    .eq(User::getEmail, user.getEmail())
                    .set(User::getPassword, pwdUser.getPassword())
                    .set(User::getAlias, pwdUser.getAlias()));
            return InvokeResult.success();
        } else {
            return InvokeResult.failure("验证码错误！");
        }
    }

    /**
     * 获取当前时间到0点的剩余时间
     *
     * @return
     */
    private int getRemainSeconds() {
        Calendar c = Calendar.getInstance();
        long now = c.getTimeInMillis();
        c.add(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        long mills = c.getTimeInMillis() - now;
        return (int) mills / 1000;
    }

}
