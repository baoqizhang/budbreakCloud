package com.budbreak.pan.manager.link.impl;

import com.budbreak.pan.vo.link.SecretVO;
import com.budbreak.pan.mapper.link.SecretMapper;
import com.budbreak.pan.manager.link.SecretManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
* @Description: TODO
* @author:
* @Createed Date: 2018/11/21-10:26
* @ModificationHistory: Who  When  What
* ---------     -------------   --------------------------------------
**/

@Service
public class SecretManagerImpl implements SecretManager {

    @Autowired
    SecretMapper secretMapper;

    @Override
    public IPage<SecretVO> getPage(Page page, Map<String,Object> map) {
        return secretMapper.selectOwnPage(page,map);
    }

    @Override
    public SecretVO selectDetailVoById(Integer id) {
        return secretMapper.selectDetailById(id);
    }

}
