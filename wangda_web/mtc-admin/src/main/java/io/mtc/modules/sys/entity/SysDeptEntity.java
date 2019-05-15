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

import com.baomidou.mybatisplus.annotations.*;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 部门管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-20 15:23:47
 */
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
	@TableField(exist=false)
	private String parentName;
	@TableField(exist=false)
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

	/**
	 * 所属大区
	 */
	@TableField("COMAREID")
	private String comareId;

	/**
	 * 大区名称
	 */
	@TableField("COMARENAME")
	private String comareName;

	/**
	 * 经营单元代码
	 */
	@TableField("BUSDEPTID")
	private String busDeptId;

	/**
	 * 经营单元名称
	 */
	@TableField("BUSDEPTNAME")
	private String busDeptName;

	/**
	 * 产品线代码
	 */
	@TableField("PROLINEID")
	private String proLineId;

	/**
	 * 产品线名称
	 */
	@TableField("PROLINENAME")
	private String proLineName;


	/**
	 * 分类编号
	 */
	@TableField("CLASSCODE")
	private String classCode;

	private Date createdDate;

	private String createdTime;

	private Date updatedDate;

	private String updatedTime;

	private String creator;

	private String updater;

	/**
	 * ztree属性
	 */
	@TableField(exist=false)
	private Boolean open;
	@TableField(exist=false)
	private List<?> list;


	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getDeptId() {
		return deptId;
	}
	/**
	 * 设置：上级部门ID，一级部门为0
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：上级部门ID，一级部门为0
	 */
	public Long getParentId() {
		return parentId;
	}
	/**
	 * 设置：部门名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：部门名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：排序
	 */
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	/**
	 * 获取：排序
	 */
	public Integer getOrderNum() {
		return orderNum;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	public String getHrid() {
		return hrid;
	}

	public void setHrid(String hrid) {
		this.hrid = hrid;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getComareId() {
		return comareId;
	}

	public void setComareId(String comareId) {
		this.comareId = comareId;
	}

	public String getComareName() {
		return comareName;
	}

	public void setComareName(String comareName) {
		this.comareName = comareName;
	}

	public String getBusDeptId() {
		return busDeptId;
	}

	public void setBusDeptId(String busDeptId) {
		this.busDeptId = busDeptId;
	}

	public String getBusDeptName() {
		return busDeptName;
	}

	public void setBusDeptName(String busDeptName) {
		this.busDeptName = busDeptName;
	}

	public String getProLineId() {
		return proLineId;
	}

	public void setProLineId(String proLineId) {
		this.proLineId = proLineId;
	}

	public String getProLineName() {
		return proLineName;
	}

	public void setProLineName(String proLineName) {
		this.proLineName = proLineName;
	}
}
