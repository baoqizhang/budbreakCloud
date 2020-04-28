package com.budbreak.pan.controller.command.hibernate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description: 代码生成器自动生成
 * @author:
 * @Createed Date: 2020-4-24 16:26:58
 * @ModificationHistory: Who  When  What
 * ---------     -------------   --------------------------------------
 **/
@Data
public class SequenceCreateCommand implements Serializable{

    private static final long serialVersionUID = 1L;

    @NotNull(message = "不能为空")
    @ApiModelProperty(value = "")
    private Long val;

}
