package com.budbreak.pan.vo.pan;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Date:2020-4-24 16:26:57
 * @author
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserVO implements Serializable{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "权限")
    private String level;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "盐值")
    private String alias;

    @ApiModelProperty(value = "是否删除 0否 1是")
    private Integer deleted;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "更新时间")
    private Date updateDate;

    @ApiModelProperty(value = "保留字段2")
    private String reserved2;

    @ApiModelProperty(value = "保留字段1")
    private String reserved1;

}
