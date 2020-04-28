package com.budbreak.pan.controller.assembler.verify;

import com.budbreak.pan.controller.command.verify.CodeCreateCommand;
import com.budbreak.pan.controller.command.verify.CodeUpdateCommand;
import com.budbreak.pan.entity.verify.Code;

import java.util.Date;

/**
 * @Description: 代码生成器自动生成
 * @author:
 * @Createed Date: 2020-4-24 16:26:58
 * @ModificationHistory: Who  When  What
 * ---------     -------------   --------------------------------------
 **/
public class CodeAssembler{
    public static Code toCode(CodeCreateCommand command){
        Date now = new Date();

        return new Code()
            .setCustomName(command.getCustomName()) // 使用用户
            .setDate(command.getDate()) // 时间
            .setOperatePerson(command.getOperatePerson()) // 操作人
            .setRegisterCode(command.getRegisterCode()) // 注册码
            .setState(command.getState()) // 状态
            .setCreateDate(now) // 创建时间
            .setUpdateDate(now); // 最后更新时间
    }

    public static Code toCode(CodeUpdateCommand command){
        Date now = new Date();

        return new Code()
            .setId(command.getId()) // id
            .setCustomName(command.getCustomName()) // 使用用户
            .setDate(command.getDate()) // 时间
            .setOperatePerson(command.getOperatePerson()) // 操作人
            .setRegisterCode(command.getRegisterCode()) // 注册码
            .setState(command.getState()) // 状态
            .setUpdateDate(now); // 最后更新时间
    }
}
