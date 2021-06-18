package com.sense.newots.object.controller;

import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.sense.newots.commonentity.BaseResource;
import com.sense.newots.commonentity.CheckRequest;
import com.sense.newots.commonentity.PageRequest;
import com.sense.newots.impl.metric.MetricUtil;
import com.sense.newots.object.entity.*;
import com.sense.newots.object.service.*;
import com.sense.newots.object.util.ResponseUtil;
import com.sense.newots.object.util.TimeUtil;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 @desc 名单管理模块
 @date 2021-05-27 10:40:24
 @author szz
 */
@RestController
@RequestMapping("/rosterTemplate")
@Slf4j
public class RosterTemplateController extends BaseResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    @Autowired
    private RosterService rosterDao;
    @Autowired
    private RosterBatchService rosterBatchDao;
    @Autowired
    private ActivityInfoService activityDao;
    @Autowired
    private RosterTemplateService rosterTemplateDao;
    @Autowired
    private RosterTemplatePreFieldService rosterPreFieldDao;
    static Gson gson = new Gson();

    @RequestMapping("list")
    @Produces({"application/json"})
    public String getRosterTemplates(@RequestBody PageRequest request) {
        new ResponseUtil();
        ResponseUtil responseUtil;
        try {
            int e = request.getStartPage() - 1;
            PageHelper.startPage(e, request.getPageNum());
            List lists = this.rosterTemplateDao.getTRosterTemplateInfo(request.getDomain(), request.getTemplateName());
            log.info("rosterTemplateList size() :" + lists.size());
            Iterator var6 = lists.iterator();
            while (var6.hasNext()) {
                RosterTemplateInfo count = (RosterTemplateInfo) var6.next();
                List list = (List) gson.fromJson(count.getColumns(), MetricUtil.typeDBCol);
                count.setDbcolumns(list);
            }
            int var10 = this.rosterTemplateDao.getTRosterTemplateInfoNum(request.getDomain());
            responseUtil = this.setResponseUtil(1, "getRosterTemplate Suc", super.getMergeSumAndList(lists == null ? new ArrayList() : lists, var10));
        } catch (Exception var9) {
            responseUtil = this.setResponseUtil(0, var9.getMessage(), (Object) null);
            log.error("getRosterTemplates fail!:{}", var9);
        }
        return gson.toJson(responseUtil);
    }

    @RequestMapping("rosterTemplateList")
    @Produces({"application/json"})
    public String newProject(@RequestBody PageRequest request) {
        new ResponseUtil();
        ResponseUtil responseUtil;
        try {
            int e = request.getStartPage() - 1;
            PageHelper.startPage(e, request.getPageNum());
            List lists = this.rosterTemplateDao.getTRosterTemplateList(request.getDomain(), request.getTemplateName());
            int var10 = this.rosterTemplateDao.getTRosterTemplateInfoNum(request.getDomain());
            responseUtil = this.setResponseUtil(1, "getRosterTemplate Suc", super.getMergeSumAndList(lists == null ? new ArrayList() : lists, var10));
        } catch (Exception var9) {
            responseUtil = this.setResponseUtil(0, var9.getMessage(), (Object) null);
            log.error("getRosterTemplates fail!:{}", var9);
        }
        return gson.toJson(responseUtil);
    }

    @RequestMapping("dimList")
    @Produces({"application/json"})
    public String dimRosterTemplates(@RequestBody PageRequest request) {
        new ResponseUtil();
        ResponseUtil responseUtil;
        try {
            int e = request.getStartPage() - 1;
            PageHelper.startPage(e, request.getPageNum());
            List lists = this.rosterTemplateDao.getTRosterTemplateInfo(request.getDomain(), request.getTemplateName());
            log.info("rosterTemplateList size() :" + lists.size());
            Iterator var6 = lists.iterator();

            while (var6.hasNext()) {
                RosterTemplateInfo count = (RosterTemplateInfo) var6.next();
                List list = (List) gson.fromJson(count.getColumns(), MetricUtil.typeDBCol);
                count.setDbcolumns(list);
                int contactNum = this.rosterDao.getContactNums(request.getDomain(), count.getName());
                count.setContactNums(contactNum);
            }
            int var10 = this.rosterTemplateDao.getTRosterTemplateInfoNum(request.getDomain());
            responseUtil = this.setResponseUtil(1, "getRosterTemplate Suc", super.getMergeSumAndList(lists == null ? new ArrayList() : lists, var10));
        } catch (Exception var9) {
            responseUtil = this.setResponseUtil(0, var9.getMessage(), (Object) null);
            log.error("getRosterTemplates fail!:{}", var9);
        }
        return gson.toJson(responseUtil);
    }

    @RequestMapping("listPreField")
    @Produces({"application/json"})
    public String listPreField() {
        new ResponseUtil();
        ResponseUtil responseUtil;
        try {
            List e = this.rosterPreFieldDao.getTRosterTemplatePreparedFields();
            int count = 0;
            if (e != null && e.size() > 0) {
                count = e.size();
            }

            responseUtil = this.setResponseUtil(1, "getPreField Suc", super.getMergeSumAndList(e == null ? new ArrayList() : e, count));
        } catch (Exception var4) {
            responseUtil = this.setResponseUtil(0, var4.getMessage(), (Object) null);
            log.error("getRosterTemplates fail!:{}", var4);
        }

        return gson.toJson(responseUtil);
    }

    @RequestMapping("add")
    @Produces({"application/json"})
    public ResponseUtil addRosterTemplate(@RequestBody RosterTemplateInfo template) {
        new ResponseUtil();

        ResponseUtil responseUtil;
        try {
            template.setColumns(gson.toJson(template.getDbcolumns()));
            template.setCreatetime(TimeUtil.getCurrentTimeStr());
            template.setLastModifyTime(TimeUtil.getCurrentTimeStr());
            boolean e = this.rosterTemplateDao.createRosterTemplateInfo(template);
            if (e) {
                responseUtil = this.setResponseUtil(1, "add RosterTemplate Suc", (Object) null);
            } else {
                responseUtil = this.setResponseUtil(0, "add RosterTemplate fail", (Object) null);
            }
        } catch (Exception var4) {
            responseUtil = this.setResponseUtil(0, var4.getMessage(), (Object) null);
            log.error("createRosterTemplate fail!:{}", var4);
        }

        return responseUtil;
    }

    @RequestMapping("update")
    @Produces({"application/json"})
    public ResponseUtil updateRosterTemplate(@RequestBody RosterTemplateInfo template) {
        ResponseUtil responseUtil = new ResponseUtil();

        try {
            if (template.getId() == 0) {
                RosterTemplateInfo e = this.rosterTemplateDao.findByName(template.getDomain(), template.getName());
                if (e != null) {
                    e.setImportMode(template.getImportMode());
                    e.setImportPath(e.getImportPath());
                    e.setImportTime(TimeUtil.getCurrentTimeStr());
                    e.setFilterCondition(template.getFilterCondition());
                    boolean ret = this.rosterTemplateDao.updateRosterTemplateInfo(e);
                    if (ret) {
                        responseUtil = this.setResponseUtil(1, "update Roster Template Suc", (Object) null);
                    } else {
                        responseUtil = this.setResponseUtil(0, "update Roster Template fail", (Object) null);
                    }
                }
            } else {
                boolean e1 = this.rosterTemplateDao.updateRosterTemplateInfo(template);
                if (e1) {
                    responseUtil = this.setResponseUtil(1, "update Roster Template Suc", (Object) null);
                } else {
                    responseUtil = this.setResponseUtil(0, "update Roster Template fail", (Object) null);
                }
            }
        } catch (Exception var5) {
            responseUtil = this.setResponseUtil(0, var5.getMessage(), (Object) null);
            log.error("updateRosterTemplate fail!:{}", var5);
        }

        return responseUtil;
    }

    @RequestMapping("delete")
    @Produces({"application/json"})
    public ResponseUtil deleteRosterTemplate(@RequestBody RosterTemplateInfo template) {
        new ResponseUtil();
        ResponseUtil responseUtil;
        try {
            boolean e = this.rosterTemplateDao.deleteRosterTemplateInfo(template);
            if (e) {
                responseUtil = this.setResponseUtil(1, "delete Roster Template Suc", (Object) null);
            } else {
                responseUtil = this.setResponseUtil(0, "delete Roster Template fail", (Object) null);
            }
        } catch (Exception var4) {
            responseUtil = this.setResponseUtil(0, var4.getMessage(), (Object) null);
            log.error("deleteRosterTemplate fail!:{}", var4);
        }

        return responseUtil;
    }

    @RequestMapping("download/{domain}/{templateName}")
    @Produces({"application/octet-stream"})
    public byte[] downloadTemplate(@PathVariable("domain") String domain, @PathVariable("templateName") String templateName, @Context HttpServletRequest request, @Context HttpServletResponse response) {
        try {
            RosterTemplateInfo e = this.rosterTemplateDao.findByName(domain, templateName);
            if (e == null) {
                return null;
            } else {
                String cols = e.getColumns();
                List list = (List) gson.fromJson(cols, MetricUtil.typeDBCol);
                String content = "";
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); ++i) {
                        if (content.length() == 0) {
                            content = content + ((DbColumn) list.get(i)).getName();
                        } else {
                            content = content + "," + ((DbColumn) list.get(i)).getName();
                        }
                    }
                }

                content = content + "\r\n";
                response.setHeader("Content-Disposition", "attachment;filename*=UTF-8\'\'" + URLEncoder.encode(templateName + ".csv", "UTF-8"));
                response.addHeader("content-type", "application/csv");
                response.setCharacterEncoding("UTF-8");
                return content.getBytes();
            }
        } catch (Exception var10) {
            var10.printStackTrace();
            return null;
        }
    }

    @RequestMapping("check")
    @Produces({"application/json"})
    public ResponseUtil checkTrunkName(@RequestBody CheckRequest number) {
        new ResponseUtil();

        ResponseUtil responseUtil;
        try {
            boolean e = this.rosterTemplateDao.checkName(number.getName(), number.getDomain());
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
    public ImportResult importRoster(@RequestBody FormDataMultiPart form, @Context HttpServletResponse response) throws UnsupportedEncodingException {
        ImportResult res = new ImportResult();

        try {
            response.setCharacterEncoding("UTF-8");
            FormDataBodyPart e = form.getField("file");
            if (e == null) {
                throw new IllegalArgumentException("请选择要上传的文件");
            }

            FormDataBodyPart rosterNamePart = form.getField("templateName");
            if (rosterNamePart == null) {

                throw new IllegalArgumentException("请输入名单模板名");
            }

            FormDataBodyPart createFlagPart = form.getField("isCreateRoster");
            FormDataBodyPart rosterBatchIdPart = form.getField("batchId");
            FormDataBodyPart domainPart = form.getField("domain");
            InputStream fileInputStream = (InputStream) e.getValueAs(InputStream.class);
            BufferedReader reader = null;
            reader = new BufferedReader(new InputStreamReader(fileInputStream, "gbk"));
            ArrayList rosters = this.rosterTemplateDao.importIntoDateSource(reader, false, rosterBatchIdPart == null ? "" : rosterBatchIdPart.getValue());
            String templateName = rosterNamePart.getValue();
            int count = 0;
            ActivityInfo info = null;
            String domain = domainPart.getValue();
            if (rosters != null && rosters.size() > 0) {
                count = rosters.size();
                Iterator batchId = rosters.iterator();

                while (batchId.hasNext()) {
                    HashMap batchInfo = (HashMap) batchId.next();
                    RosterInfo rosterInfo = new RosterInfo();
                    rosterInfo.setDomain(domain);
                    if (info != null) {
                        rosterInfo.setActivityName(info.getName());
                    }

                    rosterInfo.setTemplateName(templateName);
                    if (batchInfo.containsKey("batchId")) {
                        rosterInfo.setBatchName((String) batchInfo.get("batchId"));
                        batchInfo.remove("batchId");
                    }

                    if (batchInfo.containsKey("phoneNum1")) {
                        rosterInfo.setPhoneNum1((String) batchInfo.get("phoneNum1"));
                        batchInfo.remove("phoneNum1");
                    }

                    if (batchInfo.containsKey("phoneNum2")) {
                        rosterInfo.setPhoneNum2((String) batchInfo.get("phoneNum2"));
                        batchInfo.remove("phoneNum2");
                    }

                    if (batchInfo.containsKey("phoneNum3")) {
                        rosterInfo.setPhoneNum3((String) batchInfo.get("phoneNum3"));
                        batchInfo.remove("phoneNum3");
                    }

                    if (batchInfo.containsKey("phoneNum4")) {
                        rosterInfo.setPhoneNum4((String) batchInfo.get("phoneNum4"));
                        batchInfo.remove("phoneNum4");
                    }

                    if (batchInfo.containsKey("phoneNum5")) {
                        rosterInfo.setPhoneNum5((String) batchInfo.get("phoneNum5"));
                        batchInfo.remove("phoneNum5");
                    }

                    if (batchInfo.containsKey("lastname")) {
                        rosterInfo.setLastname((String) batchInfo.get("lastname"));
                        batchInfo.remove("lastname");
                    }

                    if (batchInfo.containsKey("firstname")) {
                        rosterInfo.setFirstname((String) batchInfo.get("firstname"));
                        batchInfo.remove("firstname");
                    }

                    if (batchInfo.containsKey("age")) {
                        rosterInfo.setAge(Integer.parseInt((String) batchInfo.get("age")));
                        batchInfo.remove("age");
                    }

                    if (batchInfo.containsKey("sex")) {
                        rosterInfo.setSex((String) batchInfo.get("sex"));
                        batchInfo.remove("sex");
                    }

                    if (batchInfo.containsKey("customerId")) {
                        rosterInfo.setCustomerId((String) batchInfo.get("customerId"));
                        batchInfo.remove("customerId");
                    }

                    if (batchInfo.containsKey("address")) {
                        rosterInfo.setAddress((String) batchInfo.get("address"));
                        batchInfo.remove("address");
                    }

                    if (batchInfo.containsKey("email")) {
                        rosterInfo.setEmail((String) batchInfo.get("email"));
                        batchInfo.remove("email");
                    }

                    if (batchInfo.containsKey("cardType")) {
                        rosterInfo.setCardType((String) batchInfo.get("cardType"));
                        batchInfo.remove("cardType");
                    }

                    if (batchInfo.containsKey("cardNum")) {
                        rosterInfo.setCardNum((String) batchInfo.get("cardNum"));
                        batchInfo.remove("cardNum");
                    }

                    rosterInfo.setCreateTime(TimeUtil.getCurrentTimeStr());
                    if (!batchInfo.isEmpty()) {
                        rosterInfo.setCustomFields(gson.toJson(batchInfo));
                    }

                    rosterInfo.setCallRound(1);
                    this.rosterDao.createRosterInfo(rosterInfo);
                }

                RosterBatchInfo batchInfo1 = new RosterBatchInfo();
                if (rosterBatchIdPart.getValue() != null) {
                    String batchId1 = rosterBatchIdPart.getValue();
                    batchInfo1.setBatchId(batchId1);
                    batchInfo1.setTemplateName(templateName);
                    batchInfo1.setCreateTime(TimeUtil.getCurrentTimeStr());
                    batchInfo1.setRoterNum(count);
                    batchInfo1.setCallRound(1);
                    batchInfo1.setDomain(domain);
                    info = this.activityDao.getTActivityInfos(domain, templateName);
                    if (info != null) {
                        batchInfo1.setActivityId(info.getId());
                        batchInfo1.setActivityName(info.getName());
                        info.addRosterNum(count);
                        info.addBatchNum();
                        this.activityDao.updateActivityInfo(info);
                        MetricUtil.addRostersDay(info.getName(), info, domain, count, batchId1);
                    }

                    this.rosterBatchDao.createRosterBatchInfo(batchInfo1);
                }
            }

            res = new ImportResult();
            res.setSucRows(count);
            res.setResult("suc");
        } catch (IllegalArgumentException var19) {
        } catch (Exception var20) {
            log.error("uploadManualRoster Fail!:{}", var20);
        }

        return res;
    }
}
