package io.mtc.modules.sap.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.mtc.modules.sap.entity.CustomerEntity;
import io.mtc.modules.sap.entity.PrintTypeEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerDao extends BaseMapper<CustomerEntity> {

    List<CustomerEntity> getCustomerList(@Param("UserName") String userName, @Param("DataType") String dataType,
                                         @Param("FilterValue") String filterValue);

    PrintTypeEntity getPrintType(@Param("DocEntry") Long DocEntry);
}
