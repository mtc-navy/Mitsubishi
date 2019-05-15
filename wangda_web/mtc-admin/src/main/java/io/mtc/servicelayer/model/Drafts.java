package io.mtc.servicelayer.model;

import java.io.Serializable;

public class Drafts extends Order implements Serializable {
    private String DocObjectCode;

    public String getDocObjectCode() {
        return DocObjectCode;
    }

    public void setDocObjectCode(String docObjectCode) {
        DocObjectCode = docObjectCode;
    }
}
