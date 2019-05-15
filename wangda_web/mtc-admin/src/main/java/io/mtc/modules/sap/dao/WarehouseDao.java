package io.mtc.modules.sap.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.mtc.modules.sap.entity.WarehouseEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseDao extends BaseMapper<WarehouseEntity> {

    /**
     * 根据分支得到成品仓库
     * @param bplid
     * @return
     */
    List<WarehouseEntity> searchCPByBPL(@Param("BPLId") String bplid, @Param("FilterValue") String fileterValue);

    /**
     * 根据分支得到办事处仓库
     * @param bplid
     * @return
     */
    List<WarehouseEntity> searchBSCByBPL(@Param("BPLId") String bplid, @Param("FilterValue") String fileterValue);

    /**
     * 根据用户得到所有仓库
     * @param userName
     * @return
     */
    List<WarehouseEntity> searchByUser(@Param("BPLId") String bplid, @Param("UserName") String userName, @Param("FilterValue") String fileterValue);

    /**
     * 根据用户得到办事处和成品仓库
     * @param userName
     * @return
     */
    List<WarehouseEntity> searchCBByUser(@Param("BPLId") String bplid, @Param("UserName") String userName, @Param("FilterValue") String fileterValue);
}
