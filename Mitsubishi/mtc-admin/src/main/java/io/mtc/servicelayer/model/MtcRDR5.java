package io.mtc.servicelayer.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/*
 * 订单子表 - 项目号 @MTC_RDR5
 */
@Data
@ApiModel(value = "订单子表 - 项目号")
public class MtcRDR5 implements Serializable {
    private Integer DocEntry;
    private Integer LineId;
    private Integer VisOrder;
    private String Object;
    private Object LogInst;
    private String U_ProjectNo;
    private Integer U_BaseEntry;
    private Integer U_BaseType;
    private Integer U_BaseLine;
}