package com.sense.newots.object.controller;

import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.sense.newots.commonentity.BaseResource;
import com.sense.newots.commonentity.CheckRequest;
import com.sense.newots.commonentity.PageRequest;
import com.sense.newots.object.entity.DNCNumber;
import com.sense.newots.object.entity.DNCTemplate;
import com.sense.newots.object.service.DNCNumberService;
import com.sense.newots.object.service.DNCTemplateService;
import com.sense.newots.object.util.ResponseUtil;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 @desc ...
 @date 2021-06-03 13:53:32
 @author szz
 */
@RestController
@RequestMapping("/dncTemplate")
@Slf4j
public class DNCTemplateController extends BaseResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    @Autowired
    private DNCNumberService dncNumDao;
    @Autowired
    private DNCTemplateService dncDao;
    static Gson gson = new Gson();

    @RequestMapping("list")
    @Produces({"application/json"})
    public String getDNCTemplates(@RequestBody PageRequest request) {
        new ResponseUtil();
        ResponseUtil responseUtil;
        try {
            List lists;
            int count;
            if (request.getTemplateName() != null && request.getTemplateName().trim().length() > 0) {
                PageHelper.startPage(request.getStartPage() - 1, request.getPageNum());
                lists = this.dncDao.getTDNCTemplates(request.getDomain(), request.getTemplateName().trim());
                count = this.dncDao.getTDNCTemplateNum(request.getDomain(), request.getTemplateName().trim());
                responseUtil = this.setResponseUtil(1, "getDNCTemplate Suc", super.getMergeSumAndList(lists == null ? new ArrayList() : lists, count));
            } else {
                PageHelper.startPage(request.getStartPage() - 1, request.getPageNum());
                lists = this.dncDao.getTDNCTemplateList(request.getDomain());
                count = this.dncDao.getTDNCTemplateNumList(request.getDomain());
                responseUtil = this.setResponseUtil(1, "getDNCTemplate Suc", super.getMergeSumAndList(lists == null ? new ArrayList() : lists, count));
            }
        } catch (Exception var6) {
            responseUtil = this.setResponseUtil(0, var6.getMessage(), (Object) null);
            log.error("getDNCTemplates fail!:{}", var6);
        }
        return gson.toJson(responseUtil);
    }

    @RequestMapping("add")
    @Produces({"application/json"})
    public String addDNCTemplate(@RequestBody DNCTemplate template) {
        new ResponseUtil();
        ResponseUtil responseUtil;
        try {
            boolean e = this.dncDao.createDNCTemplate(template);
            if (e) {
                responseUtil = this.setResponseUtil(1, "add DNCTemplate Suc", template);
            } else {
                responseUtil = this.setResponseUtil(0, "add DNCTemplate fail", template);
            }
        } catch (Exception var4) {
            responseUtil = this.setResponseUtil(0, var4.getMessage(), template);
            log.error("createDNCTemplate fail!:{}", var4);
        }
        return gson.toJson(responseUtil);
    }

    @RequestMapping("update")
    @Produces({"application/json"})
    public String updateDNCTemplate(@RequestBody DNCTemplate template) {
        new ResponseUtil();

        ResponseUtil responseUtil;
        try {
            if (template.getDncTemplateName() == null) {
                DNCTemplate e = this.dncDao.findById(template.getId());
                e.setFilterCondition(template.getFilterCondition());
                e.setServerType(template.getServerType());
                boolean ret = this.dncDao.updateDNCTemplate(e);
                if (ret) {
                    responseUtil = this.setResponseUtil(1, "update DNCTemplate Suc", e);
                } else {
                    responseUtil = this.setResponseUtil(0, "update DNCTemplate fail", e);
                }
            } else {
                boolean e1 = this.dncDao.updateDNCTemplate(template);
                if (e1) {
                    responseUtil = this.setResponseUtil(1, "update DNCTemplate Suc", template);
                } else {
                    responseUtil = this.setResponseUtil(0, "update DNCTemplate fail", template);
                }
            }
        } catch (Exception var5) {
            responseUtil = this.setResponseUtil(0, var5.getMessage(), (Object) null);
            log.error("updateDNCTemplate fail!:{}", var5);
        }

        return gson.toJson(responseUtil);
    }

    @RequestMapping("delete")
    @Produces({"application/json"})
    public ResponseUtil deleteDNCTemplate(@RequestBody DNCTemplate template) {
        new ResponseUtil();

        ResponseUtil responseUtil;
        try {
            boolean e = this.dncDao.deleteDNCTemplate(template.getId());
            if (e) {
                responseUtil = this.setResponseUtil(1, "delete DNCTemplate Suc", (Object) null);
            } else {
                responseUtil = this.setResponseUtil(0, "delete DNCTemplate fail", (Object) null);
            }
        } catch (Exception var4) {
            responseUtil = this.setResponseUtil(0, var4.getMessage(), (Object) null);
            log.error("deleteDNCTemplate fail!:{}", var4);
        }

        return responseUtil;
    }

    @RequestMapping("download")
    @Produces({"application/json"})
    public byte[] downloadTemplate(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        try {
            String e = "";
            e = e + "phoneNum\r\n";
            response.setHeader("Content-Disposition", "attachment;filename*=UTF-8\'\'" + URLEncoder.encode("DNC.csv", "UTF-8"));
            response.addHeader("content-type", "application/csv");
            response.setCharacterEncoding("UTF-8");
            return e.getBytes();
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    @RequestMapping("check")
    @Produces({"application/json"})
    public ResponseUtil checkTrunkName(@RequestBody CheckRequest number) {
        new ResponseUtil();

        ResponseUtil responseUtil;
        try {
            boolean e = this.dncDao.checkDNCName(number.getName(), number.getDomain());
            responseUtil = this.setResponseUtil(1, e ? "true" : "false", (Object) null);
        } catch (Exception var4) {
            log.error("check trunk name fail!:{}", var4);
            responseUtil = this.setResponseUtil(0, var4.getMessage(), (Object) null);
        }

        return responseUtil;
    }

    @RequestMapping("import")
    @Consumes({"multipart/form-data"})
    @Produces({"application/json"})
    public ResponseUtil importDncRoster(@RequestBody FormDataMultiPart form, @Context HttpServletResponse response) throws UnsupportedEncodingException {
        new ResponseUtil();

        ResponseUtil responseUtil;
        try {
            FormDataBodyPart e = form.getField("file");
            FormDataBodyPart domainPart = form.getField("domain");
            FormDataBodyPart dncTemplateIdPart = form.getField("id");
            if (e == null) {
                throw new IllegalArgumentException("请输入上传文件");
            }

            if (domainPart != null && !StringUtils.isBlank(domainPart.getValue())) {
                if (dncTemplateIdPart != null && !StringUtils.isBlank(dncTemplateIdPart.getValue())) {
                    response.setCharacterEncoding("UTF-8");
                    InputStream fileInputStream = (InputStream) e.getValueAs(InputStream.class);
                    BufferedReader reader = null;
                    String tempString = null;
                    int line = 0;

                    for (reader = new BufferedReader(new InputStreamReader(fileInputStream, "utf-8")); (tempString = reader.readLine()) != null; ++line) {
                        if (line > 0) {
                            DNCNumber dncNum = new DNCNumber();
                            dncNum.setPhoneNum(tempString);
                            dncNum.setDncTemplateId(dncTemplateIdPart.getValue());
                            dncNum.setDomain(domainPart.getValue());
                            boolean result = this.dncNumDao.createDNCNumber(dncNum);
                            if (!result) {
                                responseUtil = super.setResponseUtil(0, "导入dnc名单失败", (Object) null);
                                break;
                            }
                        }
                    }

                    responseUtil = super.setResponseUtil(1, "导入dnc名单成功", (Object) null);
                    tempString = null;
                    reader.close();
                    return responseUtil;
                }

                throw new IllegalArgumentException("请输入Dnc模板Id");
            }

            throw new IllegalArgumentException("请输入domain信息");
        } catch (IllegalArgumentException var13) {
            responseUtil = super.setResponseUtil(0, var13.getMessage(), (Object) null);
        } catch (Exception var14) {
            responseUtil = super.setResponseUtil(1, "导入dnc名单成功", (Object) null);
        }

        return responseUtil;
    }

}
