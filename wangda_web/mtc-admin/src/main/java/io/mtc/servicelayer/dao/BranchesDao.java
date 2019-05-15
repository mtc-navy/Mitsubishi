package io.mtc.servicelayer.dao;

import io.mtc.servicelayer.annotation.Path;
import io.mtc.servicelayer.model.Branches;
import org.springframework.stereotype.Component;

/**
 * 分支
 * <p>
 * Created by navy.jiang on 2018/9/4.
 */
@Path(value = "/Branches")
@Component
public class BranchesDao extends BaseServiceLayerDao<Branches> {
}
