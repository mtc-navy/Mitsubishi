package io.mtc.common.mybatis;

import com.baomidou.mybatisplus.incrementer.IKeyGenerator;

/**
 * Created by majun on 2018/8/30.
 */
public class HanaKeyGenerator implements IKeyGenerator {

    public HanaKeyGenerator(){

    }

    @Override
    public String executeSql(String incrementerName) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append(incrementerName);
        sql.append(".NEXTVAL FROM DUMMY");
        return sql.toString();
    }
}
