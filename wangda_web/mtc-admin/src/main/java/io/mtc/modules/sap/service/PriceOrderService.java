package io.mtc.modules.sap.service;

import io.mtc.common.exception.RRException;
import io.mtc.modules.sap.dto.DesOrderDto;
import io.mtc.modules.sap.dto.PriceOrderDto;

public interface PriceOrderService {

    PriceOrderDto info(Integer docentry) throws RRException;

    PriceOrderDto save(PriceOrderDto priceOrderDto, boolean isEdit) throws RRException;

    void cancel(Integer docentry) throws RRException;

    void close(Integer docentry) throws RRException;

    void receipt(Integer docentry) throws RRException;

    void cancelrpt(Integer docentry) throws RRException;
}
