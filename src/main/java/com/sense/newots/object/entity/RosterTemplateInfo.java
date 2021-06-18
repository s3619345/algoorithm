package com.sense.newots.object.entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class RosterTemplateInfo {
    private int id;
    private String name;
    private String descb;
    private String domain;
    private List<DbColumn> dbcolumns;
    private String columns;
    private String createtime;
    private String lastModifyTime;
    private String importPath;
    private String importMode;
    private String importTime;
    private String lastImportTime;
    private String lastImportStatus;
    private int expireDays;
    private int contactNums;
    private String filterCondition;
    private String templateName;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescb() {
        return this.descb;
    }

    public void setDescb(String descb) {
        this.descb = descb;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getLastModifyTime() {
        return this.lastModifyTime;
    }

    public void setLastModifyTime(String lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public String getImportPath() {
        return this.importPath;
    }

    public void setImportPath(String importPath) {
        this.importPath = importPath;
    }

    public String getImportMode() {
        return this.importMode;
    }

    public void setImportMode(String importMode) {
        this.importMode = importMode;
    }

    public String getImportTime() {
        return this.importTime;
    }

    public void setImportTime(String importTime) {
        this.importTime = importTime;
    }

    public String getLastImportTime() {
        return this.lastImportTime;
    }

    public void setLastImportTime(String lastImportTime) {
        this.lastImportTime = lastImportTime;
    }

    public String getLastImportStatus() {
        return this.lastImportStatus;
    }

    public void setLastImportStatus(String lastImportStatus) {
        this.lastImportStatus = lastImportStatus;
    }

    public int getExpireDays() {
        return this.expireDays;
    }

    public void setExpireDays(int expireDays) {
        this.expireDays = expireDays;
    }

    public int getContactNums() {
        return this.contactNums;
    }

    public void setContactNums(int contactNums) {
        this.contactNums = contactNums;
    }

    public String getFilterCondition() {
        return this.filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    public List<DbColumn> getDbcolumns() {
        return this.dbcolumns;
    }

    public void setDbcolumns(List<DbColumn> dbcolumns) {
        this.dbcolumns = dbcolumns;
    }

    public String getColumns() {
        return this.columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }
}