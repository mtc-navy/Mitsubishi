package io.mtc.servicelayer.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/*
 * 订单子表 - 附加参数 @MTC_RDR4
 */
@Data
@ApiModel(value = "订单子表 - 附加参数")
public class MtcRDR4 implements Serializable {
    private Integer DocEntry;
    private Integer LineId;
    private Integer VisOrder;
    private String Object;
    private Object LogInst;
    private BigDecimal U_PalletSizeL;
    private BigDecimal U_PalletSizeW;
    private BigDecimal U_Capacity;
    private BigDecimal U_GangWayW;
    private BigDecimal U_MainAisleW1;
    private BigDecimal U_MainAisleW2;
}