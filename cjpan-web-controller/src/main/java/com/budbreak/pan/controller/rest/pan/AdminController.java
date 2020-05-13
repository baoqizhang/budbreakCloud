package com.budbreak.pan.controller.rest.pan;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.budbreak.pan.common.InvokeResult;
import com.budbreak.pan.common.PageResult;
import com.budbreak.pan.common.PassWordCreate;
import com.budbreak.pan.entity.pan.User;
import com.budbreak.pan.entity.verify.Code;
import com.budbreak.pan.manager.pan.UserManager;
import com.budbreak.pan.mapper.pan.UserMapper;
import com.budbreak.pan.mapper.verify.CodeMapper;
import com.budbreak.pan.service.WebUtil;
import com.budbreak.pan.service.pan.UserService;
import com.budbreak.pan.service.verify.CodeService;
import com.budbreak.pan.vo.pan.UserVO;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 管理员维护的接口
 * Created by zc on 2018/11/26.
 */
@RestController
public class AdminController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private CodeService iVerifyCodeService;

    @Autowired
    private CodeMapper codeMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserManager userManager;


    @PutMapping("/alterPassword")
    @ApiOperation("重置密码")
    @RequiresPermissions("1")
    public InvokeResult alterSecret(@RequestParam("id") Integer id) {
        return userService.alterPassword(id);
    }

    /**
     * 根据用户名删除用户，可以直接访问
     */
    @DeleteMapping("/deleteUser")
    @ApiOperation("根据用户id删除用户")
    @RequiresPermissions("1")
    public InvokeResult deleteUser(@RequestParam Integer id) {
        userMapper.deleteById(id);
        return InvokeResult.success();
    }

    /**
     * 用户产生验证码的接口，只有特定用户可以访问
     */
    @GetMapping(value = "/registerCode")
    @RequiresPermissions("1")
    public ModelAndView registerCode(ModelAndView modelAndView, HttpServletRequest request) {
        String username = WebUtil.getUserNameByRequest((request));
        modelAndView.setViewName("registerCode");
        return modelAndView;
    }

    /**
     * 根据操作人的名字和要验证码人的名字来生成注册码
     *
     * @param customName
     * @param request
     * @return
     */
    @PostMapping(value = "proRegisterCode", produces = "application/json; charset=utf-8")
    @RequiresPermissions("1")
    public InvokeResult proRegisterCode(@RequestParam String customName, HttpServletRequest request) {
        if (customName == null && customName.equals("")) {
            return InvokeResult.failure("用户名不能为空！");
        }
        //查询该用户名是否存在，若存在提示已存在
        Code code = codeMapper.selectOne(new LambdaQueryWrapper<Code>().eq(Code::getCustomName, customName));
        if (code != null){
            return InvokeResult.failure("该用户已存在注册码，注册码为:" +code.getRegisterCode());
        }
        String registerCode = PassWordCreate.createPassWord(6);
        Code verifyCode = new Code();
        verifyCode.setState(false);
        verifyCode.setRegisterCode(registerCode);
        verifyCode.setOperatePerson(WebUtil.getUserNameByRequest(request));
        verifyCode.setDate(new Date());
        verifyCode.setCustomName(customName);
        boolean result = iVerifyCodeService.save(verifyCode);
        if (result) {
            return InvokeResult.success(registerCode);
        } else {
            return InvokeResult.failure("生成注册码失败，请重新操作！");
        }
    }

    @GetMapping("getUserList")
    @ApiOperation(value = "获取用户列表")
    @RequiresPermissions("1")
    public IPage<UserVO> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String searchWord) {
        PageResult page = new PageResult(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>(2, 1);
        map.put("searchWord", searchWord);
        return userManager.getPage(page, map);
    }

    @PutMapping("updateAuth")
    @ApiOperation("修改用户权限")
    @RequiresPermissions("1")
    public InvokeResult updateAuth(@RequestParam("id") Integer id){
        userMapper.update(null, new LambdaUpdateWrapper<User>()
                .eq(User::getId, id)
                .set(User::getLevel, "1"));
        return InvokeResult.success();
    }

}
