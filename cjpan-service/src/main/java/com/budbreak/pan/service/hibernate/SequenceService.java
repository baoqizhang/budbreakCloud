package com.budbreak.pan.service.hibernate;

import com.baomidou.mybatisplus.extension.service.IService;
import com.budbreak.pan.entity.hibernate.Sequence;
import com.budbreak.pan.vo.hibernate.SequenceVO;
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
public interface SequenceService extends IService<Sequence> {

    /**
     * add
     * @param entity
     */
    void addEntity(Sequence entity);

     /**
     * updateEntity
     * @param entity
     */
    void updateEntity(Sequence entity);

}
