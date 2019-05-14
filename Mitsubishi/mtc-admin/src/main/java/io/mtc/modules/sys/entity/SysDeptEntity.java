/**
 * Copyright 2018 MTC http://www.mtcsys.com/
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.mtc.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.KeySequence;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 部门管理
 */
@Data
@TableName("mtc_sys_dept")
@KeySequence("mtc_sys_dept_seq")
public class SysDeptEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //部门ID
    @TableId(type = IdType.INPUT)
    private Long deptId;

    //上级部门ID，一级部门为0
    private Long parentId;

    //部门名称
    private String name;

    //上级部门名称
    @TableField(exist = false)
    private String parentName;

    @TableField(exist = false)
    private String parentCode;

    //排序
    private Integer orderNum;

    private Integer delFlag;

    /**
     * 部门类型
     */
    @TableField("DEPTCODE")
    private String deptCode;

    /**
     * 类型
     */
    private String type;

    /**
     * 部门类型
     */
    private String deptType;

    /**
     * HR ID 号
     */
    private String hrid;

    private Date createdDate;

    private String createdTime;

    private Date updatedDate;

    private String updatedTime;

    private String creator;

    private String updater;

    /**
     * ztree属性
     */
    @TableField(exist = false)
    private Boolean open;

    @TableField(exist = false)
    private List<?> list;

}
