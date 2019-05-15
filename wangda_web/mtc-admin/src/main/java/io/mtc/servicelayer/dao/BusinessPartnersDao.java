package io.mtc.servicelayer.dao;

import io.mtc.servicelayer.annotation.Path;
import io.mtc.servicelayer.model.BusinessPartners;
import io.mtc.servicelayer.model.QueryParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务伙伴
 * <p>
 * Created by navy.jiang on 2018/9/4.
 */
@Path(value = "/BusinessPartners")
@Component
public class BusinessPartnersDao extends BaseServiceLayerDao<BusinessPartners> {

    private static final Log logger = LogFactory.getLog(BusinessPartnersDao.class);

    /**
     * 获取客户信息(客户选择框用)
     *
     * @return
     */
    public List<BusinessPartners> qryCustomerList(String filer) {
        List<BusinessPartners> businessPartnersList = new ArrayList<>();
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\"CardType\"='S' ");
            if (!StringUtils.isEmpty(filer)) {
                stringBuilder.append("AND (\"CardCode\" LIKE '%'||'" + filer + "'||'%' OR ");
                stringBuilder.append("\"CardName\" LIKE '%'||'" + filer + "'||'%') ");
            }
            QueryParam.Builder builder = new QueryParam.Builder();
            builder.select(stringBuilder.toString());
            QueryParam param = new QueryParam(builder);
            businessPartnersList = this.queryList(BusinessPartners.class, param);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return businessPartnersList;
    }
}
