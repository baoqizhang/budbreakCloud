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
@TableName("pan_user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable{

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO,value = "fd_id")
    private Integer id;

    /** 用户名*/
    @TableField("fd_username")
    private String username;

    /** 密码*/
    @TableField("fd_password")
    private String password;

    /** 权限*/
    @TableField("fd_level")
    private String level;

    /** 邮箱*/
    @TableField("fd_email")
    private String email;

    /** 电话号码*/
    @TableField("fd_phone")
    private String phone;

    /** 盐值*/
    @TableField("fd_alias")
    private String alias;

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
    @TableField("fd_reserved2")
    private String reserved2;

    /** 保留字段1*/
    @TableField("fd_reserved1")
    private String reserved1;

}
