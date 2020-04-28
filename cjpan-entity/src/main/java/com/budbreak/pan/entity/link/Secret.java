package com.budbreak.pan.entity.link;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 代码生成器自动生成
 * @author:
 * @Createed Date: 2020-4-24 16:26:57
 * @ModificationHistory: Who  When  What
 * ---------     -------------   --------------------------------------
 **/
@Data
@Accessors(chain = true)
@TableName("link_secret")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Secret implements Serializable{

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO,value = "fd_id")
    private Integer id;

    /** 下载数*/
    @TableField("fd_downloadNum")
    private Integer downloadNum;

    /** 过期时间*/
    @TableField("fd_expireDate")
    private Date expireDate;

    /** 文件名称*/
    @TableField("fd_fileName")
    private String fileName;

    /** 链接地址*/
    @TableField("fd_localLink")
    private String localLink;

    /** 加密*/
    @TableField("fd_secret")
    private String secret;

    /** 加密链接*/
    @TableField("fd_secretLink")
    private String secretLink;

    /** 分享时间*/
    @TableField("fd_shareDate")
    private Date shareDate;

    /** 用户姓名*/
    @TableField("fd_userName")
    private String userName;

    /** 是否删除 0否 1是*/
    @TableLogic
    @TableField("fd_deleted")
    private Integer deleted;

    /** 创建时间*/
    @TableField("fd_createDate")
    private Date createDate;

    /** 更新时间*/
    @TableField("fd_updateDate")
    private Date updateDate;

    /** 保留字段1*/
    @TableField("fd_reserved1")
    private String reserved1;

    /** 保留字段2*/
    @TableField("fd_reserved2")
    private String reserved2;

}
