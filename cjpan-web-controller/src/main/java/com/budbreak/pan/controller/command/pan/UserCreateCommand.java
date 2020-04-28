package com.budbreak.pan.controller.command.pan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

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
public class UserCreateCommand implements Serializable{

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    private String userName;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String password;

    @NotBlank(message = "邮箱不能为空")
    @ApiModelProperty(value = "邮箱")
    private String email;

    @NotBlank(message = "电话号码不能为空")
    @ApiModelProperty(value = "电话号码")
    private String phone;

    @NotBlank(message = "不能为空")
    @Length(max = 255,message = "不能超过255位")
    private String regcode;
}
