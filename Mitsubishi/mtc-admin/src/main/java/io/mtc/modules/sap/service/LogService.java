package io.mtc.modules.sap.service;

import io.mtc.common.exception.RRException;

/**
 * 日志表
 */
public interface LogService {

    /**
     * 日志（Web新增配置表和销售订单时用）
     *
     * @param CfgDocEntry
     * @param ORDREntry
     * @param Msg
     */
    void Log(Long CfgDocEntry, Long ORDREntry, String Msg) throws RRException;

}
