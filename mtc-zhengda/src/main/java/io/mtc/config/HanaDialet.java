package io.mtc.config;

import com.baomidou.mybatisplus.extension.plugins.pagination.DialectModel;
import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.IDialect;

public class HanaDialet implements IDialect {

    public static final HanaDialet INSTANCE = new HanaDialet();

    @Override
    public DialectModel buildPaginationSql(String originalSql, long offset, long limit) {
        String sql = originalSql + " limit " + FIRST_MARK;
        boolean existOffset = false;
        if (offset > 0) {
            existOffset = true;
            sql += (" offset " + SECOND_MARK);
        }
        DialectModel model = new DialectModel(sql, limit, offset);
        return existOffset ? model.setConsumerChain() : model.setConsumer(true);
    }
}
