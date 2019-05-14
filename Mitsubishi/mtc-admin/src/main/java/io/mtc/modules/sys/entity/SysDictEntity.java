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
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 数据字典
 */
@Data
@TableName("sys_dict")
@KeySequence("sys_dict_seq")
public class SysDictEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.INPUT)
    private Long id;
    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空")
    private String name;
    /**
     * 字典类型
     */
    @NotBlank(message = "字典类型不能为空")
    private String type;
    /**
     * 字典码
     */
    @NotBlank(message = "字典码不能为空")
    private String code;
    /**
     * 字典值
     */
    @NotBlank(message = "字典值不能为空")
    private String value;
    /**
     * 排序
     */
    private Integer orderNum;
    /**
     * 备注
     */
    private String remark;
    /**
     * 删除标记  -1：已删除  0：正常
     */
    @TableLogic
    private Integer delFlag;
}
