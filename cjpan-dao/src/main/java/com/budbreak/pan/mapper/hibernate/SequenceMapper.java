package com.budbreak.pan.mapper.hibernate;

import com.budbreak.pan.entity.hibernate.Sequence;
import com.budbreak.pan.vo.hibernate.SequenceVO;
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
public interface SequenceMapper extends BaseMapper<Sequence> {

    /**
    * select Sequence by id
    * @param id
    * @return
    */
    SequenceVO selectDetailById(@Param("id")int id) ;

    /**
    * selectOwnPage
    * @param page
    * @param param
    * @return
    */
    IPage<SequenceVO> selectOwnPage(Page page, @Param("param") Map<String, Object> param);

}
