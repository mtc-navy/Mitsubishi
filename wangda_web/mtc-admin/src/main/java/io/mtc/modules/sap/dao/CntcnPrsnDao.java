package io.mtc.modules.sap.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.mtc.modules.sap.entity.CntctPrsnEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CntcnPrsnDao extends BaseMapper<CntctPrsnEntity> {

    List<CntctPrsnEntity> search(@Param("CardCode") String cardCode, @Param("UserName") String userName,
                                 @Param("FilterValue") String filterValue);
}
