package io.mtc.modules.sap.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.mtc.modules.sap.dao.MtcWRDRDao;
import io.mtc.modules.sap.entity.MtcWRDREntity;
import io.mtc.modules.sap.service.MtcWRDRService;
import org.springframework.stereotype.Service;

/**
 * 提货单
 */
@Service("mtcWRDRService")
public class MtcWRDRServiceImpl extends ServiceImpl<MtcWRDRDao, MtcWRDREntity> implements MtcWRDRService {

}
