package com.budbreak.pan.service.pan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.budbreak.pan.entity.pan.Save;
import com.budbreak.pan.vo.pan.SaveVO;
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
public interface SaveService extends IService<Save> {

    /**
     * add
     * @param entity
     */
    void addEntity(Save entity);

     /**
     * updateEntity
     * @param entity
     */
    void updateEntity(Save entity);

}
