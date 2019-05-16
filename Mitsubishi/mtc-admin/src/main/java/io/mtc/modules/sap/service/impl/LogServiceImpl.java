package io.mtc.modules.sap.service.impl;

import io.mtc.common.exception.RRException;
import io.mtc.common.utils.DateUtils;
import io.mtc.common.utils.ListUtils;
import io.mtc.modules.sap.entity.MtcOLOG;
import io.mtc.modules.sap.entity.MtcOMMP;
import io.mtc.modules.sap.service.LogService;
import io.mtc.modules.sap.service.MtcOLOGService;
import io.mtc.modules.sap.service.MtcOMMPService;
import io.mtc.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志
 */
@Service("logService")
public class LogServiceImpl implements LogService {

    @Autowired
    private MtcOMMPService mtcOMMPService;
    @Autowired
    private MtcOLOGService mtcOLOGService;

    /**
     * 日志（新增配置表时用）
     *
     * @param CfgDocEntry
     * @param ORDREntry
     * @param Msg(为空时候不需要插入日志表数据)
     */
    @Override
    @Transactional
    public void Log(Long CfgDocEntry, Long ORDREntry, String Msg) throws RRException {
        //构造查询条件
        Map<String, Object> key = new HashMap<>();
        key.put("CfgDocEntry", CfgDocEntry);

        //构建关系表
        MtcOMMP mtcOMMP;
        List<MtcOMMP> mtcOMMPList = mtcOMMPService.selectByMap(key);
        if (ListUtils.isNotEmpty(mtcOMMPList)) {
            //已存在时更新日志表信息
            mtcOMMP = mtcOMMPList.get(0);

            //插入错误日志:无错误是返回日志ID为0
            MtcOLOG mtcOLOG = insertErrorLog(Msg);
            mtcOMMP.setLogid(mtcOLOG.getId());
            mtcOMMPService.updateById(mtcOMMP);
        } else {
            //插入错误日志:无错误是返回日志ID为0
            MtcOLOG mtcOLOG = insertErrorLog(Msg);

            //插入关系表数据
            mtcOMMP = new MtcOMMP();
            mtcOMMP.setCfgdocentry(CfgDocEntry);//配置表Entry
            mtcOMMP.setOrdrentry(ORDREntry);//销售订单Entry
            mtcOMMP.setLogid(mtcOLOG.getId());
            mtcOMMPService.insert(mtcOMMP);
        }
    }

    /**
     * 插入错误日志
     *
     * @param msg
     * @return
     */
    private MtcOLOG insertErrorLog(String msg) throws RRException {
        MtcOLOG mtcOLOG = new MtcOLOG();

        //无错误日志返回空
        if (StringUtils.isEmpty(msg)) return mtcOLOG;

        //存在错误日志先插入日志信息
        mtcOLOG.setMessage(msg);
        mtcOLOG.setCreator(ShiroUtils.getUserId().toString());
        mtcOLOG.setCreatetime(DateUtils.getDateString());
        mtcOLOGService.insert(mtcOLOG);
        return mtcOLOG;
    }

}
