package com.budbreak.pan.mapper.pan;

import com.budbreak.pan.entity.pan.Save;
import com.budbreak.pan.vo.pan.SaveVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
/**
 * @Description: 代码生成器自动生成
 * @author:
 * @Createed Date: 2020-4-24 14:46:16
 * @ModificationHistory: Who  When  What
 * ---------     -------------   --------------------------------------
 **/
@Repository
public interface SaveMapper extends BaseMapper<Save> {

    /**
    * select Save by id
    * @param id
    * @return
    */
    SaveVO selectDetailById(@Param("id")int id) ;

    /**
    * selectOwnPage
    * @param page
    * @param param
    * @return
    */
    IPage<SaveVO> selectOwnPage(Page page, @Param("param") Map<String, Object> param);

}
