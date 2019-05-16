package io.mtc.modules.sap.entity;

import com.baomidou.mybatisplus.annotations.KeySequence;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/*
 * 关系映射表
 */
@Data
@ApiModel(value = "关系映射表")
@TableName("\"MTC_OMMP\"")
@KeySequence("\"MTC_OMMP_SEQ\"")
public class MtcOMMP implements Serializable {
    /**
     * ID
     * 表 : MTC_OMMP
     * 对应字段 : ID
     */
    @ApiModelProperty(value = "ID")
    @TableField(value = "\"ID\"")
    private Long id;

    /**
     * 配置表单号
     * 表 : MTC_OMMP
     * 对应字段 : CfgDocEntry
     */
    @ApiModelProperty(value = "配置表单号")
    @TableField(value = "\"CfgDocEntry\"")
    private Long cfgdocentry;

    /**
     * SAP销售单号
     * 表 : MTC_OMMP
     * 对应字段 : ORDREntry
     */
    @ApiModelProperty(value = "SAP销售单号")
    @TableField(value = "\"ORDREntry\"")
    private Long ordrentry;

    /**
     * SAP交货单号
     * 表 : MTC_OMMP
     * 对应字段 : ODLNEntry
     */
    @ApiModelProperty(value = "SAP交货单号")
    @TableField(value = "\"ODLNEntry\"")
    private Long odlnentry;

    /**
     * SAP应收发票单号
     * 表 : MTC_OMMP
     * 对应字段 : OINVEntry
     */
    @ApiModelProperty(value = "SAP应收发票单号")
    @TableField(value = "\"OINVEntry\"")
    private Long oinventry;

    /**
     * 日志ID
     * 表 : MTC_OMMP
     * 对应字段 : LogID
     */
    @ApiModelProperty(value = "日志ID")
    @TableField(value = "\"LogID\"")
    private Long logid;

    private static final long serialVersionUID = 1L;
}