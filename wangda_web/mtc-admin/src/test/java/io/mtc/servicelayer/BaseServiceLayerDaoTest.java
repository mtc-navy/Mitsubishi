package io.mtc.servicelayer;

import io.mtc.servicelayer.dao.BaseServiceLayerDao;
import io.mtc.servicelayer.model.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by majun on 2018/9/3.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseServiceLayerDaoTest{

    @Autowired
    private BaseServiceLayerDao baseServiceLayerDao;

    @Test
    public void loginTest() throws Exception {
        Session session = baseServiceLayerDao.login();
        System.out.println(session);
    }

}
