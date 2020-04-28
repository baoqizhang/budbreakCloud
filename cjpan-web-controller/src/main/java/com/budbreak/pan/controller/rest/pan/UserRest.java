package com.budbreak.pan.controller.rest.pan;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.budbreak.pan.common.CommonDTO;
import com.budbreak.pan.common.InvokeResult;
import com.budbreak.pan.common.PageResult;
import com.budbreak.pan.controller.assembler.pan.UserAssembler;
import com.budbreak.pan.controller.command.pan.UserCreateCommand;
import com.budbreak.pan.controller.command.pan.UserUpdateCommand;
import com.budbreak.pan.entity.pan.User;
import com.budbreak.pan.manager.pan.UserManager;
import com.budbreak.pan.mapper.pan.UserMapper;
import com.budbreak.pan.service.pan.UserService;
import com.budbreak.pan.vo.pan.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.budbreak.pan.common.SystemUtil.isWindows;

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
    @GetMapping("sendEmail/{email}")
    public InvokeResult sendEmail(@PathVariable String email){
        // TODO 发送邮箱验证码并保存到redis，用于重置密码验证
        return InvokeResult.success();
    }

    @ApiOperation("忘记密码")
    @PostMapping("forget")
    public InvokeResult forget(){
        // TODO 查询输入用户的redis验证码是否正确，进行修改密码
        return InvokeResult.success();
    }

    @DeleteMapping("delete")
    @ApiOperation(value = "delect User by id")
    public InvokeResult delete(@RequestBody CommonDTO commonDTO) {
        userService.removeByIds(Arrays.asList(commonDTO.getIds()));
        return InvokeResult.success();
    }

    @PutMapping("update")
    @ApiOperation(value = "update User by id")
    public InvokeResult update(@RequestBody @Valid UserUpdateCommand command) {
        User entity = UserAssembler.toUser(command);
        userService.updateEntity(entity);
        return InvokeResult.success();
    }

    @GetMapping("detail")
    @ApiOperation(value = "get User detail by id")
    public InvokeResult detail(@RequestParam Integer id) {
        UserVO vo = userManager.selectDetailVoById(id);
        return InvokeResult.success(vo);
    }

    @GetMapping("page")
    @ApiOperation(value = "get User page")
    public IPage<UserVO> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        PageResult page = new PageResult(pageNum, pageSize);

        Map<String, Object> map = new HashMap<>(4);

        return userManager.getPage(page, map);
    }
}
