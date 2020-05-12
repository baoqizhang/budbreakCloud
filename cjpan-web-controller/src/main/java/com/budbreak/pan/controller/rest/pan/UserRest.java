package com.budbreak.pan.controller.rest.pan;

import com.budbreak.pan.common.CommonDTO;
import com.budbreak.pan.common.InvokeResult;
import com.budbreak.pan.controller.assembler.pan.UserAssembler;
import com.budbreak.pan.controller.command.pan.UserCreateCommand;
import com.budbreak.pan.controller.command.pan.UserUpdateCommand;
import com.budbreak.pan.entity.pan.User;
import com.budbreak.pan.manager.pan.UserManager;
import com.budbreak.pan.service.pan.UserService;
import com.budbreak.pan.vo.pan.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;

/**
 * @Description: 代码生成器自动生成
 * @author:
 * @Createed Date: 2020-4-24 14:46:16
 * @ModificationHistory: Who  When  What
 * ---------     -------------   --------------------------------------
 **/
@RestController
@RequestMapping("api/v1/pan/user")
@Api(value = "userService", tags = "用户相关API")
public class UserRest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserManager userManager;


    @ApiOperation("登陆")
    @PostMapping("/login")
    public InvokeResult login(HttpServletResponse response, String userName, String password) {
        return userService.login(response, userName,password);
    }

    @ApiOperation("退出")
    @GetMapping("/quit")
    public InvokeResult loginOut(HttpServletRequest request, HttpServletResponse response) {
        return userService.loginOut(request, response);
    }

    @ApiOperation("注册")
    @PostMapping("register")
    public InvokeResult register(@RequestBody @Valid UserCreateCommand command) {
        User entity = UserAssembler.toUser(command);
        return userService.register(entity, command.getRegcode());
    }

    @ApiOperation("忘记密码验证码发送邮件")
    @GetMapping("sendEmail")
    public InvokeResult sendEmail(String email,String userName){
        return userService.sendEmail(email, userName);
    }

    @ApiOperation("忘记密码")
    @PutMapping("forget")
    public InvokeResult forget(@RequestBody UserUpdateCommand command){
        User user = UserAssembler.toUser(command);
        return userService.forget(user,command.getCaptcha());
    }

    @DeleteMapping("delete")
    @ApiOperation(value = "delect User by id")
    public InvokeResult delete(@RequestBody CommonDTO commonDTO) {
        userService.removeByIds(Arrays.asList(commonDTO.getIds()));
        return InvokeResult.success();
    }

    @PutMapping("updatePwd")
    @ApiOperation(value = "修改密码")
    public InvokeResult update(@RequestBody @Valid UserUpdateCommand command, HttpServletRequest request) {
        User entity = UserAssembler.toUser(command);
        return userService.updateEntity(entity,request);
    }

    @GetMapping("detail")
    @ApiOperation(value = "get User detail by id")
    public InvokeResult detail(@RequestParam Integer id) {
        UserVO vo = userManager.selectDetailVoById(id);
        return InvokeResult.success(vo);
    }


}
