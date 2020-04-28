package com.budbreak.pan.manager.pan.impl;

import com.budbreak.pan.vo.pan.SaveVO;
import com.budbreak.pan.mapper.pan.SaveMapper;
import com.budbreak.pan.manager.pan.SaveManager;

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
public class SaveManagerImpl implements SaveManager {

    @Autowired
    SaveMapper saveMapper;

    @Override
    public IPage<SaveVO> getPage(Page page, Map<String,Object> map) {
        return saveMapper.selectOwnPage(page,map);
    }

    @Override
    public SaveVO selectDetailVoById(Integer id) {
        return saveMapper.selectDetailById(id);
    }

}
