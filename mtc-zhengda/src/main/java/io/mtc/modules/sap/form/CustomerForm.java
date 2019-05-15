package io.mtc.modules.sap.form;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 客户列表
 */
@Data
@ApiModel(value = "客户列表")
public class CustomerForm {

    /**
     * 表 : MTC_WEB_VM_Customer
     * 对应字段 : CardCode
     */
    @ApiModelProperty(value = "业务伙伴代码")
    private String cardcode;

    /**
     * 表 : MTC_WEB_VM_Customer
     * 对应字段 : CardName
     */
    @ApiModelProperty(value = "业务伙伴名称")
    private String cardname;

    /**
     * 表 : MTC_WEB_VM_Customer
     * 对应字段 : SalePsitn
     */
    @ApiModelProperty(value = "业务员编号")
    private String salepsitn;

    /**
     * 表 : MTC_WEB_VM_Customer
     * 对应字段 : SaleMan
     */
    @ApiModelProperty(value = "业务员名称")
    private String saleman;

    /**
     * 表 : MTC_WEB_VM_Customer
     * 对应字段 : DeptPsitn
     */
    @ApiModelProperty(value = "大部岗位号")
    private String deptpsitn;

    /**
     * 表 : MTC_WEB_VM_Customer
     * 对应字段 : Deptmet
     */
    @ApiModelProperty(value = "大部岗位名")
    private String deptmet;

}
