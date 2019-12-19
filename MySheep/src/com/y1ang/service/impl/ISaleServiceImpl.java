package com.y1ang.service.impl;

import com.y1ang.dao.SaleMapper;
import com.y1ang.entity.Page;
import com.y1ang.entity.Sale;
import com.y1ang.service.ISaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author y1ang
 * @Data 2018年10月15日-下午2:46:35
 * @Description 卖羊账单实现类
 */

@Service
public class ISaleServiceImpl implements ISaleService {

    @Autowired
    private SaleMapper dao;

    @Override
    public List<Sale> findSaleRecord(Page page) {
        List<Sale> saleList = dao.findSaleRecord(page);
        saleList.forEach(a -> {
            if (!StringUtils.isEmpty(a.getSaleDate())) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = format.parse(a.getSaleDate());
                    SimpleDateFormat formatDisplay = new SimpleDateFormat("yy/MM/dd");
                    a.setSaleDate(formatDisplay.format(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        return saleList;

    }

    @Override
    public int getSaleCount(int batchNumber) {
        return dao.getSaleCount(batchNumber);
    }

    @Override
    public Sale finSaleRecordByID(int saleID) {
        return dao.finSaleRecordByID(saleID);
    }

    @Override
    public int insertSaleRecord(Sale sale) {
        return dao.insertSaleRecord(sale);
    }

    @Override
    public int updateSaleRecord(Sale sale) {
        return dao.updateSaleRecord(sale);
    }

    @Override
    public int deleteSaleRecord(int saleID) {
        return dao.deleteSaleRecord(saleID);
    }

}
