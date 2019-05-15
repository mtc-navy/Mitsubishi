package io.mtc.modules.sap.dao;

import io.mtc.modules.sap.entity.BoundNoEntity;
import io.mtc.modules.sap.entity.CarNoEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarBoundDao {

    List<CarNoEntity> getCarNoList(@Param("CardCode") String cardCode,
                                   @Param("CarNo") String carNo,
                                   @Param("TaxDate") String taxDate,
                                    @Param("BPLId") String BPLId);


    List<BoundNoEntity> getBoundNoList(@Param("CardCode") String cardCode,
                                       @Param("CarNo") String carNo,
                                       @Param("TaxDate") String taxDate);

}
