package io.mtc.modules.sap.entity;

import com.baomidou.mybatisplus.annotations.KeySequence;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/*
 * 日志表
 */
@Data
@ApiModel(value = "日志表")
@TableName("\"MTC_OLOG\"")
@KeySequence("\"MTC_OLOG_SEQ\"")
public class MtcOLOG implements Serializable {
    /**
     * ID
     * 表 : MTC_OLOG
     * 对应字段 : ID
     */
    @ApiModelProperty(value = "ID")
    @TableField(value = "\"ID\"")
    private Long id;

    /**
     * Message
     * 表 : MTC_OLOG
     * 对应字段 : Message
     */
    @ApiModelProperty(value = "Message")
    @TableField(value = "\"Message\"")
    private String message;

    /**
     * 创建时间
     * 表 : MTC_OLOG
     * 对应字段 : CreateTime
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "\"CreateTime\"")
    private String createtime;

    /**
     * 创建人
     * 表 : MTC_OLOG
     * 对应字段 : Creator
     */
    @ApiModelProperty(value = "创建人")
    @TableField(value = "\"Creator\"")
    private String creator;

    private static final long serialVersionUID = 1L;
}