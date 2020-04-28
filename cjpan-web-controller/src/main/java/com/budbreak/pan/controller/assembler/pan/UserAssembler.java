package com.budbreak.pan.controller.assembler.pan;

import com.budbreak.pan.controller.command.pan.UserCreateCommand;
import com.budbreak.pan.controller.command.pan.UserUpdateCommand;
import com.budbreak.pan.entity.pan.User;

import java.util.Date;

/**
 * @Description: 代码生成器自动生成
 * @author:
 * @Createed Date: 2020-4-24 16:26:58
 * @ModificationHistory: Who  When  What
 * ---------     -------------   --------------------------------------
 **/
public class UserAssembler {
    public static User toUser(UserCreateCommand command) {
        Date now = new Date();

        return new User()
                .setUsername(command.getUserName()) // 用户名
                .setPassword(command.getPassword()) // 密码
                .setEmail(command.getEmail()) // 邮箱
                .setCreateDate(now) // 创建时间
                .setPhone(command.getPhone())
                .setUpdateDate(now); // 最后更新时间
    }

    public static User toUser(UserUpdateCommand command) {
        Date now = new Date();

        return new User()
                .setId(command.getId()) // id
                .setUsername(command.getUsername()) // 用户名
                .setPassword(command.getPassword()) // 密码
                .setLevel(command.getLevel()) // 权限
                .setEmail(command.getEmail()) // 邮箱
                .setPhone(command.getPhone()) // 电话号码
                .setAlias(command.getAlias()) // 盐值
                .setReserved1(command.getReserved1()) // 保留字段1
                .setUpdateDate(now); // 最后更新时间
    }
}
