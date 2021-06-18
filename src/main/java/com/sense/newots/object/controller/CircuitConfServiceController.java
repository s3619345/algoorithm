package com.sense.newots.object.controller;

import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.sense.newots.commonentity.BaseResource;
import com.sense.newots.commonentity.PageRequest;
import com.sense.newots.object.entity.CircuitConf;
import com.sense.newots.object.service.CircuitConfService;
import com.sense.newots.object.util.ResponseUtil;
import com.sense.newots.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 @desc
 @date 2021-05-31 10:02:05
 @author szz
 */
@RestController
@RequestMapping("/circuitConf")
@Slf4j
public class CircuitConfServiceController extends BaseResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    @Autowired
    private CircuitConfService circuitConfDAO;
    static Gson gson = new Gson();

    @RequestMapping("list")
    @Produces({"application/json"})
    public String getActivityInfos(@RequestBody PageRequest request) {
        new ResponseUtil();
        ResponseUtil responseUtil;
        try {
            int e = request.getStartPage() - 1;
            PageHelper.startPage(e, request.getPageNum());
            List lists = this.circuitConfDAO.getCircuitInfos(request.getIsRun(), request.getActivityName(), request.getPrefix());
            int count = this.circuitConfDAO.getCircuitInfoNum();
            responseUtil = this.setResponseUtil(1, "getCircuitInfo Suc", super.getMergeSumAndList(lists == null ? new ArrayList() : lists, count));
        } catch (Exception var9) {
            responseUtil = this.setResponseUtil(0, var9.getMessage(), (Object) null);
            log.error("getCircuitInfos fail!:{}", var9);
        }
        return gson.toJson(responseUtil);
    }

    @RequestMapping("save")
    @Produces({"application/json"})
    public ResponseUtil SaveCircuit(@RequestBody CircuitConf circuit) {

        new ResponseUtil();
        ResponseUtil responseUtil = null;
        try {
            List<CircuitConf> circuits = this.circuitConfDAO.findByActivityId(circuit.getActivityName());
            Integer addStime = Integer.parseInt(circuit.getStartTime().replace(":", ""));
            Integer addEtime = Integer.parseInt(circuit.getEndTime().replace(":", ""));
            LocalDateTime today = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String time = today.format(formatter);
            Integer nowTime = Integer.parseInt(time.substring(11, 16).replace(":", ""));
            if (addStime <= nowTime) {
                circuit.setIsRun(1);
            }
            if (addStime <= nowTime && addEtime <= nowTime) {
                circuit.setIsRun(2);
            }
            if (StringUtil.isEmpty(circuits)) {
                this.circuitConfDAO.saveCircuit(circuit);
                return this.setResponseUtil(1, "save circuit suc", (Object) null);
            } else {
                for (int i = 0; i < circuits.size(); i++) {
                    CircuitConf circuit1 = circuits.get(i);
                    Integer startTime = Integer.parseInt(circuit1.getStartTime().replace(":", ""));
                    Integer endTime = Integer.parseInt(circuit1.getEndTime().replace(":", ""));

                    boolean e = addStime >= startTime && addStime >= endTime && addEtime >= startTime && addEtime >= endTime ||
                            addStime <= startTime && addStime <= endTime && addEtime <= startTime && addEtime <= endTime;
                    if (e == false) {
                        return this.setResponseUtil(2, "添加的时间冲突,请核查", (Object) null);
                    }
                }
                boolean e = this.circuitConfDAO.saveCircuit(circuit);
                if (e) {
                    responseUtil = this.setResponseUtil(1, "save circuit suc", (Object) null);
                } else {
                    responseUtil = this.setResponseUtil(0, "save circuit fail", (Object) null);
                }
            }

        } catch (Exception var1) {
            responseUtil = this.setResponseUtil(0, var1.getMessage(), (Object) null);
            log.error("save circuit fail:{}", var1);
        }
        return responseUtil;
    }

    @RequestMapping("update")
    @Produces({"application/json"})
    public ResponseUtil updateCircuit(@RequestBody CircuitConf circuit) {

        ResponseUtil responseUtil = new ResponseUtil();
        try {
            List<CircuitConf> circuits = this.circuitConfDAO.findByActivityId(circuit.getActivityName());
            Integer addStime = Integer.parseInt(circuit.getStartTime().replace(":", ""));
            Integer addEtime = Integer.parseInt(circuit.getEndTime().replace(":", ""));
            LocalDateTime today = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String time = today.format(formatter);
            Integer nowTime = Integer.parseInt(time.substring(11, 16).replace(":", ""));
            if (addStime <= nowTime) {
                circuit.setIsRun(1);
            }
            if (addStime <= nowTime && addEtime <= nowTime) {
                circuit.setIsRun(2);
            }
            for (int i = 0; i < circuits.size(); i++) {
                CircuitConf circuit1 = circuits.get(i);
                if (circuit.getId() == circuit1.getId()) {
                    circuits.remove(circuit1);
                    i--;
                    continue;
                }
                Integer startTime = Integer.parseInt(circuit1.getStartTime().replace(":", ""));
                Integer endTime = Integer.parseInt(circuit1.getEndTime().replace(":", ""));

                boolean e = addStime >= startTime && addStime >= endTime && addEtime >= startTime && addEtime >= endTime ||
                        addStime <= startTime && addStime <= endTime && addEtime <= startTime && addEtime <= endTime;
                if (e == false) {
                    return this.setResponseUtil(2, "修改的时间冲突,请核查", (Object) null);
                }
            }
            this.circuitConfDAO.updateCircuit(circuit);
            responseUtil = this.setResponseUtil(1, "update circuit suc", (Object) null);
        } catch (Exception e) {
            responseUtil = this.setResponseUtil(0, "update circuit fail", (Object) null);
            log.info("update circuit fail:{}", e);
        }
        return responseUtil;
    }

    @RequestMapping("del")
    @Produces({"application/json"})
    public ResponseUtil delCircuit(@RequestBody String info) {
        ResponseUtil responseUtil = new ResponseUtil();
        JSONObject jsonObject = JSONObject.fromObject(info);
        JSONArray jsonArray = jsonObject.getJSONArray("ids");

        for (int i=0;i<jsonArray.size();i++){
            Integer id = (Integer) jsonArray.get(i);
            try {
                this.circuitConfDAO.delCircuit(id);
                responseUtil = this.setResponseUtil(1, "delete Circuit Suc", (Object)null);
            } catch (Exception var4) {
                responseUtil = this.setResponseUtil(0, var4.getMessage(), (Object)null);
                log.error( "deleteActivityInfo fail!:{}", var4);
            }
        }
        return responseUtil;
    }

}
