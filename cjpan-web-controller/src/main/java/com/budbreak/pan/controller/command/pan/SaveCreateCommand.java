package com.budbreak.pan.controller.command.pan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Description: 代码生成器自动生成
 * @author:
 * @Createed Date: 2020-4-24 16:26:58
 * @ModificationHistory: Who  When  What
 * ---------     -------------   --------------------------------------
 **/
@Data
public class SaveCreateCommand implements Serializable{

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "文件名不能为空")
    @ApiModelProperty(value = "文件名")
    private String fileName;

    @NotBlank(message = "文件链接不能为空")
    @ApiModelProperty(value = "文件链接")
    private String localLink;

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    private String userName;

    @NotBlank(message = "文件路径不能为空")
    @ApiModelProperty(value = "文件路径")
    private String panPath;

}
