package com.y1ang.service.impl;

import com.y1ang.dao.OtherMapper;
import com.y1ang.entity.Other;
import com.y1ang.entity.Page;
import com.y1ang.service.IOtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class IOtherServiceImpl implements IOtherService {

    @Autowired
    private OtherMapper dao;

    /**
     * 查询所有其他记录信息
     */
    @Override
    public List<Other> findAllOtherRecord(Page page) {
        List<Other> otherList = dao.findAllOtherRecord(page);
        otherList.forEach(a -> {
            if (!StringUtils.isEmpty(a.getRecordDate())) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = format.parse(a.getRecordDate());
                    SimpleDateFormat formatDisplay = new SimpleDateFormat("yy/MM/dd");
                    a.setRecordDate(formatDisplay.format(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        return otherList;
    }

    @Override
    public int addOtherRecord(Other other) {
        return dao.addOtherRecord(other);
    }

    @Override
    public int updateOtherRecord(Other other) {
        return dao.updateOtherRecord(other);
    }

    @Override
    public int deleteOtherRecord(int recordID) {
        return dao.deleteOtherRecord(recordID);
    }

    @Override
    public Other findOtherRecordByID(int recordID) {
        return dao.findOtherRecordByID(recordID);
    }

    @Override
    public int getOtherCount(int batchNumber) {
        return dao.getOtherCount(batchNumber);
    }

}
