package io.mtc.servicelayer.model;

import java.io.Serializable;

public class DraftToDocument implements Serializable {

    private Document Document;

    public DraftToDocument.Document getDocument() {
        return Document;
    }

    public void setDocument(DraftToDocument.Document document) {
        Document = document;
    }

    public static class Document {

        private String DocDueDate;

        public String getDocDueDate() {
            return DocDueDate;
        }

        public void setDocDueDate(String docDueDate) {
            DocDueDate = docDueDate;
        }

        public String getDocEntry() {
            return DocEntry;
        }

        public void setDocEntry(String docEntry) {
            DocEntry = docEntry;
        }

        private String DocEntry;
    }

}
