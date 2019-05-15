package io.mtc.modules.sap.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 物料列表
 */
@Data
@ApiModel(value = "物料列表")
public class MaterialForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 表 : MTC_WEB_VM_Material
     * 对应字段 : ItemCode
     */
    @ApiModelProperty(value = "物料代码")
    private String itemcode;

    /**
     * 表 : MTC_WEB_VM_Material
     * 对应字段 : ItemName
     */
    @ApiModelProperty(value = "物料名称")
    private String itemname;

    /**
     * 表 : MTC_WEB_VM_Material
     * 对应字段 : PerPacKg
     */
    @ApiModelProperty(value = "规格(标重)")
    private BigDecimal perpackg;

    /**
     * 表 : MTC_WEB_VM_Material
     * 对应字段 : IsStdPack
     */
    @ApiModelProperty(value = "是否标包")
    private String isstdpack;

    /**
     * 表 : MTC_WEB_VM_Material
     * 对应字段 : InvntryUom
     */
    @ApiModelProperty(value = "库存单位")
    private String invntryuom;

}