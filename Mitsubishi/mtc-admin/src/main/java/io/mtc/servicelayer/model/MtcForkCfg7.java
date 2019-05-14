package io.mtc.servicelayer.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/*
 * 可减配置@MTC_FORKCFG7
 */
@Data
@ApiModel(value = "可减配置")
public class MtcForkCfg7 implements Serializable {
    private String Code;
    private int LineId;
    private String Object;
    private Object LogInst;
    private String U_PartCode;
    private BigDecimal U_PriceFactory;
    private BigDecimal U_PriceHQ;
    private BigDecimal U_PriceCustomer;
}
