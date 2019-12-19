package com.y1ang.service.impl;

import com.y1ang.dao.VaccineMapper;
import com.y1ang.entity.Page;
import com.y1ang.entity.Vaccine;
import com.y1ang.service.IVaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author y1ang
 * @Data 2018年10月13日-下午5:42:20
 * @Description 疫苗账单服务层实现类
 */
@Service
public class IVaccineServiceImpl implements IVaccineService {

    @Autowired
    private VaccineMapper dao;

    @Override
    public List<Vaccine> findAllVaccine(Page page) {
        List<Vaccine> vaccineList = dao.findAllVaccine(page);
        vaccineList.forEach(a -> {
            if (!StringUtils.isEmpty(a.getVaccineDate())) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = format.parse(a.getVaccineDate());
                    SimpleDateFormat formatDisplay = new SimpleDateFormat("yy/MM/dd");
                    a.setVaccineDate(formatDisplay.format(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        return vaccineList;
    }

    @Override
    public int insertVaccineRecord(Vaccine vaccine) {
        return dao.insertVaccineRecord(vaccine);
    }

    @Override
    public int updateVaccineRecord(Vaccine vaccine) {
        return dao.updateVaccineRecord(vaccine);
    }

    @Override
    public int deleteVaccineRecord(int vaccineID) {
        return dao.deleteVaccineRecord(vaccineID);
    }

    @Override
    public Vaccine findVaccineByID(int vaccineID) {
        return dao.findVaccineByID(vaccineID);
    }

    @Override
    public int getVaccineCount(int batchNumber) {
        return dao.getVaccineCount(batchNumber);
    }

}
