package io.mtc.servicelayer.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/*
 * 订单子表 - 车辆选配|附加配件 @MTC_RDR1
 */
@Data
@ApiModel(value = "订单子表 - 车辆选配|附加配件")
public class MtcRDR1 implements Serializable {
    private Integer DocEntry;
    private Integer LineId;
    private Integer VisOrder;
    private String Object;
    private Object LogInst;
    private String U_WithOut;
    private String U_CfgType;
    private String U_CfgSubType;
    private String U_CfgSpec;
    private BigDecimal U_Price;
    private BigDecimal U_Discount;
    private BigDecimal U_Amount;
    private String U_IsDefault;
    private String U_Comment;
}