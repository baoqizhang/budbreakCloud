package com.budbreak.pan.service.link;

import com.baomidou.mybatisplus.extension.service.IService;
import com.budbreak.pan.entity.link.Secret;
import com.budbreak.pan.vo.link.SecretVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
/**
* @Description: TODO
* @author:
* @Createed Date: 2018/11/21-10:26
* @ModificationHistory: Who  When  What
* ---------     -------------   --------------------------------------
**/
public interface SecretService extends IService<Secret> {

    /**
     * add
     * @param entity
     */
    void addEntity(Secret entity);

     /**
     * updateEntity
     * @param entity
     */
    void updateEntity(Secret entity);

    /**
     * 通过加密链接查找
     * @param link
     * @return
     */
    SecretVO selectSecretBySecretLink(String link);

    /**
     *  增加下载次数
     * @param linkSecret
     */
    void addOneToDownloadNum(SecretVO linkSecret);

    /**
     * 通过本地链接+用户名查找
     * @param localLink
     * @param userName
     */
    SecretVO selectLinkSecretByLocalLinkAndUserName(String localLink, String userName);
}
