package com.sense.newots.object.entity;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ImportResult {
    private String result;
    private int sucRows;

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getSucRows() {
        return this.sucRows;
    }

    public void setSucRows(int sucRows) {
        this.sucRows = sucRows;
    }
}

