package com.budbreak.pan.manager.link;

import com.budbreak.pan.vo.link.SecretVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;
/**
* @Description: TODO
* @author:
* @Createed Date: TODO
* @ModificationHistory: Who  When  What
* ---------     -------------   --------------------------------------
**/
public interface SecretManager {

    /**
    * 获取列表
    * @param page 分页page
    * @param map
    * @return 分页数据
    */
    IPage<SecretVO> getPage(Page page, Map<String, Object> map);

    /**
    * 获取详情
    * @param id
    * @return
    */
    SecretVO selectDetailVoById(Integer id);

}
