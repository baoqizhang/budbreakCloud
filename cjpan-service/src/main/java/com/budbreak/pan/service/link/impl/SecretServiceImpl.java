package com.budbreak.pan.service.link.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.budbreak.pan.entity.link.Secret;
import com.budbreak.pan.mapper.link.SecretMapper;
import com.budbreak.pan.service.link.SecretService;

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
public class SecretServiceImpl extends ServiceImpl<SecretMapper, Secret> implements SecretService {

    @Autowired
    SecretMapper secretMapper;

    @Override
    public void addEntity(Secret entity) {
        secretMapper.insert(entity);
    }

    @Override
    public void updateEntity(Secret entity) {
        secretMapper.updateById(entity);
    }

}
