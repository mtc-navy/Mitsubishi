package io.mtc.modules.sap.param;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 主键参数
 */
@Data
@ApiModel(value = "主键参数")
public class KeyParam {

    @ApiModelProperty(value = "主键参数")
    private Long docEntry;

}
