package io.mtc.modules.sap.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.mtc.common.utils.*;
import io.mtc.modules.sap.dao.SaleReturnDao;
import io.mtc.modules.sap.dto.SaleInvoiceEditDto;
import io.mtc.modules.sap.entity.SaleReturnEntity;
import io.mtc.modules.sap.service.SaleInvoiceService;
import io.mtc.modules.sap.service.SaleReturnService;
import io.mtc.modules.sys.shiro.ShiroUtils;
import io.mtc.servicelayer.dao.ReturnsDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("saleReturnService")
public class SaleReturnServiceImpl extends ServiceImpl<SaleReturnDao, SaleReturnEntity> implements SaleReturnService {

    @Autowired
    private SaleReturnDao saleReturnDao;

    @Autowired
    private ReturnsDao returnsDao;

    @Autowired
    private SaleInvoiceService saleInvoiceService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) throws Exception {

        //客户编码或者客户名称（模糊查询）
        String cardCode = ObjectUtil.nullToEmpty((String) params.get("CustomerNo")).trim();
        //单据状态
        String docStatus = ObjectUtil.nullToEmpty((String) params.get("DocStatus")).trim();
        //开始日期
        String startDateStr = ObjectUtil.nullToEmpty((String) params.get("StartDate"));
        Date startDate = null;
        if (StringUtils.isNotEmpty(startDateStr)) {
            startDate = DateUtils.stringToDate(startDateStr, DateUtils.DATE_PATTERN);
        }
        //结束日期
        String endDateStr = ObjectUtil.nullToEmpty((String) params.get("EndDate"));
        Date endDate = null;
        if (StringUtils.isNotEmpty(endDateStr)) {
            endDate = DateUtils.stringToDate(endDateStr, DateUtils.DATE_PATTERN);
        }
        //用户名
        String userName = ShiroUtils.getUserEntity().getUsername().trim();
        //交货单号
        String takeGoodNo = ObjectUtil.nullToEmpty((String) params.get("DeliveryNum")).trim();

        Page<SaleReturnEntity> page = new Query<SaleReturnEntity>(params).getPage();
        page.setRecords(saleReturnDao.queryList(page,
                cardCode, docStatus, startDate, endDate, userName, takeGoodNo));

        return new PageUtils(page);

    }

    /**
     * 退货草稿取消
     *
     * @param docEntry
     */
    @Override
    public void cancel(Long docEntry) throws Exception {
        returnsDao.cancel(docEntry);
    }

    @Override
    public Integer save(SaleInvoiceEditDto saleInvoiceEditDto) throws Exception {
        saleInvoiceEditDto.setIsOrder("N");
        return saleInvoiceService.save(saleInvoiceEditDto, false, Constant.DraftType.RETURN, false);
    }

    @Override
    public void update(SaleInvoiceEditDto saleInvoiceEditDto) throws Exception {
        saleInvoiceEditDto.setIsOrder("N");
        saleInvoiceService.save(saleInvoiceEditDto, false, Constant.DraftType.RETURN, true);
    }

    @Override
    public SaleInvoiceEditDto info(Long docEntry) throws Exception {
        SaleInvoiceEditDto dto;
        //查询主表数据
        dto = saleReturnDao.searchMater(docEntry.toString());

        //查询明细表数据
        List<SaleInvoiceEditDto.ItemInfo> itemInfos = saleReturnDao.searchDetail(docEntry.toString());
        dto.setItemInfos(itemInfos);
        //合计总金额
        BigDecimal sumAmt = BigDecimal.ZERO;
        for (SaleInvoiceEditDto.ItemInfo itemInfo : itemInfos) {
            sumAmt = sumAmt.add(itemInfo.getPayAmt());
        }
        return dto;
    }
}
