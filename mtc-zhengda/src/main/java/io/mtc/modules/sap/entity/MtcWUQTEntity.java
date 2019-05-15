package io.mtc.modules.sap.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 计划单
 */
@Data
@TableName("\"MTC_WUQT\"")
@KeySequence("MTC_WUQT_SEQ")
@ApiModel(value = "计划单")
public class MtcWUQTEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 单据编号
     * 表 : MTC_WUQT
     * 对应字段 : DocEntry
     */
    @ApiModelProperty(value = "单据编号")
    @TableId(value = "\"DocEntry\"", type = IdType.INPUT)
    private Long docentry;

    /**
     * 客户编码
     * 表 : MTC_WUQT
     * 对应字段 : CardCode
     */
    @ApiModelProperty(value = "客户编码")
    @TableField(value = "\"CardCode\"")
    private String cardcode;

    /**
     * 客户名称
     * 表 : MTC_WUQT
     * 对应字段 : CardName
     */
    @ApiModelProperty(value = "客户名称")
    @TableField(value = "\"CardName\"")
    private String cardname;

    /**
     * 状态0-可修改，1-不可修改，2-已取消，3-已结算
     * 表 : MTC_WUQT
     * 对应字段 : DocStatus
     */
    @ApiModelProperty(value = "状态0-可修改，1-不可修改，2-已取消，3-已结算")
    @TableField(value = "\"DocStatus\"")
    private String docstatus;

    /**
     * 业务员编号
     * 表 : MTC_WUQT
     * 对应字段 : SalePsitn
     */
    @ApiModelProperty(value = "业务员编号")
    @TableField(value = "\"SalePsitn\"")
    private String salepsitn;

    /**
     * 业务员名称
     * 表 : MTC_WUQT
     * 对应字段 : SaleMan
     */
    @ApiModelProperty(value = "业务员名称")
    @TableField(value = "\"SaleMan\"")
    private String saleman;

    /**
     * 大部岗位号
     * 表 : MTC_WUQT
     * 对应字段 : DeptPsitn
     */
    @ApiModelProperty(value = "大部岗位号")
    @TableField(value = "\"DeptPsitn\"")
    private String deptpsitn;

    /**
     * 大部岗位名
     * 表 : MTC_WUQT
     * 对应字段 : Deptmet
     */
    @ApiModelProperty(value = "大部岗位名")
    @TableField(value = "\"Deptmet\"")
    private String deptmet;

    /**
     * 创建人
     * 表 : MTC_WUQT
     * 对应字段 : Creator
     */
    @ApiModelProperty(value = "创建人")
    @TableField(value = "\"Creator\"")
    private String creator;

    /**
     * 创建日期
     * 表 : MTC_WUQT
     * 对应字段 : CreateDate
     */
    @ApiModelProperty(value = "创建日期")
    @TableField(value = "\"CreateDate\"")
    private Date createdate;

    /**
     * 创建时间
     * 表 : MTC_WUQT
     * 对应字段 : CreateTime
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "\"CreateTime\"")
    private String createtime;

    /**
     * 更新人
     * 表 : MTC_WUQT
     * 对应字段 : Updater
     */
    @ApiModelProperty(value = "更新人")
    @TableField(value = "\"Updater\"")
    private String updater;

    /**
     * 更新日期
     * 表 : MTC_WUQT
     * 对应字段 : UpdateDate
     */
    @ApiModelProperty(value = "更新日期")
    @TableField(value = "\"UpdateDate\"")
    private Date updatedate;
}