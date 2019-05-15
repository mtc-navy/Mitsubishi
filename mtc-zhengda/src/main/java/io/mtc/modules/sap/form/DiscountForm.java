package io.mtc.modules.sap.form;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 车牌信息
 */
@Data
@ApiModel(value = "信用额度列表")
public class DiscountForm {

    @ApiModelProperty(value = "客户代码")
    private String cardCode;

    @ApiModelProperty(value = "信用额度")
    private BigDecimal credit;

    @ApiModelProperty(value = "累计欠款")
    private BigDecimal arrears;

    @ApiModelProperty(value = "可用返利")
    private BigDecimal rebate;


}
