package io.mtc.modules.sys.dao;

import io.mtc.modules.sys.entity.DropListEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 通用列表接口
 */
@Repository
public interface ListDao {

    List<Map<String, Object>> queryPageList(@Param("SQL") String sql);

    List<DropListEntity> queryDropList(@Param("SQL") String sql);
}
