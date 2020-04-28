package com.budbreak.pan.manager.pan.impl;

import com.budbreak.pan.vo.pan.UserVO;
import com.budbreak.pan.mapper.pan.UserMapper;
import com.budbreak.pan.manager.pan.UserManager;

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
public class UserManagerImpl implements UserManager {

    @Autowired
    UserMapper userMapper;

    @Override
    public IPage<UserVO> getPage(Page page, Map<String,Object> map) {
        return userMapper.selectOwnPage(page,map);
    }

    @Override
    public UserVO selectDetailVoById(Integer id) {
        return userMapper.selectDetailById(id);
    }

}
