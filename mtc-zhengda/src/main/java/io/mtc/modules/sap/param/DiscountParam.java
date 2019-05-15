package io.mtc.modules.sap.param;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 物料列表参数
 */
@Data
@ApiModel(value = "客户信用及折扣相关参数")
public class DiscountParam {

    @ApiModelProperty(value = "业务伙伴代码")
    private String cardCode;

    @ApiModelProperty(value = "选择单据主键")
    private String docEntry;

}
