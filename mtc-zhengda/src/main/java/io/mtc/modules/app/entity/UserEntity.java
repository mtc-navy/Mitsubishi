package io.mtc.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户
 */
@Data
@TableName("@MTC_WUSR")
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 表 : @MTC_WUSR
     * 对应字段 : Code
     */
    private String code;

    /**
     * 表 : @MTC_WUSR
     * 对应字段 : Name
     */
    private String name;

    /**
     * 表 : @MTC_WUSR
     * 对应字段 : DocEntry
     */
    private Long docentry;

    /**
     * 表 : @MTC_WUSR
     * 对应字段 : Canceled
     */
    private String canceled;

    /**
     * 表 : @MTC_WUSR
     * 对应字段 : Object
     */
    private String object;

    /**
     * 表 : @MTC_WUSR
     * 对应字段 : LogInst
     */
    private Integer loginst;

    /**
     * 表 : @MTC_WUSR
     * 对应字段 : UserSign
     */
    private Integer usersign;

    /**
     * 表 : @MTC_WUSR
     * 对应字段 : Transfered
     */
    private String transfered;

    /**
     * 表 : @MTC_WUSR
     * 对应字段 : CreateDate
     */
    private Date createdate;

    /**
     * 表 : @MTC_WUSR
     * 对应字段 : CreateTime
     */
    private Short createtime;

    /**
     * 表 : @MTC_WUSR
     * 对应字段 : UpdateDate
     */
    private Date updatedate;

    /**
     * 表 : @MTC_WUSR
     * 对应字段 : UpdateTime
     */
    private Short updatetime;

    /**
     * 表 : @MTC_WUSR
     * 对应字段 : DataSource
     */
    private String datasource;

    /**
     * 表 : @MTC_WUSR
     * 对应字段 : U_PassWord
     */
    private String uPassword;

    /**
     * 表 : @MTC_WUSR
     * 对应字段 : U_OACode
     */
    private String uOacode;

    /**
     * 表 : @MTC_WUSR
     * 对应字段 : U_SAPPistn
     */
    private String uSappistn;

    /**
     * 表 : @MTC_WUSR
     * 对应字段 : U_WeChat
     */
    private String uWechat;

    /**
     * 表 : @MTC_WUSR
     * 对应字段 : U_IsActive
     */
    private String uIsactive;

    /**
     * 表 : @MTC_WUSR
     * 对应字段 : U_empId
     */
    private String uEmpid;
}
