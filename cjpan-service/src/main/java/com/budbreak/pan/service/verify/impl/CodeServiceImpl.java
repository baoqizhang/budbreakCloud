package com.budbreak.pan.service.verify.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.budbreak.pan.entity.verify.Code;
import com.budbreak.pan.mapper.verify.CodeMapper;
import com.budbreak.pan.service.verify.CodeService;

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
public class CodeServiceImpl extends ServiceImpl<CodeMapper, Code> implements CodeService {

    @Autowired
    CodeMapper codeMapper;

    @Override
    public void addEntity(Code entity) {
        codeMapper.insert(entity);
    }

    @Override
    public void updateEntity(Code entity) {
        codeMapper.updateById(entity);
    }

}
