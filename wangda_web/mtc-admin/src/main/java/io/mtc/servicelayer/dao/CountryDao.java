package io.mtc.servicelayer.dao;

import io.mtc.servicelayer.annotation.Path;
import io.mtc.servicelayer.model.Country;
import org.springframework.stereotype.Component;

/**
 *
 * Created by majun on 2018/9/3.
 */
@Path(value = "/Countries")
@Component
public class CountryDao extends BaseServiceLayerDao<Country> {


}
