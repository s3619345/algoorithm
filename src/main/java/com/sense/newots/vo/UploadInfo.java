package com.sense.newots.vo;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by yangyuchang on 2018/11/1 0001.
 */
public class UploadInfo implements Serializable {

    private String url;              //ots的地址
    private String fileName;        // 上传文件名称
    private String corpCode;        // 商户编码
    private String templateCode;    // 模板名称
    private Long    batchId;         //批次id
    private Integer creditCount;    //商户信用数量
    private String[] requiredParam ;//模板参数英文
    private String[] requiredheadNameParam ;//模板参数中文

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCorpCode() {
        return corpCode;
    }

    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public Integer getCreditCount() {
        return creditCount;
    }

    public void setCreditCount(Integer creditCount) {
        this.creditCount = creditCount;
    }

    public String[] getRequiredParam() {
        return requiredParam;
    }

    public void setRequiredParam(String[] requiredParam) {
        this.requiredParam = requiredParam;
    }

    public String[] getRequiredheadNameParam() {
        return requiredheadNameParam;
    }

    public void setRequiredheadNameParam(String[] requiredheadNameParam) {
        this.requiredheadNameParam = requiredheadNameParam;
    }

    @Override
    public String toString() {
        return "UploadInfo{" +
                "url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                ", corpCode='" + corpCode + '\'' +
                ", templateCode='" + templateCode + '\'' +
                ", batchId=" + batchId +
                ", creditCount=" + creditCount +
                ", requiredParam=" + Arrays.toString(requiredParam) +
                ", requiredheadNameParam=" + Arrays.toString(requiredheadNameParam) +
                '}';
    }
}
