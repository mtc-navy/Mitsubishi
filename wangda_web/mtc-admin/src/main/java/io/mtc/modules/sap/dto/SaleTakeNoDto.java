package io.mtc.modules.sap.dto;

public class SaleTakeNoDto {

    private Long docEntry;
    private Long docNum;
    private String aliasName;
    private String driver;
    private String docStatus;
    private String takeNo;

    public Long getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(Long docEntry) {
        this.docEntry = docEntry;
    }

    public Long getDocNum() {
        return docNum;
    }

    public void setDocNum(Long docNum) {
        this.docNum = docNum;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }

    public String getTakeNo() {
        return takeNo;
    }

    public void setTakeNo(String takeNo) {
        this.takeNo = takeNo;
    }
}
