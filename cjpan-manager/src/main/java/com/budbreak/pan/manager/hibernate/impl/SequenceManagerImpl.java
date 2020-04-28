package com.budbreak.pan.manager.hibernate.impl;

import com.budbreak.pan.vo.hibernate.SequenceVO;
import com.budbreak.pan.mapper.hibernate.SequenceMapper;
import com.budbreak.pan.manager.hibernate.SequenceManager;

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
public class SequenceManagerImpl implements SequenceManager {

    @Autowired
    SequenceMapper sequenceMapper;

    @Override
    public IPage<SequenceVO> getPage(Page page, Map<String,Object> map) {
        return sequenceMapper.selectOwnPage(page,map);
    }

    @Override
    public SequenceVO selectDetailVoById(Integer id) {
        return sequenceMapper.selectDetailById(id);
    }

}
