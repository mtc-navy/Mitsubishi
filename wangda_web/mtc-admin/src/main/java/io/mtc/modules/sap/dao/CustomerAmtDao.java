package io.mtc.modules.sap.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.mtc.modules.sap.entity.BPLEntity;
import io.mtc.modules.sap.entity.CustomerAmtEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerAmtDao extends BaseMapper<BPLEntity> {

    List<CustomerAmtEntity> search(@Param("BPLId") String bplid,
                                   @Param("CardCode") String cardCode);

    List<CustomerAmtEntity> searchOther(@Param("BPLId") String bplid,
                                        @Param("CardCode") String cardCode,
                                        @Param("DocEntry") Long docEntry);

}
