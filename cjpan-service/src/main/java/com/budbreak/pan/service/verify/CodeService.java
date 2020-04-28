package com.budbreak.pan.service.verify;

import com.baomidou.mybatisplus.extension.service.IService;
import com.budbreak.pan.entity.verify.Code;

/**
* @Description: TODO
* @author:
* @Createed Date: 2018/11/21-10:26
* @ModificationHistory: Who  When  What
* ---------     -------------   --------------------------------------
**/
public interface CodeService extends IService<Code> {

    /**
     * add
     * @param entity
     */
    void addEntity(Code entity);

     /**
     * updateEntity
     * @param entity
     */
    void updateEntity(Code entity);

}
