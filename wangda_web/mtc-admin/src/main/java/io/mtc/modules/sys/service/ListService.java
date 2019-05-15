package io.mtc.modules.sys.service;

import io.mtc.common.utils.PageUtils;
import io.mtc.modules.sys.entity.DropListEntity;

import java.util.List;

public interface ListService {

    PageUtils queryList(PageUtils procedure, String deptCode, String loginUserName, Integer pageIndex, Integer pageSize, String param) throws Exception;

    List<DropListEntity> queryList(PageUtils procedure, String filter, String loginUserName) throws Exception;
}
