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

}
