package io.mtc.modules.sap.dto;

public class SalesUpdateDto {

    private Long DocEntry;

    private Integer LineNum;

    private String SrcWhsCode;

    private String TrgtWhsCode;

    public Long getDocEntry() {
        return DocEntry;
    }

    public void setDocEntry(Long docEntry) {
        DocEntry = docEntry;
    }

    public Integer getLineNum() {
        return LineNum;
    }

    public void setLineNum(Integer lineNum) {
        LineNum = lineNum;
    }

    public String getSrcWhsCode() {
        return SrcWhsCode;
    }

    public void setSrcWhsCode(String srcWhsCode) {
        SrcWhsCode = srcWhsCode;
    }

    public String getTrgtWhsCode() {
        return TrgtWhsCode;
    }

    public void setTrgtWhsCode(String trgtWhsCode) {
        TrgtWhsCode = trgtWhsCode;
    }
}
