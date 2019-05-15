package io.mtc.servicelayer.model;

import io.mtc.common.utils.DateUtils;

import java.util.Date;

/**
 * Created by majun on 10/31/16.
 */
public class Session {

    private String SessionId;

    private Integer SessionTimeout;

    private Date expireTime;

    public String getSessionId() {
        return SessionId;
    }

    public void setSessionId(String sessionId) {
        SessionId = sessionId;
    }

    public Integer getSessionTimeout() {
        return SessionTimeout;
    }

    public void setSessionTimeout(Integer sessionTimeout) {
        SessionTimeout = sessionTimeout;
        expireTime = DateUtils.addDateMinutes(new Date(),sessionTimeout-5);
    }

    public Date getExpireTime() {
        return expireTime;
    }

    @Override
    public String toString() {
        return "Session{" +
                "SessionId='" + SessionId + '\'' +
                ", SessionTimeout=" + SessionTimeout +
                '}';
    }
}
