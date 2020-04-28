package com.budbreak.pan.controller.command.pan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
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
public class UserUpdateCommand implements Serializable{

    private static final long serialVersionUID = 1L;

    @NotNull(message = "id不能为空")
    @ApiModelProperty(value = "id")
    private Integer id;

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    private String username;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String password;

    @NotBlank(message = "权限不能为空")
    @ApiModelProperty(value = "权限")
    private String level;

    @NotBlank(message = "邮箱不能为空")
    @ApiModelProperty(value = "邮箱")
    private String email;

    @NotBlank(message = "电话号码不能为空")
    @ApiModelProperty(value = "电话号码")
    private String phone;

    @NotBlank(message = "盐值不能为空")
    @ApiModelProperty(value = "盐值")
    private String alias;

    @NotBlank(message = "保留字段1不能为空")
    @ApiModelProperty(value = "保留字段1")
    private String reserved1;

}
