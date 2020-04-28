package com.budbreak.pan.vo.verify;

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
public class CodeVO implements Serializable{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "使用用户")
    private String customName;

    @ApiModelProperty(value = "时间")
    private Date date;

    @ApiModelProperty(value = "操作人")
    private String operatePerson;

    @ApiModelProperty(value = "注册码")
    private String registerCode;

    @ApiModelProperty(value = "状态")
    private Boolean state;

    @ApiModelProperty(value = "是否删除 0否 1是")
    private Integer deleted;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "更新时间")
    private Date updateDate;

    @ApiModelProperty(value = "保留字段1")
    private String reserved1;

    @ApiModelProperty(value = "保留字段2")
    private String reserved2;

}
