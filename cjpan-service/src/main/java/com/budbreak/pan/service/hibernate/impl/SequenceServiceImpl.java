package com.budbreak.pan.service.hibernate.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.budbreak.pan.entity.hibernate.Sequence;
import com.budbreak.pan.mapper.hibernate.SequenceMapper;
import com.budbreak.pan.service.hibernate.SequenceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
* @Description: TODO
* @author:
* @Createed Date: 2018/11/21-10:26
* @ModificationHistory: Who  When  What
* ---------     -------------   --------------------------------------
**/

@Service
@Transactional(rollbackFor = Exception.class)
public class SequenceServiceImpl extends ServiceImpl<SequenceMapper, Sequence> implements SequenceService {

    @Autowired
    SequenceMapper sequenceMapper;

    @Override
    public void addEntity(Sequence entity) {
        sequenceMapper.insert(entity);
    }

    @Override
    public void updateEntity(Sequence entity) {
        sequenceMapper.updateById(entity);
    }

}
