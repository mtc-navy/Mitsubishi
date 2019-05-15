package io.mtc.modules.sap.param;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 销售量校验参数
 */
@Data
@ApiModel(value = "销售量校验参数")
public class AvgCheckParam {

    @ApiModelProperty(value = "业务伙伴代码")
    private String cardCode;

    @ApiModelProperty(value = "物料编码")
    private String itemCode;

    @ApiModelProperty(value = "日期")
    private String docDate;

    @ApiModelProperty(value = "当前数量")
    private BigDecimal quantity;
}
