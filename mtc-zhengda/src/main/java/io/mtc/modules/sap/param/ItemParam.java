package io.mtc.modules.sap.param;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 物料列表参数
 */
@Data
@ApiModel(value = "物料列表参数")
public class ItemParam {

    @ApiModelProperty(value = "业务伙伴代码")
    private String cardCode;

}
