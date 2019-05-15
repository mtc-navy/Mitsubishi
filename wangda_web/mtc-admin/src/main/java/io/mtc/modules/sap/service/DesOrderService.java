package io.mtc.modules.sap.service;

import io.mtc.common.exception.RRException;
import io.mtc.modules.sap.dto.DesOrderDto;

public interface DesOrderService {

    DesOrderDto info(Integer docentry) throws RRException;

    DesOrderDto save(DesOrderDto desOrderDto, boolean isEdit) throws RRException;

    void cancel(Integer docentry) throws RRException;

    void close(Integer docentry) throws RRException;

    void receipt(Integer docentry) throws RRException;

    void cancelrpt(Integer docentry) throws RRException;
}
