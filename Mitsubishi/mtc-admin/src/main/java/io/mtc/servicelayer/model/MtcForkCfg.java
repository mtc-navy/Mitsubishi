package io.mtc.servicelayer.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/*
 * 车型配置表@MTC_FORKCFG
 */
@Data
@ApiModel(value = "车型配置表")
public class MtcForkCfg implements Serializable {
    private String Code;
    private Object Name;
    private int DocEntry;
    private String Canceled;
    private String Object;
    private Object LogInst;
    private int UserSign;
    private String Transfered;
    private String CreateDate;
    private String CreateTime;
    private Object UpdateDate;
    private Object UpdateTime;
    private String DataSource;
    private String U_Factory;
    private String U_BaseModel;
    private String U_TypeSpec;
    private String U_WOCharger;
    private BigDecimal U_PriceFactory;
    private BigDecimal U_PriceHQ;
    private BigDecimal U_PriceCustomer;
    private String U_Comment;
}
