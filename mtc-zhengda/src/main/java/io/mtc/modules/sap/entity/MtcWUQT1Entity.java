package io.mtc.modules.sap.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 计划单子表
 */
@Data
@TableName("\"MTC_WUQT1\"")
@ApiModel(value = "计划单子表")
public class MtcWUQT1Entity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 单据编号
     * 表 : MTC_WUQT1
     * 对应字段 : DocEntry
     */
    @ApiModelProperty(value = "单据编号")
    @TableField(value = "\"DocEntry\"")
    private Long docentry;

    /**
     * 行号
     * 表 : MTC_WUQT1
     * 对应字段 : LineNum
     */
    @ApiModelProperty(value = "行号")
    @TableField(value = "\"LineNum\"")
    private Integer linenum;

    /**
     * 物料编码
     * 表 : MTC_WUQT1
     * 对应字段 : ItemCode
     */
    @ApiModelProperty(value = "物料编码")
    @TableField(value = "\"ItemCode\"")
    private String itemcode;

    /**
     * 物料名称
     * 表 : MTC_WUQT1
     * 对应字段 : ItemName
     */
    @ApiModelProperty(value = "物料名称")
    @TableField(value = "\"ItemName\"")
    private String itemname;

    /**
     * 规格（标重）
     * 表 : MTC_WUQT1
     * 对应字段 : PerPacKg
     */
    @ApiModelProperty(value = "规格（标重）")
    @TableField(value = "\"PerPacKg\"")
    private BigDecimal perpackg;

    /**
     * 包数
     * 表 : MTC_WUQT1
     * 对应字段 : Packages
     */
    @ApiModelProperty(value = "包数")
    @TableField(value = "\"Packages\"")
    private BigDecimal packages;

    /**
     * 数量
     * 表 : MTC_WUQT1
     * 对应字段 : Quantity
     */
    @ApiModelProperty(value = "数量")
    @TableField(value = "\"Quantity\"")
    private BigDecimal quantity;

    /**
     * 库存单位
     * 表 : MTC_WUQT1
     * 对应字段 : InvntryUom
     */
    @ApiModelProperty(value = "库存单位")
    @TableField(value = "\"InvntryUom\"")
    private String invntryuom;

    /**
     * 基地仓库
     * 表 : MTC_WUQT1
     * 对应字段 : WhsCode
     */
    @ApiModelProperty(value = "基地仓库")
    @TableField(value = "\"WhsCode\"")
    private String whscode;

    /**
     * 仓库名称
     * 表 : MTC_WUQT1
     * 对应字段 : WhsName
     */
    @ApiModelProperty(value = "仓库名称")
    @TableField(value = "\"WhsName\"")
    private String whsname;

    /**
     * 提货日期
     * 表 : MTC_WUQT1
     * 对应字段 : EarliestDate
     */
    @ApiModelProperty(value = "提货日期")
    @TableField(value = "\"EarliestDate\"")
    private Date earliestdate;

    /**
     * 已提货量
     * 表 : MTC_WUQT1
     * 对应字段 : WRDRQty
     */
    @ApiModelProperty(value = "已提货量")
    @TableField(value = "\"WRDRQty\"")
    private BigDecimal wrdrqty;

    /**
     * 行状态
     * 表 : MTC_WUQT1
     * 对应字段 : LineStatus
     */
    @ApiModelProperty(value = "行状态")
    @TableField(value = "\"LineStatus\"")
    private String linestatus;

    /**
     * 允许被生产引用
     * 表 : MTC_WUQT1
     * 对应字段 : IsUseForPro
     */
    @ApiModelProperty(value = "允许被生产引用")
    @TableField(value = "\"IsUseForPro\"")
    private String isuseforpro;

    /**
     * 生产引用状态
     * 表 : MTC_WUQT1
     * 对应字段 : ForProStatus
     */
    @ApiModelProperty(value = "生产引用状态")
    @TableField(value = "\"ForProStatus\"")
    private String forprostatus;

    /**
     * 生产计划单号
     * 表 : MTC_WUQT1
     * 对应字段 : TargetEntry
     */
    @ApiModelProperty(value = "生产计划单号")
    @TableField(value = "\"TargetEntry\"")
    private Integer targetentry;

    /**
     * 生产计划单行号
     * 表 : MTC_WUQT1
     * 对应字段 : TargetLine
     */
    @ApiModelProperty(value = "生产计划单行号")
    @TableField(value = "\"TargetLine\"")
    private Integer targetline;

}