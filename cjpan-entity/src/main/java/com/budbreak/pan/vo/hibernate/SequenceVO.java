package com.budbreak.pan.vo.hibernate;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Date:2020-4-24 16:26:57
 * @author
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SequenceVO implements Serializable{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "")
    private Long val;

}
