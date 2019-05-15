package io.mtc.modules.sap.entity;

/**
 * 预算组织树entity
 */
public class BudgetOrgTreeEntity {

    private Long docEntry;

    private String code;

    private String parentCode;

    private String name;

    private String companyCode;

    private String deptCode;

    private String buziCode;

    private String saleCode;

    private String type;

    private String typeName;

    public Long getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(Long docEntry) {
        this.docEntry = docEntry;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getBuziCode() {
        return buziCode;
    }

    public void setBuziCode(String buziCode) {
        this.buziCode = buziCode;
    }

    public String getSaleCode() {
        return saleCode;
    }

    public void setSaleCode(String saleCode) {
        this.saleCode = saleCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
