package io.mtc.servicelayer.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/*
 * 订单子表 - 删除配件 @MTC_RDR2
 */
@Data
@ApiModel(value = "订单子表 - 删除配件")
public class MtcRDR2 implements Serializable {
    private int DocEntry;
    private int LineId;
    private int VisOrder;
    private String Object;
    private Object LogInst;
    private String U_CfgSpec;
    private BigDecimal U_Price;
    private BigDecimal U_Discount;
    private BigDecimal U_Amount;
    private Object U_Comment;
}