package io.mtc.servicelayer.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/*
 * 充电器配置@MTC_FORKCFG2
 */
@Data
@ApiModel(value = "充电器配置")
public class MtcForkCfg2 implements Serializable {
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
