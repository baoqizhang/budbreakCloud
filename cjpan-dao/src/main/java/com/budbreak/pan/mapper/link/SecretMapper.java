package com.budbreak.pan.mapper.link;

import com.budbreak.pan.entity.link.Secret;
import com.budbreak.pan.vo.link.SecretVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
public interface SecretMapper extends BaseMapper<Secret> {

    /**
    * select Secret by id
    * @param id
    * @return
    */
    SecretVO selectDetailById(@Param("id")int id) ;

    /**
    * selectOwnPage
    * @param page
    * @param param
    * @return
    */
    IPage<SecretVO> selectOwnPage(Page page, @Param("param") Map<String, Object> param);

    /**
     * 通过加密连接查找
     * @param link
     * @return
     */
    SecretVO selectSecretBySecretLink(@Param("link") String link);

    /**
     * 增加下次次数
     * @param downloadNum
     * @param localLink
     */
    void updateDownloadNum(int downloadNum, String localLink);

    /**
     * 通过本地连接+用户名查找
     * @param localLink
     * @param userName
     * @return
     */
    SecretVO selectLinkSecretByLocalLinkAndUserName(String localLink, String userName);

    /**
     * 更新链接
     * @param map
     */
    void updateSecret(@Param("map") Map map);
}
