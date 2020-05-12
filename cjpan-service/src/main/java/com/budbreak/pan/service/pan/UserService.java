package com.budbreak.pan.service.pan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.budbreak.pan.common.InvokeResult;
import com.budbreak.pan.entity.pan.User;
import com.budbreak.pan.vo.pan.UserVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @Description: TODO
* @author:
* @Createed Date: 2018/11/21-10:26
* @ModificationHistory: Who  When  What
* ---------     -------------   --------------------------------------
**/
public interface UserService extends IService<User> {

    /**
     * add
     * @param entity
     */
    void addEntity(User entity);

     /**
     * updateEntity
      * @param entity
      * @param request
      */
    InvokeResult updateEntity(User entity, HttpServletRequest request);

    /**
     * 登陆操作
     *
     *
     * @param response
     * @param userName
     * @param password
     * @return
     */
    InvokeResult login(HttpServletResponse response, String userName, String password);

    /**
     * 通过用户名查询用户
     * @param userName
     * @return
     */
    UserVO selectUserByUserName(String userName);

    /**
     * 注册登陆
     * @param user 用户信息
     * @param registerCode 邀请码
     * @return
     */
    InvokeResult register(User user, String registerCode);

    /**
     * 退出登陆
     * @param request
     * @param response
     */
    InvokeResult loginOut(HttpServletRequest request, HttpServletResponse response);

    /**
     * 重置密码，重置密码发送至用户邮箱
     * @param id
     * @return
     */
    InvokeResult alterPassword(Integer id);

    /**
     * 发送修改密码邮件验证码
     * @param email
     * @param userName
     * @return
     */
    InvokeResult sendEmail(String email, String userName);

    /**
     * 重置密码
     * @param captcha
     * @return
     */
    InvokeResult forget(User user, String captcha);
}
