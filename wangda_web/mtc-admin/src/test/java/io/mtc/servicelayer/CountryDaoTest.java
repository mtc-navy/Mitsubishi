package io.mtc.servicelayer;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import io.mtc.common.utils.PageUtils;
import io.mtc.servicelayer.dao.CountryDao;
import io.mtc.servicelayer.model.Country;
import io.mtc.servicelayer.model.QueryParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by majun on 2018/9/3.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CountryDaoTest{

    @Autowired
    private CountryDao countryDao;

    @Test
    public void getCountryById() throws Exception {
        Country country = countryDao.getObjectById("TE",Country.class);
        System.out.println(country);
    }

    @Test
    public void deleteById() throws Exception {
        countryDao.delete("TE");
    }

    @Test
    public void save() throws Exception {
        Country country = new Country();
        country.setName("Test country");
        country.setCode("TE");
        country = countryDao.save(country);
        System.out.println(country);
    }

    @Test
    public void update() throws Exception {
        Country country = new Country();
        country.setName("test country2");
        country.setAddressFormat(1);
        countryDao.update("TE",country);

        country = countryDao.getObjectById("TE",Country.class);
        System.out.println(country);
    }

    @Test
    public void patch() throws Exception {
        Country country = new Country();
        country.setName("test country3");
        //country.setAddressFormat(1);
        countryDao.patch("TE",country);

        country = countryDao.getObjectById("TE",Country.class);
        System.out.println(country);
    }

    @Test
    public void queryList() throws Exception {
        List<Country> list = countryDao.queryList(Country.class);
        System.out.println(list);
    }

    @Test
    public void queryListParam() throws Exception {
        QueryParam queryParam = new QueryParam.Builder()
                .select("Code,Name")
                .orderby("Code desc")
                //.filter("Code eq 'ZW'")
                //.skip(10)
                .top(50)
                .build();


        List<Country> list = countryDao.queryList(Country.class,queryParam);
        System.out.println(list.size());
        System.out.println(list);
    }

    @Test
    public void queryPage() throws Exception {
        QueryParam queryParam = new QueryParam.Builder()
                .select("Code,Name")
                .orderby("Code desc")
                .build();
        Page<Country> page = countryDao.queryPage(1,10,Country.class,queryParam);
        System.out.println(JSONObject.toJSONString(new PageUtils(page)));

        page = countryDao.queryPage(2,10,Country.class,queryParam);
        System.out.println(JSONObject.toJSONString(new PageUtils(page)));
    }

}
