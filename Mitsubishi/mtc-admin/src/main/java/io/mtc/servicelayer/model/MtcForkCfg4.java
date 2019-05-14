package io.mtc.servicelayer.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/*
 * 轮胎配置@MTC_FORKCFG4
 */
@Data
@ApiModel(value = "轮胎配置")
public class MtcForkCfg4 implements Serializable {
    private String Code;
    private int LineId;
    private String Object;
    private Object LogInst;
    private String U_SubType;
    private String U_PartCode;
    private BigDecimal U_PriceFactory;
    private BigDecimal U_PriceHQ;
    private BigDecimal U_PriceCustomer;
    private String U_IsDefault;
}
