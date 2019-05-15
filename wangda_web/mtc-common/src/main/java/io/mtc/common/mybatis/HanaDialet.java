package io.mtc.common.mybatis;

import com.baomidou.mybatisplus.plugins.pagination.IDialect;

/**
 * Created by majun on 2018/8/30.
 */
public class HanaDialet implements IDialect {

    public static final HanaDialet INSTANCE = new HanaDialet();

    @Override
    public String buildPaginationSql(String originalSql, int offset, int limit) {
        StringBuilder sql = new StringBuilder(originalSql);
        sql.append(" limit ").append(limit);
        if (offset > 0) {
            sql.append(" offset ").append(offset);
        }
        return sql.toString();
    }

}
