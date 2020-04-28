package com.budbreak.pan.controller.assembler.pan;

import com.budbreak.pan.controller.command.pan.SaveCreateCommand;
import com.budbreak.pan.controller.command.pan.SaveUpdateCommand;
import com.budbreak.pan.entity.pan.Save;

import java.util.Date;

/**
 * @Description: 代码生成器自动生成
 * @author:
 * @Createed Date: 2020-4-24 16:26:58
 * @ModificationHistory: Who  When  What
 * ---------     -------------   --------------------------------------
 **/
public class SaveAssembler{
    public static Save toSave(SaveCreateCommand command){
        Date now = new Date();

        return new Save()
            .setFileName(command.getFileName()) // 文件名
            .setLocalLink(command.getLocalLink()) // 文件链接
            .setUserName(command.getUserName()) // 用户名
            .setPanPath(command.getPanPath()) // 文件路径
            .setCreateDate(now) // 创建时间
            .setUpdateDate(now); // 最后更新时间
    }

    public static Save toSave(SaveUpdateCommand command){
        Date now = new Date();

        return new Save()
            .setId(command.getId()) // id
            .setFileName(command.getFileName()) // 文件名
            .setLocalLink(command.getLocalLink()) // 文件链接
            .setUserName(command.getUserName()) // 用户名
            .setPanPath(command.getPanPath()) // 文件路径
            .setUpdateDate(now); // 最后更新时间
    }
}
