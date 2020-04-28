package com.budbreak.pan.manager.verify;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.budbreak.pan.vo.verify.CodeVO;

import java.util.Map;
/**
* @Description: TODO
* @author:
* @Createed Date: TODO
* @ModificationHistory: Who  When  What
* ---------     -------------   --------------------------------------
**/
public interface CodeManager {

    /**
    * 获取列表
    * @param page 分页page
    * @param map
    * @return 分页数据
    */
    IPage<CodeVO> getPage(Page page, Map<String, Object> map);

    /**
    * 获取详情
    * @param id
    * @return
    */
    CodeVO selectDetailVoById(Integer id);

}
