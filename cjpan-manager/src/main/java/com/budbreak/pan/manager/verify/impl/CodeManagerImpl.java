package com.budbreak.pan.manager.verify.impl;

import com.budbreak.pan.mapper.verify.CodeMapper;
import com.budbreak.pan.manager.verify.CodeManager;

import com.budbreak.pan.vo.verify.CodeVO;
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
public class CodeManagerImpl implements CodeManager {

    @Autowired
    CodeMapper codeMapper;

    @Override
    public IPage<CodeVO> getPage(Page page, Map<String,Object> map) {
        return codeMapper.selectOwnPage(page,map);
    }

    @Override
    public CodeVO selectDetailVoById(Integer id) {
        return codeMapper.selectDetailById(id);
    }

}
