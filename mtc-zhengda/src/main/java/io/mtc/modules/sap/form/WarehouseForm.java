package io.mtc.modules.sap.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/*
 * MTC_WEB_VM_Warehouse
 */
@Data
@ApiModel(value = "仓库列表")
public class WarehouseForm implements Serializable {
    /**
     * 表 : MTC_WEB_VM_Warehouse
     * 对应字段 : WhsCode
     */
    @ApiModelProperty(value = "仓库编码")
    private String whscode;

    /**
     * 表 : MTC_WEB_VM_Warehouse
     * 对应字段 : WhsName
     */
    @ApiModelProperty(value = "仓库名称")
    private String whsname;

    private static final long serialVersionUID = 1L;
}