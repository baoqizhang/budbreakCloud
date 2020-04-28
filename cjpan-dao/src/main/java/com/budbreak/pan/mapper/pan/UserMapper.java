package com.budbreak.pan.mapper.pan;

import com.budbreak.pan.entity.pan.User;
import com.budbreak.pan.vo.pan.UserVO;
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
public interface UserMapper extends BaseMapper<User> {

    /**
    * select User by id
    * @param id
    * @return
    */
    UserVO selectDetailById(@Param("id")int id) ;

    /**
    * selectOwnPage
    * @param page
    * @param param
    * @return
    */
    IPage<UserVO> selectOwnPage(Page page, @Param("param") Map<String, Object> param);

    /**
     * 通过用户名查询是否存在用户
     * @param userName
     * @return
     */
    UserVO selectUserByUserName(@Param("userName") String userName);
}
