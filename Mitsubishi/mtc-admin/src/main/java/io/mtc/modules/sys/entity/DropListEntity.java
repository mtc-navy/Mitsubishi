package io.mtc.modules.sys.entity;

import lombok.Data;

@Data
public class DropListEntity {

    private Long id;
    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;
    /**
     * 简称
     */
    private String shortname;
    /**
     * 类型
     */
    private String type;
    /**
     * 自定义字段1
     */
    private String def1;
    /**
     * 自定义字段2
     */
    private String def2;
    /**
     * 自定义字段3
     */
    private String def3;
    /**
     * 自定义字段4
     */
    private String def4;
    /**
     * 自定义字段5
     */
    private String def5;
}
