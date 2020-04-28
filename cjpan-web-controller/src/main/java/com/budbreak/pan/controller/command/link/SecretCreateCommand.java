package com.budbreak.pan.controller.command.link;

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
public class SecretCreateCommand implements Serializable{

    private static final long serialVersionUID = 1L;

    @NotNull(message = "下载数不能为空")
    @ApiModelProperty(value = "下载数")
    private Integer downloadNum;

    @NotNull(message = "过期时间不能为空")
    @ApiModelProperty(value = "过期时间")
    private Date expireDate;

    @NotBlank(message = "文件名称不能为空")
    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @NotBlank(message = "链接地址不能为空")
    @ApiModelProperty(value = "链接地址")
    private String localLink;

    @NotBlank(message = "加密不能为空")
    @ApiModelProperty(value = "加密")
    private String secret;

    @NotBlank(message = "加密链接不能为空")
    @ApiModelProperty(value = "加密链接")
    private String secretLink;

    @NotNull(message = "分享时间不能为空")
    @ApiModelProperty(value = "分享时间")
    private Date shareDate;

    @NotBlank(message = "用户姓名不能为空")
    @ApiModelProperty(value = "用户姓名")
    private String userName;

}
