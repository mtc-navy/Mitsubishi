package io.mtc.modules.sap.dao;

import io.mtc.modules.sap.form.CustomerForm;
import io.mtc.modules.sap.form.DiscountForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户列表
 */
@Mapper
public interface CustomerDao {

    List<CustomerForm> list(@Param("UserId") Long UserId);

    List<DiscountForm> discount(@Param("UserId") Long userId,
                                @Param("CardCode") String cardCode,
                                @Param("DocEntry") String docEntry);
}
