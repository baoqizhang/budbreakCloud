package com.budbreak.pan.controller.rest.pan;

import com.budbreak.pan.common.InvokeResult;
import com.budbreak.pan.common.PassWordCreate;
import com.budbreak.pan.entity.verify.Code;
import com.budbreak.pan.service.WebUtil;
import com.budbreak.pan.service.pan.UserService;
import com.budbreak.pan.service.verify.CodeService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


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

    /**
     * 用户修改密码的接口，可以直接访问
     *
     * @param userName userName
     * @param password password
     */
    @RequestMapping("/alterPassword")
    public void alterSecret(@RequestParam String userName, @RequestParam String password) {
//        userService.alterPassword(userName, password);
    }

    /**
     * 根据用户名删除用户，可以直接访问
     *
     * @param userName userName
     */
    @RequestMapping("/deleteUser")
    public void deleteUser(@RequestParam String[] userName) {
//        userService.deleteByUsernames(userName);
    }

    /**
     * 用户产生验证码的接口，只有特定用户可以访问
     */
    @RequestMapping(value = "/registerCode")
    @RequiresPermissions("1")
    public ModelAndView registerCode(ModelAndView modelAndView, HttpServletRequest request) {
        String username = WebUtil.getUserNameByRequest((request));
//        if ("sandeepin".equals(username) || "cflower".equals(username)) {
            modelAndView.setViewName("registerCode");
            return modelAndView;
//        } else {
//            modelAndView.setViewName("errorPage");
//            modelAndView.addObject("message", "没有权限生成验证码！");
//            return modelAndView;
//        }
    }

    /**
     * 根据操作人的名字和要验证码人的名字来生成注册码
     *
     * @param customName
     * @param request
     * @return
     */
    @RequestMapping(value = "proRegisterCode", produces = "application/json; charset=utf-8")
    @RequiresPermissions("1")
    public InvokeResult proRegisterCode(@RequestParam String customName, HttpServletRequest request) {
        if (customName == null && customName == "")
        {
            return InvokeResult.failure("用户名不能为空！");
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
}
