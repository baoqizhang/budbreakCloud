package com.budbreak.pan.entity.hibernate;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: 代码生成器自动生成
 * @author:
 * @Createed Date: 2020-4-24 16:26:57
 * @ModificationHistory: Who  When  What
 * ---------     -------------   --------------------------------------
 **/
@Data
@Accessors(chain = true)
@TableName("hibernate_sequence")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Sequence implements Serializable{

    private static final long serialVersionUID = 1L;

    /** */
    @TableField("next_val")
    private Long val;

}
