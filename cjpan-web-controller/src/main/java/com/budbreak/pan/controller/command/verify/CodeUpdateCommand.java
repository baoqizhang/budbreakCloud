package com.budbreak.pan.controller.command.verify;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 代码生成器自动生成
 * @author:
 * @Createed Date: 2020-4-24 16:26:58
 * @ModificationHistory: Who  When  What
 * ---------     -------------   --------------------------------------
 **/
@Data
public class CodeUpdateCommand implements Serializable{

    private static final long serialVersionUID = 1L;

    @NotNull(message = "id不能为空")
    @ApiModelProperty(value = "id")
    private Integer id;

    @NotBlank(message = "使用用户不能为空")
    @ApiModelProperty(value = "使用用户")
    private String customName;

    @NotNull(message = "时间不能为空")
    @ApiModelProperty(value = "时间")
    private Date date;

    @NotBlank(message = "操作人不能为空")
    @ApiModelProperty(value = "操作人")
    private String operatePerson;

    @NotBlank(message = "注册码不能为空")
    @ApiModelProperty(value = "注册码")
    private String registerCode;

    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态")
    private Boolean state;

}
