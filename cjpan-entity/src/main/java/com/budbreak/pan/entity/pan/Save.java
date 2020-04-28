package com.budbreak.pan.entity.pan;

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
@TableName("pan_save")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Save implements Serializable{

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO,value = "fd_id")
    private Integer id;

    /** 文件名*/
    @TableField("fd_fileName")
    private String fileName;

    /** 文件链接*/
    @TableField("fd_localLink")
    private String localLink;

    /** 用户名*/
    @TableField("fd_userName")
    private String userName;

    /** 文件路径*/
    @TableField("fd_panPath")
    private String panPath;

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

    /** 保留字段2*/
    @TableField("fd_reserved1")
    private String reserved1;

    /** 保留字段2*/
    @TableField("fd_reserved2")
    private String reserved2;

}
