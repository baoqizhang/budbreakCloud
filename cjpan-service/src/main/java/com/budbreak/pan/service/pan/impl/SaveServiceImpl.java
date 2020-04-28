package com.budbreak.pan.service.pan.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.budbreak.pan.entity.pan.Save;
import com.budbreak.pan.mapper.pan.SaveMapper;
import com.budbreak.pan.service.pan.SaveService;

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
public class SaveServiceImpl extends ServiceImpl<SaveMapper, Save> implements SaveService {

    @Autowired
    SaveMapper saveMapper;

    @Override
    public void addEntity(Save entity) {
        saveMapper.insert(entity);
    }

    @Override
    public void updateEntity(Save entity) {
        saveMapper.updateById(entity);
    }

}
