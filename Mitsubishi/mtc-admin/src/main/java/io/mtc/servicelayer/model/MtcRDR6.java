package io.mtc.servicelayer.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/*
 * 订单子表 - 附件 @MTC_RDR6
 */
@Data
@ApiModel(value = "订单子表 - 附件")
public class MtcRDR6 implements Serializable {
    private Integer DocEntry;
    private Integer LineId;
    private Integer VisOrder;
    private String Object;
    private Object LogInst;
    private String U_FilePath;
    private String U_FileName;
    private String U_FileType;
}