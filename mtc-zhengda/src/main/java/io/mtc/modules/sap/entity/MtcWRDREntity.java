package io.mtc.modules.sap.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 提货单
 */
@Data
@TableName("\"MTC_WRDR\"")
@KeySequence("MTC_WRDR_SEQ")
@ApiModel(value = "提货单")
public class MtcWRDREntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 单据编号
     * 表 : MTC_WRDR
     * 对应字段 : DocEntry
     */
    @ApiModelProperty(value = "单据编号")
    @TableId(value = "\"DocEntry\"", type = IdType.INPUT)
    private Long docentry;

    /**
     * 客户编码
     * 表 : MTC_WRDR
     * 对应字段 : CardCode
     */
    @ApiModelProperty(value = "客户编码")
    @TableField(value = "\"CardCode\"")
    private String cardcode;

    /**
     * 客户名称
     * 表 : MTC_WRDR
     * 对应字段 : CardName
     */
    @ApiModelProperty(value = "客户名称")
    @TableField(value = "\"CardName\"")
    private String cardname;

    /**
     * 状态0-草稿，1-审批中，2-未批准，3-已生效，4-待过一磅，5-提货中，6-待过二磅，7-已出库，8-已作废，
     * 表 : MTC_WRDR
     * 对应字段 : DocStatus
     */
    @ApiModelProperty(value = "状态0-草稿，1-审批中，2-未批准，3-已生效，4-待过一磅，5-提货中，6-待过二磅，7-已出库，8-已作废，")
    @TableField(value = "\"DocStatus\"")
    private String docstatus;

    /**
     * 提货日期
     * 表 : MTC_WRDR
     * 对应字段 : EarliestDate
     */
    @ApiModelProperty(value = "提货日期")
    @TableField(value = "\"EarliestDate\"")
    private Date earliestdate;

    /**
     * 提供发票0-不开票，1-普通发票，2-专用发票，3-电子发票
     * 表 : MTC_WRDR
     * 对应字段 : IsInvoices
     */
    @ApiModelProperty(value = "提供发票0-不开票，1-普通发票，2-专用发票，3-电子发票")
    @TableField(value = "\"IsInvoices\"")
    private String isinvoices;

    /**
     * 开票状态0-未导出，1-已导出未开票，2-已开票
     * 表 : MTC_WRDR
     * 对应字段 : InvoiceTy
     */
    @ApiModelProperty(value = "开票状态0-未导出，1-已导出未开票，2-已开票")
    @TableField(value = "\"InvoiceTy\"")
    private String invoicety;

    /**
     * 车牌号
     * 表 : MTC_WRDR
     * 对应字段 : CarNum
     */
    @ApiModelProperty(value = "车牌号")
    @TableField(value = "\"CarNum\"")
    private String carnum;

    /**
     * 运输商编码
     * 表 : MTC_WRDR
     * 对应字段 : AgentCode
     */
    @ApiModelProperty(value = "运输商编码")
    @TableField(value = "\"AgentCode\"")
    private String agentcode;

    /**
     * 运输商名称
     * 表 : MTC_WRDR
     * 对应字段 : AgentName
     */
    @ApiModelProperty(value = "运输商名称")
    @TableField(value = "\"AgentName\"")
    private String agentname;

    /**
     * 运费单价
     * 表 : MTC_WRDR
     * 对应字段 : PerAgtPrice
     */
    @ApiModelProperty(value = "运费单价")
    @TableField(value = "\"PerAgtPrice\"")
    private BigDecimal peragtprice;

    /**
     * 牌价合计
     * 表 : MTC_WRDR
     * 对应字段 : SumPAmt
     */
    @ApiModelProperty(value = "牌价合计")
    @TableField(value = "\"SumPAmt\"")
    private BigDecimal sumpamt;

    /**
     * 现场折扣合计
     * 表 : MTC_WRDR
     * 对应字段 : SumBDisc
     */
    @ApiModelProperty(value = "现场折扣合计")
    @TableField(value = "\"SumBDisc\"")
    private BigDecimal sumbdisc;

    /**
     * 现场运补合计
     * 表 : MTC_WRDR
     * 对应字段 : SumEDisc
     */
    @ApiModelProperty(value = "现场运补合计")
    @TableField(value = "\"SumEDisc\"")
    private BigDecimal sumedisc;

    /**
     * 使用折扣金额
     * 表 : MTC_WRDR
     * 对应字段 : UsedDisc
     */
    @ApiModelProperty(value = "使用折扣金额")
    @TableField(value = "\"UsedDisc\"")
    private BigDecimal useddisc;

    /**
     * 单据金额
     * 表 : MTC_WRDR
     * 对应字段 : DocTotal
     */
    @ApiModelProperty(value = "单据金额")
    @TableField(value = "\"DocTotal\"")
    private BigDecimal doctotal;

    /**
     * 收款金额
     * 表 : MTC_WRDR
     * 对应字段 : GatherAmt
     */
    @ApiModelProperty(value = "收款金额")
    @TableField(value = "\"GatherAmt\"")
    private BigDecimal gatheramt;

    /**
     * 欠款金额
     * 表 : MTC_WRDR
     * 对应字段 : DebtAmt
     */
    @ApiModelProperty(value = "欠款金额")
    @TableField(value = "\"DebtAmt\"")
    private BigDecimal debtamt;

    /**
     * 预计还款日
     * 表 : MTC_WRDR
     * 对应字段 : RefundDate
     */
    @ApiModelProperty(value = "预计还款日")
    @TableField(value = "\"RefundDate\"")
    private Date refunddate;

    /**
     * 业务员编号
     * 表 : MTC_WRDR
     * 对应字段 : SalePsitn
     */
    @ApiModelProperty(value = "业务员编号")
    @TableField(value = "\"SalePsitn\"")
    private String salepsitn;

    /**
     * 业务员名称
     * 表 : MTC_WRDR
     * 对应字段 : SaleMan
     */
    @ApiModelProperty(value = "业务员名称")
    @TableField(value = "\"SaleMan\"")
    private String saleman;

    /**
     * 大部岗位号
     * 表 : MTC_WRDR
     * 对应字段 : DeptPsitn
     */
    @ApiModelProperty(value = "大部岗位号")
    @TableField(value = "\"DeptPsitn\"")
    private String deptpsitn;

    /**
     * 大部岗位名
     * 表 : MTC_WRDR
     * 对应字段 : Deptmet
     */
    @ApiModelProperty(value = "大部岗位名")
    @TableField(value = "\"Deptmet\"")
    private String deptmet;

    /**
     * 插单原因
     * 表 : MTC_WRDR
     * 对应字段 : UrgencyCd
     */
    @ApiModelProperty(value = "插单原因")
    @TableField(value = "\"UrgencyCd\"")
    private String urgencycd;

    /**
     * 生成模式0-依据计划单，1-手工创建
     * 表 : MTC_WRDR
     * 对应字段 : DocType
     */
    @ApiModelProperty(value = "生成模式0-依据计划单，1-手工创建")
    @TableField(value = "\"DocType\"")
    private String doctype;

    /**
     * 创建人
     * 表 : MTC_WRDR
     * 对应字段 : Creator
     */
    @ApiModelProperty(value = "创建人")
    @TableField(value = "\"Creator\"")
    private String creator;

    /**
     * 创建日期
     * 表 : MTC_WRDR
     * 对应字段 : CreateDate
     */
    @ApiModelProperty(value = "创建日期")
    @TableField(value = "\"CreateDate\"")
    private Date createdate;

    /**
     * 创建时间
     * 表 : MTC_WRDR
     * 对应字段 : CreateTime
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "\"CreateTime\"")
    private String createtime;

    /**
     * 更新人
     * 表 : MTC_WRDR
     * 对应字段 : Updater
     */
    @ApiModelProperty(value = "更新人")
    @TableField(value = "\"Updater\"")
    private String updater;

    /**
     * 更新日期
     * 表 : MTC_WRDR
     * 对应字段 : UpdateDate
     */
    @ApiModelProperty(value = "更新日期")
    @TableField(value = "\"UpdateDate\"")
    private Date updatedate;

    /**
     * 更新时间
     * 表 : MTC_WRDR
     * 对应字段 : UpdateTime
     */
    @ApiModelProperty(value = "更新时间")
    @TableField(value = "\"UpdateTime\"")
    private String updatetime;

    /**
     * OA流程编号
     * 表 : MTC_WRDR
     * 对应字段 : OAReqstNum
     */
    @ApiModelProperty(value = "OA流程编号")
    @TableField(value = "\"OAReqstNum\"")
    private String oareqstnum;

    /**
     * OA审批日期
     * 表 : MTC_WRDR
     * 对应字段 : OAApprDate
     */
    @ApiModelProperty(value = "OA审批日期")
    @TableField(value = "\"OAApprDate\"")
    private Date oaapprdate;

    /**
     * OA审批意见
     * 表 : MTC_WRDR
     * 对应字段 : OAPropose
     */
    @ApiModelProperty(value = "OA审批意见")
    @TableField(value = "\"OAPropose\"")
    private String oapropose;


}