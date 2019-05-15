package io.mtc.servicelayer;

import io.mtc.servicelayer.dao.OrdersDao;
import io.mtc.servicelayer.model.Order;
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
public class OrderDaoTest {

    @Autowired
    private OrdersDao ordersDao;

    @Test
    public void getOrderById() throws Exception {
        Order order = ordersDao.getObjectById(4240L,Order.class);
        System.out.println(order);
    }

}
