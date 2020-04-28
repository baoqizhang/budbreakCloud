package com.budbreak.pan.mapper.verify;

import com.budbreak.pan.entity.verify.Code;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.budbreak.pan.vo.verify.CodeVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;
/**
 * @Description: 代码生成器自动生成
 * @author:
 * @Createed Date: 2020-4-24 14:46:16
 * @ModificationHistory: Who  When  What
 * ---------     -------------   --------------------------------------
 **/
@Repository
public interface CodeMapper extends BaseMapper<Code> {

    /**
    * select Code by id
    * @param id
    * @return
    */
    CodeVO selectDetailById(@Param("id")int id) ;

    /**
    * selectOwnPage
    * @param page
    * @param param
    * @return
    */
    IPage<CodeVO> selectOwnPage(Page page, @Param("param") Map<String, Object> param);

    /**
     * 查询邀请码是否存在
     * @param registerCode
     * @param userName
     * @return
     */
    CodeVO selectByRegisterCode(String registerCode, String userName);
}
