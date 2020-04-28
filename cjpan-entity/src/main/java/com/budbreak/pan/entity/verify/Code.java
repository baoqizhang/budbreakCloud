package com.budbreak.pan.entity.verify;

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
@TableName("verify_code")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Code implements Serializable{

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO,value = "fd_id")
    private Integer id;

    /** 使用用户*/
    @TableField("fd_customName")
    private String customName;

    /** 时间*/
    @TableField("fd_date")
    private Date date;

    /** 操作人*/
    @TableField("fd_operatePerson")
    private String operatePerson;

    /** 注册码*/
    @TableField("fd_registerCode")
    private String registerCode;

    /** 状态*/
    @TableField("fd_state")
    private Boolean state;

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
