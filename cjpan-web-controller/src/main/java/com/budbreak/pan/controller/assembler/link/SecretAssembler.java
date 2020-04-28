package com.budbreak.pan.controller.assembler.link;

import com.budbreak.pan.controller.command.link.SecretCreateCommand;
import com.budbreak.pan.controller.command.link.SecretUpdateCommand;
import com.budbreak.pan.entity.link.Secret;

import java.util.Date;

/**
 * @Description: 代码生成器自动生成
 * @author:
 * @Createed Date: 2020-4-24 16:26:58
 * @ModificationHistory: Who  When  What
 * ---------     -------------   --------------------------------------
 **/
public class SecretAssembler{
    public static Secret toSecret(SecretCreateCommand command){
        Date now = new Date();

        return new Secret()
            .setDownloadNum(command.getDownloadNum()) // 下载数
            .setExpireDate(command.getExpireDate()) // 过期时间
            .setFileName(command.getFileName()) // 文件名称
            .setLocalLink(command.getLocalLink()) // 链接地址
            .setSecret(command.getSecret()) // 加密
            .setSecretLink(command.getSecretLink()) // 加密链接
            .setShareDate(command.getShareDate()) // 分享时间
            .setUserName(command.getUserName()) // 用户姓名
            .setCreateDate(now) // 创建时间
            .setUpdateDate(now); // 最后更新时间
    }

    public static Secret toSecret(SecretUpdateCommand command){
        Date now = new Date();

        return new Secret()
            .setId(command.getId()) // id
            .setDownloadNum(command.getDownloadNum()) // 下载数
            .setExpireDate(command.getExpireDate()) // 过期时间
            .setFileName(command.getFileName()) // 文件名称
            .setLocalLink(command.getLocalLink()) // 链接地址
            .setSecret(command.getSecret()) // 加密
            .setSecretLink(command.getSecretLink()) // 加密链接
            .setShareDate(command.getShareDate()) // 分享时间
            .setUserName(command.getUserName()) // 用户姓名
            .setUpdateDate(now); // 最后更新时间
    }
}
