package io.mtc.modules.sap.service;

import com.baomidou.mybatisplus.service.IService;
import io.mtc.common.utils.Constant;
import io.mtc.common.utils.PageUtils;
import io.mtc.modules.sap.dto.StockTransferDto;
import io.mtc.modules.sap.entity.StockTransferEntity;

import java.math.BigInteger;
import java.util.Map;

public interface StockTransferService extends IService<StockTransferEntity> {

    /**
     * 查询转储列表
     *
     * @param params
     * @return
     * @throws Exception
     */
    PageUtils queryPage(Map<String, Object> params) throws Exception;

    /**
     * 取消单据
     *
     * @param docEntry
     * @throws Exception
     */
    void cancel(Long docEntry, String docStatus) throws Exception;

    /**
     * 保存转储单
     *
     * @param stockTransferDto
     * @throws Exception
     */
    BigInteger save(StockTransferDto stockTransferDto, boolean bRequest, boolean bEdit) throws Exception;

    /**
     * 保存转储单
     *
     * @param stockTransferDto
     * @param bRequest
     * @return
     * @throws Exception
     */
    BigInteger save(StockTransferDto stockTransferDto, boolean bRequest) throws Exception;

    /**
     * 更新转储申请单
     *
     * @param stockTransferDto
     * @return
     */
    void update(StockTransferDto stockTransferDto, boolean bRequest) throws Exception;

    /**
     * 修改时获取转储单信息
     *
     * @param docEntry
     * @return
     * @throws Exception
     */
    StockTransferDto info(Long docEntry, String status) throws Exception;

    /**
     * 打印
     *
     * @param docEntry
     * @return
     */
    Constant.PDFType print(Long docEntry, boolean bRequest);

}
