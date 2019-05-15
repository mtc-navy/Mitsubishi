package io.mtc.modules.sap.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.mtc.common.exception.RRException;
import io.mtc.common.utils.Constant;
import io.mtc.common.utils.ObjectUtil;
import io.mtc.common.utils.PageUtils;
import io.mtc.common.utils.Query;
import io.mtc.modules.sap.dao.CustomerDao;
import io.mtc.modules.sap.dao.ItemPriceDao;
import io.mtc.modules.sap.dao.StockTransferDao;
import io.mtc.modules.sap.dto.StockTransferDto;
import io.mtc.modules.sap.entity.DistNumberEntity;
import io.mtc.modules.sap.entity.PrintTypeEntity;
import io.mtc.modules.sap.entity.StockTransferEntity;
import io.mtc.modules.sap.service.StockTransferService;
import io.mtc.modules.sys.shiro.ShiroUtils;
import io.mtc.servicelayer.dao.InventoryTransferRequestsDao;
import io.mtc.servicelayer.dao.StockTransfersDao;
import io.mtc.servicelayer.model.TransferRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("stockTransferService")
public class StockTransferServiceImpl extends ServiceImpl<StockTransferDao, StockTransferEntity> implements StockTransferService {

    /**
     * 用户名
     */
    private String UserName = "";
    @Autowired
    private StockTransferDao transferDao;
    @Autowired
    private InventoryTransferRequestsDao inventoryTransferRequestsDao;
    @Autowired
    private StockTransfersDao stockTransfersDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private ItemPriceDao itemPriceDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) throws Exception {

        //单据状态
        String docStatus = ObjectUtil.nullToEmpty((String) params.get("DocStatus")).trim();
        //分支
        String bplId = ObjectUtil.nullToEmpty((String) params.get("BPLId"));
        //开始日期
        String startDateStr = ObjectUtil.nullToEmpty((String) params.get("StartDate"));
        //结束日期
        String endDateStr = ObjectUtil.nullToEmpty((String) params.get("EndDate"));
        //用户名
        String userName = ShiroUtils.getUserEntity().getUsername().trim();
        //从仓库
        String fromWhsCode = ObjectUtil.nullToEmpty((String) params.get("fromWhsCode"));
        //到仓库
        String toWhsCode = ObjectUtil.nullToEmpty((String) params.get("toWhsCode"));
        params.put("UserName", userName);

        Page<StockTransferEntity> page = new Query<StockTransferEntity>(params).getPage();
        List<StockTransferEntity> stockTransferEntities = transferDao.queryList(
                bplId, docStatus, startDateStr, endDateStr, userName, fromWhsCode, toWhsCode);
        page.setRecords(stockTransferEntities);

        return new PageUtils(page);

    }

    @Override
    public void cancel(Long docEntry, String docStatus) throws Exception {
        if (docStatus.equals("R")) //申请单
            inventoryTransferRequestsDao.close(docEntry);
        else
            stockTransfersDao.cancel(docEntry);
    }

    @Override
    public BigInteger save(StockTransferDto stockTransferDto, boolean bRequest, boolean bEdit) throws Exception {
        BigInteger docEntry = stockTransferDto.getDocEntry();

        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setBPLID(stockTransferDto.getBplId());//分支
        transferRequest.setBPLName(stockTransferDto.getBplName());
        transferRequest.setTaxDate(stockTransferDto.getTaxDate());
        transferRequest.setDocDate(stockTransferDto.getTaxDate());
        transferRequest.setFromWarehouse(stockTransferDto.getFiller());
        transferRequest.setToWarehouse(stockTransferDto.getToWhsCode());
        transferRequest.setU_Creator(ShiroUtils.getUserEntity().getUsername());
        transferRequest.setU_CName(ShiroUtils.getUserEntity().getSapusername());
        transferRequest.setU_TrsType("W");
        transferRequest.setU_Driver(stockTransferDto.getDriver());
        transferRequest.setU_TranType(stockTransferDto.getTranType());
        transferRequest.setU_BusiType(stockTransferDto.getBusiType());
        transferRequest.setU_CarNo(stockTransferDto.getCarNo());
        transferRequest.setU_WORDREntry(stockTransferDto.getBoundNo());

        //行
        List<TransferRequest.StockTransferLine> lines = new ArrayList<>();
        for (int i = 0; i < stockTransferDto.getLines().size(); i++) {
            StockTransferDto.LineInfo lineInfo = stockTransferDto.getLines().get(i);

            TransferRequest.StockTransferLine stockTransferLine = new TransferRequest.StockTransferLine();
            stockTransferLine.setLineNum(i);
            stockTransferLine.setItemCode(lineInfo.getItemCode());
            stockTransferLine.setItemDescription(lineInfo.getItemName());

            if (StringUtils.isEmpty(lineInfo.getFromWarehouseCode())) {
                throw new RRException("第" + (i + 1) + "行从仓库不能为空");
            }
            if (StringUtils.isEmpty(lineInfo.getWarehouseCode())) {
                throw new RRException("第" + (i + 1) + "行到仓库不能为空");
            }
            stockTransferLine.setWarehouseCode(lineInfo.getWarehouseCode());
            stockTransferLine.setFromWarehouseCode(lineInfo.getFromWarehouseCode());

            if (lineInfo.getIsPackage().equals("Y")) {
                stockTransferLine.setFactor(lineInfo.getFactor1());
                stockTransferLine.setFactor2(lineInfo.getFactor2());
            } else {
                stockTransferLine.setQuantity(lineInfo.getQuantity());
            }

            if (!bRequest) {
                BigDecimal quantity = lineInfo.getQuantity();
                BigDecimal lastQty = quantity;

                //批次信息
                List<TransferRequest.StockTransferLine.BatchNumbers> batchNumbersList = new ArrayList<>();
                //获取批次号信息
                List<DistNumberEntity> distNumberEntityList = itemPriceDao.searchDistNumber(lineInfo.getItemCode(), stockTransferDto.getFiller());
                for (DistNumberEntity distNumberEntity : distNumberEntityList) {
                    //交货数量小于等于0时弹出循环
                    if (lastQty.compareTo(BigDecimal.ZERO) <= 0) break;

                    TransferRequest.StockTransferLine.BatchNumbers batchNumbers = new TransferRequest.StockTransferLine.BatchNumbers();
                    if (lastQty.compareTo(distNumberEntity.getQuantity()) >= 0) {
                        batchNumbers.setBatchNumber(distNumberEntity.getDistNumber());
                        batchNumbers.setQuantity(distNumberEntity.getQuantity());
                        lastQty = lastQty.subtract(distNumberEntity.getQuantity());
                    } else {
                        batchNumbers.setBatchNumber(distNumberEntity.getDistNumber());
                        batchNumbers.setQuantity(lastQty);
                        lastQty = BigDecimal.ZERO;
                    }
                    batchNumbersList.add(batchNumbers);
                }
                stockTransferLine.setBatchNumbers(batchNumbersList);
            }

            lines.add(stockTransferLine);
        }
        transferRequest.setStockTransferLines(lines);

        //修改保存
        if (bEdit) {
            if (bRequest)
                inventoryTransferRequestsDao.patch(docEntry, transferRequest);
            else
                stockTransfersDao.patch(docEntry, transferRequest);
        } else {
            TransferRequest result;
            if (bRequest) {
                result = (TransferRequest) inventoryTransferRequestsDao.save(transferRequest);
            } else {
                result = (TransferRequest) stockTransfersDao.save(transferRequest);
            }
            docEntry = result.getDocEntry();
        }
        return docEntry;
    }

    @Override
    public void update(StockTransferDto stockTransferDto, boolean bRequest) throws Exception {
        this.save(stockTransferDto, bRequest, true);
    }

    @Override
    public BigInteger save(StockTransferDto stockTransferDto, boolean bRequest) throws Exception {
        return this.save(stockTransferDto, bRequest, false);
    }

    @Override
    public StockTransferDto info(Long docEntry, String status) throws Exception {
        return getEditDto(docEntry, status);
    }

    @Override
    public Constant.PDFType print(Long docEntry, boolean bRequest) {
        PrintTypeEntity printTypeEntity = customerDao.getPrintType(docEntry);
        Constant.PDFType printType = Constant.PDFType.value(printTypeEntity.getPrintType());
        if (printType == Constant.PDFType.无) {
            throw new RRException("业务伙伴主数据[" + printTypeEntity.getCardCode() + "]" + printTypeEntity.getCardName()
                    + "未设定打印内容");
        }
        return printType;
    }

    /**
     * 根据DocEntry获取当前转储单信息
     *
     * @param docEntry
     * @param status
     * @return
     */
    private StockTransferDto getEditDto(Long docEntry, String status) throws Exception {
        StockTransferDto dto;
        //查询用过滤条件变量定义
        String userName;
        if (ShiroUtils.getUserEntity() != null) {
            userName = UserName;
        } else {
            userName = ShiroUtils.getUserEntity().getUsername().trim();
        }
        String filterValue = "";

        //查询主表数据
        boolean bRequest = status.equals("R"); //申请单
        dto = bRequest ? transferDao.searchReqMaster(docEntry) : transferDao.searchMaster(docEntry);

        //查询明细表数据
        List<StockTransferDto.LineInfo> lines = bRequest ? transferDao.searchReqDetail(docEntry, dto.getFiller()) : transferDao.searchDetail(docEntry, dto.getFiller());
        dto.setLines(lines);

        return dto;
    }
}
