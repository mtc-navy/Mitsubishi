package io.mtc.servicelayer.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/*
 * 订单主表@MTC_ORDR
 */
@Data
@ApiModel(value = "订单主表")
public class MtcORDR implements Serializable {

    private Integer DocNum;
    private Integer Period;
    private Integer Instance;
    private Integer Series;
    private String Handwrtten;
    private String Status;
    private String RequestStatus;
    private String Creator;
    private String Remark;
    private Integer DocEntry;
    private String Canceled;
    private String Object;
    private Object LogInst;
    private Integer UserSign;
    private String Transfered;
    private String CreateDate;
    private String CreateTime;
    private Object UpdateDate;
    private Object UpdateTime;
    private String DataSource;
    private String U_DocDate;
    private String U_OrderNo;
    private String U_InnerNo;
    private String U_Currency;
    private String U_ReqDelivery;
    private String U_ReplyDelivery;
    private String U_CardCode;
    private String U_CardName;
    private String U_Phone;
    private String U_AddressCountry;
    private String U_AddressProvince;
    private String U_Addres;
    private String U_FinalCardName;
    private String U_ItemModel;
    private String U_ItemCode;
    private BigDecimal U_Quantity;
    private BigDecimal U_TotalAmount;
    private String U_DataFrom;
    private Integer U_BaseEntry;
    private Integer U_BaseType;
    private Integer U_DocStatus;
    private String U_Comment;
}