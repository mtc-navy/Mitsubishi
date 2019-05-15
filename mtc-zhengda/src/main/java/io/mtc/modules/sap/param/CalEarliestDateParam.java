package io.mtc.modules.sap.param;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 物料列表参数
 */
@Data
@ApiModel(value = "提货日期计算参数")
public class CalEarliestDateParam {

    @ApiModelProperty(value = "仓库代码")
    private String whsCode;

    @ApiModelProperty(value = "物料代码")
    private String itemCode;

    @ApiModelProperty(value = "用户ID")
    private Long userId;
}
