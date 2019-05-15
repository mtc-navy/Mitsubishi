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

package io.mtc.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.mtc.common.utils.Constant;
import io.mtc.common.utils.DateUtils;
import io.mtc.modules.sap.entity.BudgetOrgTreeEntity;
import io.mtc.modules.sys.dao.SysDeptDao;
import io.mtc.modules.sys.entity.SysDeptEntity;
import io.mtc.modules.sys.service.SysDeptService;
import io.mtc.modules.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


@SuppressWarnings("ALL")
@Service("sysDeptService")
public class SysDeptServiceImpl extends ServiceImpl<SysDeptDao, SysDeptEntity> implements SysDeptService {

    @Autowired
    private SysDeptDao sysDeptDao;

    @Override
    public List<SysDeptEntity> queryList(Map<String, Object> params) {
        List<SysDeptEntity> deptList =
                this.selectList(new EntityWrapper<SysDeptEntity>()
                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER)));

        List<SysDeptEntity> allList = selectList(null);
        Map<Long, SysDeptEntity> allMap = new HashMap<>();
        allList.forEach(entity -> allMap.put(entity.getDeptId(), entity));

        for (SysDeptEntity sysDeptEntity : deptList) {
            SysDeptEntity parentDeptEntity = allMap.get(sysDeptEntity.getParentId());
            if (parentDeptEntity != null) {
                sysDeptEntity.setParentName(parentDeptEntity.getName());
                sysDeptEntity.setParentCode(parentDeptEntity.getDeptCode());
            }
        }
        return deptList;
    }

    @Override
    public SysDeptEntity selectById(Serializable id) {
        SysDeptEntity entity = super.selectById(id);
        if (entity == null) {
            return entity;
        }
        Long parentId = entity.getParentId();
        SysDeptEntity parent = super.selectById(parentId);
        if (parent != null) {
            entity.setParentCode(parent.getDeptCode());
            entity.setParentName(parent.getName());
        }
        return entity;
    }

    @Override
    public List<Long> queryDetpIdList(Long parentId) {
        return baseMapper.queryDetpIdList(parentId);
    }

    @Override
    public List<Long> getSubDeptIdList(Long deptId) {
        //部门及子部门ID列表
        List<Long> deptIdList = new ArrayList<>();

        //获取子部门ID
        List<Long> subIdList = queryDetpIdList(deptId);
        getDeptTreeList(subIdList, deptIdList);

        return deptIdList;
    }

    @Override
    public boolean updateById(SysDeptEntity entity) {
        entity.setUpdater(ShiroUtils.getUserEntity().getUsername());
        entity.setUpdatedDate(DateUtils.getDate(DateUtils.DATE_PATTERN));
        entity.setUpdatedTime(DateUtils.format(new Date(), DateUtils.TIME_PATTERN));
        return super.updateById(entity);
    }

    @Override
    @Transactional
    public void init() {

        delete(new EntityWrapper<SysDeptEntity>());

        //从SAP中获取dept数据
        List<SysDeptEntity> depts = sysDeptDao.queryDeptFromSap();

        //从表中获取dept数据
        List<SysDeptEntity> originalDepts = selectList(null);

        //事业部
        Map<String,SysDeptEntity> divisionMap = new HashMap<>();
        //公司
        Map<String, SysDeptEntity> companyMap = new HashMap<>();
        //部门
        Map<String, SysDeptEntity> deptMap = new HashMap<>();
        //经营单元
        Map<String, SysDeptEntity> buzUnitMap = new HashMap<>();

        //表中数据map
        Map<String, SysDeptEntity> originalMap = new HashMap<>();

        for (SysDeptEntity dept : depts) {
            if(dept.getType().equals(Constant.OrganizationType.DIVISION.getType())){
                divisionMap.put(dept.getDeptCode(),dept);
            } else if (dept.getType().equals(Constant.OrganizationType.COMPANY.getType())) {
                companyMap.put(dept.getDeptCode(), dept);
            } else if (dept.getType().equals(Constant.OrganizationType.DEPARTMENT.getType())) {
                deptMap.put(dept.getDeptCode(), dept);
            } else if (dept.getType().equals(Constant.OrganizationType.SALEUNIT.getType())) {
                buzUnitMap.put(dept.getDeptCode(), dept);
            }
        }

        originalDepts.forEach(entity -> originalMap.put(entity.getDeptCode(), entity));

        //事业部数据
        insertOrUpdate(originalMap,divisionMap,null);

        //公司数据
        insertOrUpdate(originalMap, companyMap, divisionMap);

        //部门数据
        insertOrUpdate(originalMap, deptMap, companyMap);

        //经营单元数据
        insertOrUpdate(originalMap, buzUnitMap, deptMap);

    }

    @Override
    public List<BudgetOrgTreeEntity> queryBudgetOrgTree(String OrgLevel, String FitSale) {
        String username = ShiroUtils.getUserEntity().getUsername();
        return sysDeptDao.queryBudgetOrgTree(username, OrgLevel, FitSale);
    }

    private void insertOrUpdate(Map<String, SysDeptEntity> originalMap, Map<String, SysDeptEntity> currentMap, Map<String, SysDeptEntity> parentMap) {
        List<SysDeptEntity> list = currentMap.entrySet().stream().map(deptEntry -> {
            SysDeptEntity entity = deptEntry.getValue();
            if (parentMap != null && entity.getParentCode() != null && !"0".equals(entity.getParentCode())) {
                SysDeptEntity father = parentMap.get(entity.getParentCode());
                entity.setParentId(father.getDeptId());
            } else {
                entity.setParentId(0L);
            }
            return entity;
        }).collect(Collectors.toList());

        //需要更新的list
        List<SysDeptEntity> updateList = new ArrayList<>();
        //需要新增的list
        List<SysDeptEntity> insertList = new ArrayList<>();

        list.forEach(entity -> {
            //如果原来表中有数据，则更新
            SysDeptEntity oldEntity = originalMap.get(entity.getDeptCode());
            if (oldEntity != null) {
                oldEntity.setUpdater(ShiroUtils.getUserEntity().getUsername());
                oldEntity.setUpdatedDate(DateUtils.getDate(DateUtils.DATE_PATTERN));
                oldEntity.setUpdatedTime(DateUtils.format(new Date(), DateUtils.TIME_PATTERN));
                oldEntity.setName(entity.getName());
                oldEntity.setType(entity.getType());
                oldEntity.setDeptCode(entity.getDeptCode());
                oldEntity.setClassCode(entity.getClassCode());
                oldEntity.setDeptType(entity.getDeptType());
                oldEntity.setComareId(entity.getComareId());
                oldEntity.setComareName(entity.getComareName());
                oldEntity.setBusDeptId(entity.getBusDeptId());
                oldEntity.setBusDeptName(entity.getBusDeptName());
                oldEntity.setProLineId(entity.getProLineId());
                oldEntity.setProLineName(entity.getProLineName());
                if (parentMap != null && entity.getParentCode() != null && !"0".equals(entity.getParentCode())) {
                    SysDeptEntity father = parentMap.get(entity.getParentCode());
                    oldEntity.setParentId(father.getDeptId());
                } else {
                    oldEntity.setParentId(0L);
                }
                currentMap.put(oldEntity.getDeptCode(), oldEntity);
                updateList.add(oldEntity);
            } else {
                //如果原来表中没有数据，则新增
                entity.setDelFlag(0);
                entity.setCreator(ShiroUtils.getUserEntity().getUsername());
                entity.setCreatedDate(DateUtils.getDate(DateUtils.DATE_PATTERN));
                entity.setCreatedTime(DateUtils.format(new Date(), DateUtils.TIME_PATTERN));
                insertList.add(entity);
            }
        });

        if (!CollectionUtils.isEmpty(insertList)) {
            insertBatch(insertList);
        }
        if (!CollectionUtils.isEmpty(updateList)) {
            updateBatchById(updateList);
        }
    }

    /**
     * 递归
     */
    private void getDeptTreeList(List<Long> subIdList, List<Long> deptIdList) {
        for (Long deptId : subIdList) {
            List<Long> list = queryDetpIdList(deptId);
            if (list.size() > 0) {
                getDeptTreeList(list, deptIdList);
            }

            deptIdList.add(deptId);
        }
    }
}
