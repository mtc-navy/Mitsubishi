package io.mtc.modules.sap.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 提货单子表
 */
@Data
@TableName("\"MTC_WRDR1\"")
@ApiModel(value = "提货单子表")
public class MtcWRDR1Entity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 单据编号
     * 表 : MTC_WRDR1
     * 对应字段 : DocEntry
     */
    @ApiModelProperty(value = "单据编号")
    @TableField(value = "\"DocEntry\"")
    private Long docentry;

    /**
     * 行号
     * 表 : MTC_WRDR1
     * 对应字段 : LineNum
     */
    @ApiModelProperty(value = "行号")
    @TableField(value = "\"LineNum\"")
    private Integer linenum;

    /**
     * 物料编码
     * 表 : MTC_WRDR1
     * 对应字段 : ItemCode
     */
    @ApiModelProperty(value = "物料编码")
    @TableField(value = "\"ItemCode\"")
    private String itemcode;

    /**
     * 物料名称
     * 表 : MTC_WRDR1
     * 对应字段 : ItemName
     */
    @ApiModelProperty(value = "物料名称")
    @TableField(value = "\"ItemName\"")
    private String itemname;

    /**
     * 规格（标重）
     * 表 : MTC_WRDR1
     * 对应字段 : PerPacKg
     */
    @ApiModelProperty(value = "规格（标重）")
    @TableField(value = "\"PerPacKg\"")
    private BigDecimal perpackg;

    /**
     * 是否标包
     * 表 : MTC_WRDR1
     * 对应字段 : IsStdPack
     */
    @ApiModelProperty(value = "是否标包")
    @TableField(value = "\"IsStdPack\"")
    private String isstdpack;

    /**
     * 包数
     * 表 : MTC_WRDR1
     * 对应字段 : Packages
     */
    @ApiModelProperty(value = "包数")
    @TableField(value = "\"Packages\"")
    private BigDecimal packages;

    /**
     * 数量
     * 表 : MTC_WRDR1
     * 对应字段 : Quantity
     */
    @ApiModelProperty(value = "数量")
    @TableField(value = "\"Quantity\"")
    private BigDecimal quantity;

    /**
     * 库存单位
     * 表 : MTC_WRDR1
     * 对应字段 : InvntryUom
     */
    @ApiModelProperty(value = "库存单位")
    @TableField(value = "\"InvntryUom\"")
    private String invntryuom;

    /**
     * 基地仓库
     * 表 : MTC_WRDR1
     * 对应字段 : WhsCode
     */
    @ApiModelProperty(value = "基地仓库")
    @TableField(value = "\"WhsCode\"")
    private String whscode;

    /**
     * 仓库名称
     * 表 : MTC_WRDR1
     * 对应字段 : WhsName
     */
    @ApiModelProperty(value = "仓库名称")
    @TableField(value = "\"WhsName\"")
    private String whsname;

    /**
     * 可用库存
     * 表 : MTC_WRDR1
     * 对应字段 : AvarialQty
     */
    @ApiModelProperty(value = "可用库存")
    @TableField(value = "\"AvarialQty\"")
    private BigDecimal avarialqty;

    /**
     * 牌价
     * 表 : MTC_WRDR1
     * 对应字段 : BasePrice
     */
    @ApiModelProperty(value = "牌价")
    @TableField(value = "\"BasePrice\"")
    private BigDecimal baseprice;

    /**
     * 现场折扣
     * 表 : MTC_WRDR1
     * 对应字段 : BaseDisc
     */
    @ApiModelProperty(value = "现场折扣")
    @TableField(value = "\"BaseDisc\"")
    private BigDecimal basedisc;

    /**
     * 现场运补
     * 表 : MTC_WRDR1
     * 对应字段 : BaseExpn
     */
    @ApiModelProperty(value = "现场运补")
    @TableField(value = "\"BaseExpn\"")
    private BigDecimal baseexpn;

    /**
     * 开票价
     * 表 : MTC_WRDR1
     * 对应字段 : Pirce
     */
    @ApiModelProperty(value = "开票价")
    @TableField(value = "\"Pirce\"")
    private BigDecimal pirce;

    /**
     * 税码
     * 表 : MTC_WRDR1
     * 对应字段 : VatGroup
     */
    @ApiModelProperty(value = "税码")
    @TableField(value = "\"VatGroup\"")
    private String vatgroup;

    /**
     * 行金额
     * 表 : MTC_WRDR1
     * 对应字段 : LineTotal
     */
    @ApiModelProperty(value = "行金额")
    @TableField(value = "\"LineTotal\"")
    private BigDecimal linetotal;

    /**
     * 行状态
     * 表 : MTC_WRDR1
     * 对应字段 : LineStatus
     */
    @ApiModelProperty(value = "行状态")
    @TableField(value = "\"LineStatus\"")
    private String linestatus;

    /**
     * 业务员编号
     * 表 : MTC_WRDR1
     * 对应字段 : SaelPsitn
     */
    @ApiModelProperty(value = "业务员编号")
    @TableField(value = "\"SaelPsitn\"")
    private String saelpsitn;

    /**
     * 业务员名称
     * 表 : MTC_WRDR1
     * 对应字段 : SaleMan
     */
    @ApiModelProperty(value = "业务员名称")
    @TableField(value = "\"SaleMan\"")
    private String saleman;

    /**
     * 区域主管
     * 表 : MTC_WRDR1
     * 对应字段 : XQMan
     */
    @ApiModelProperty(value = "区域主管")
    @TableField(value = "\"XQMan\"")
    private String xqman;

    /**
     * 大区主管
     * 表 : MTC_WRDR1
     * 对应字段 : DQMan
     */
    @ApiModelProperty(value = "大区主管")
    @TableField(value = "\"DQMan\"")
    private String dqman;

    /**
     * 部主管
     * 表 : MTC_WRDR1
     * 对应字段 : DBMan
     */
    @ApiModelProperty(value = "部主管")
    @TableField(value = "\"DBMan\"")
    private String dbman;

    /**
     * 计划单号
     * 表 : MTC_WRDR1
     * 对应字段 : BaseEntry
     */
    @ApiModelProperty(value = "计划单号")
    @TableField(value = "\"BaseEntry\"")
    private Integer baseentry;

    /**
     * 计划单行号
     * 表 : MTC_WRDR1
     * 对应字段 : BaseLine
     */
    @ApiModelProperty(value = "计划单行号")
    @TableField(value = "\"BaseLine\"")
    private Integer baseline;

    /**
     * 销售订单号
     * 表 : MTC_WRDR1
     * 对应字段 : TargetEntry
     */
    @ApiModelProperty(value = "销售订单号")
    @TableField(value = "\"TargetEntry\"")
    private Integer targetentry;

    /**
     * 销售订单行号
     * 表 : MTC_WRDR1
     * 对应字段 : TargetLine
     */
    @ApiModelProperty(value = "销售订单行号")
    @TableField(value = "\"TargetLine\"")
    private Integer targetline;

}