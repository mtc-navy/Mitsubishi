package io.mtc.modules.sap.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.mtc.modules.sap.entity.BPLEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BPLDao extends BaseMapper<BPLEntity> {

    List<BPLEntity> search(@Param("CardCode") String cardCode,
                           @Param("DataType") String dataType,
                           @Param("UserName") String userName);

    List<BPLEntity> searchWithKey(@Param("CardCode") String cardCode,
                                  @Param("DataType") String dataType,
                                  @Param("UserName") String userName,
                                  @Param("FilterValue") String filterValue);

    BPLEntity selectByKey(@Param("BPLId") String BPLId);

    List<BPLEntity> searchByUser(@Param("UserName") String userName, @Param("FilterValue") String filterValue);
}
