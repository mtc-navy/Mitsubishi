package io.mtc.servicelayer.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/*
 * 订单子表 - 运费 @MTC_RDR3
 */
@Data
@ApiModel(value = "订单子表 - 运费")
public class MtcRDR3 implements Serializable {

    private Integer DocEntry;
    private Integer LineId;
    private Integer VisOrder;
    private String Object;
    private Object LogInst;
    private BigDecimal U_TruckLength;
    private BigDecimal U_TruckWidth;
    private BigDecimal U_TruckHeight;
    private BigDecimal U_TruckVolume;
    private BigDecimal U_OtherLength1;
    private BigDecimal U_OtherWidth1;
    private BigDecimal U_OtherHeight1;
    private BigDecimal U_OtherVolume1;
    private BigDecimal U_OtherLength2;
    private BigDecimal U_OtherWidth2;
    private BigDecimal U_OtherHeight2;
    private BigDecimal U_OtherVolume2;
    private BigDecimal U_VolumeAmount;
    private BigDecimal U_VolumeFactor;
    private BigDecimal U_TotalAmount;
    private BigDecimal U_InsureBaseAmount;
    private BigDecimal U_InsureFactor1;
    private BigDecimal U_InsureFactor2;
    private BigDecimal U_InsureAmount;
    private String U_Comment;
}