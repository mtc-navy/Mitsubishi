package io.mtc.modules.sys.service.impl;

import com.alibaba.fastjson.JSONObject;
import io.mtc.common.utils.Constant;
import io.mtc.common.utils.PageUtils;
import io.mtc.modules.sys.dao.ListDao;
import io.mtc.modules.sys.entity.DropListEntity;
import io.mtc.modules.sys.entity.SysDictEntity;
import io.mtc.modules.sys.service.ListService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 通用接口服务
 */
@Service("listService")
public class ListServiceImpl implements ListService {

    @Autowired
    private ListDao listDao;

    @Override
    public PageUtils queryList(PageUtils procedure, String deptCode, String loginUserName, Integer pageIndex, Integer pageSize, String param) throws Exception {
        //获取查询的存储过程名
        String qryProce = ((SysDictEntity) procedure.getList().get(0)).getValue();
        JSONObject jsonObject = JSONObject.parseObject(param);
        //获取参数数组
        Map<String, Object> paramMap = null;
        if (jsonObject != null) paramMap = jsonObject.getInnerMap();

        //构造查询分页信息和列表信息的存储过程
        String qrySizeSql = "CALL \"" + qryProce + "\" ('S','" + loginUserName + "'," + pageIndex + "," + pageSize;
        String qryListSql = "CALL \"" + qryProce + "\" ('L','" + loginUserName + "'," + pageIndex + "," + pageSize;
        if (paramMap != null) {
            for (int i = 0; i < paramMap.size(); i++) {
                String prmValue = String.valueOf(paramMap.get("param" + (i + 1)));
                if (prmValue == null || prmValue.equals("null") || prmValue.equals("NULL")) {
                    prmValue = Constant.EMPTY;
                }
                qrySizeSql = qrySizeSql + ",'" + prmValue + "'";
                qryListSql = qryListSql + ",'" + prmValue + "'";
            }
        }
        qrySizeSql = qrySizeSql + ")";
        qryListSql = qryListSql + ")";

        List<Map<String, Object>> sizeRst = listDao.queryPageList(qrySizeSql.replace(";", ""));
        List<Map<String, Object>> listRst = listDao.queryPageList(qryListSql.replace(";", ""));
        Integer totalCount = Integer.valueOf(sizeRst.get(0).get("totalCount").toString());
        Integer currPage = Integer.valueOf(sizeRst.get(0).get("currPage").toString());
        String fieldList = sizeRst.get(0).get("fieldList").toString();
        String lengthList = sizeRst.get(0).get("lengthList").toString();
        List<String> fields = Arrays.asList((fieldList.split(",")));
        List<String> lengths = Arrays.asList((lengthList.split(",")));

        return new PageUtils(listRst, totalCount, pageSize, currPage, fields, lengths);
    }

    @Override
    public List<DropListEntity> queryList(PageUtils procedure, String loginUserName, String param) throws Exception {
        //获取查询的存储过程名
        String qryProce = ((SysDictEntity) procedure.getList().get(0)).getValue();
        JSONObject jsonObject = JSONObject.parseObject(param);
        //获取参数数组
        Map<String, Object> paramMap = null;
        if (jsonObject != null) paramMap = jsonObject.getInnerMap();

        String qrySql = "CALL \"" + qryProce + "\" ('" + loginUserName+"'";
        if (paramMap != null) {
            for (int i = 0; i < paramMap.size(); i++) {
                String prmValue = String.valueOf(paramMap.get("param" + (i + 1)));
                if (prmValue == null || prmValue.equals("null") || prmValue.equals("NULL")) {
                    prmValue = Constant.EMPTY;
                }
                qrySql = qrySql + ",'" + prmValue + "'";
            }
        }
        qrySql = qrySql + ")";
        //String qrySql = "CALL " + qryProce + "('" + loginUserName + "','" + filter + "')";
        return listDao.queryDropList(qrySql.replace(";", ""));
    }
}
