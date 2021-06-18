package com.sense.newots.object.entity;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ChangeBatchInfo {
    private int oldId;
    private int newId;

    public int getOldId() {
        return this.oldId;
    }

    public void setOldId(int oldId) {
        this.oldId = oldId;
    }

    public int getNewId() {
        return this.newId;
    }

    public void setNewId(int newId) {
        this.newId = newId;
    }
}
