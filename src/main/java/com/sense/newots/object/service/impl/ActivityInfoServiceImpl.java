package com.sense.newots.object.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.sense.newots.impl.metric.MetricUtil;
import com.sense.newots.object.dao.ActivityInfoMapper;
import com.sense.newots.object.entity.ActivityInfo;
import com.sense.newots.object.service.ActivityInfoService;
import com.sense.newots.object.util.TimeUtil;
import com.sense.newots.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 @desc ...
 @date 2021-05-20 11:08:22
 @author szz
 */
@Service
@Slf4j
public class ActivityInfoServiceImpl implements ActivityInfoService {
    static Gson gson = new Gson();
    @Autowired()
    private ActivityInfoMapper activityInfoMapper;

    @Override
    public List<ActivityInfo> selectActivityInfo() {
        List<ActivityInfo> list = activityInfoMapper.selectActivityInfo();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public ActivityInfo findActivityInfoByTemplate(String name) {
        ActivityInfo activityInfoByTemplate = activityInfoMapper.findActivityInfoByTemplate(name);
        if (activityInfoByTemplate == null) {
            return null;
        }
        return activityInfoByTemplate;
    }

    @Override
    public boolean updateActivityInfo(ActivityInfo info) throws DataAccessException {
        log.info("## ActivityInfo update " + gson.toJson(info));
        info.setLastUpdateTime(TimeUtil.getCurrentTimeStr());

        try {
            info.setLastUpdateTime(TimeUtil.getCurrentTimeStr());
           activityInfoMapper.updateActivityInfo(info);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return false;
        }
        return true;
    }

    @Override
    public Integer clickCount(String bid, String startTime, String endTime) {
        Integer count = activityInfoMapper.clickCount(bid, startTime, endTime);
        if(count==null){
            return 0;
        }
        log.info("##clickcount result" + count);
        return count;
    }

    @Override
    public ActivityInfo getTActivityInfos(String domain, String templateName) {
        ActivityInfo tActivityInfos = activityInfoMapper.getTActivityInfos(domain, templateName);
        if (tActivityInfos == null) {
            return null;
        }
        return tActivityInfos;
    }

    @Override
    public List<ActivityInfo> getStoppedActivityInfos() {
        List<ActivityInfo> stoppedActivityInfos = activityInfoMapper.getStoppedActivityInfos();
        if (stoppedActivityInfos == null) {
            return null;
        }
        return stoppedActivityInfos;
    }

    @Override
    public boolean deleteActivityInfo(ActivityInfo info) throws DataAccessException {
        log.info("## ActivityInfo delete " + gson.toJson(info));
        try {
            activityInfoMapper.deleteActivityInfo(info);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean updatePrefix(String prefix, String name) throws DataAccessException {
        try {
            activityInfoMapper.updatePrefix(prefix, name);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return false;
        }
        return true;
    }

    @Override
    public List<ActivityInfo> getTActivityInfosList(String activityStatus, String prefix,String activityName) {
        List<ActivityInfo> list = activityInfoMapper.getTActivityInfosList(activityStatus, prefix,activityName);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public int getTActivityInfoNum(String domain) {
        int i = activityInfoMapper.getTActivityInfoNum(domain);
        if (i <= 0) {
            return 0;
        }
        return i;
    }

    @Override
    public boolean createActivityInfo(ActivityInfo template) throws DataAccessException {
        log.info("## ActivityInfo create " + gson.toJson(template));
        try {
            activityInfoMapper.createActivityInfo(template);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return false;
        }
        return true;
    }

    @Override
    public ActivityInfo findById(Integer id) {
        ActivityInfo activityInfo = activityInfoMapper.findById(id);
        if (activityInfo == null) {
            return null;
        }
        return activityInfo;
    }

    @Override
    public boolean deleteActivityInfoById(Integer id) {
        log.info("## ActivityInfo delete " + id);
        try {
            activityInfoMapper.deleteActivityInfoById(id);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean checkName(String name, String domain) {
        int i = activityInfoMapper.checkName(name, domain);
        log.info("## getTActivityInfoNum [" + domain + "] result " + i);
        if (i <= 0)
            return true;
        return false;
    }

    @Override
    public int sendCount(String templateName, String startTime, String endTime) {
        try {
            return MetricUtil.sendCount.get(templateName, new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    int i = activityInfoMapper.sendCount(templateName, startTime, endTime);
                    log.info("## sendCount [" + templateName + "] result "+i);
                    if (StringUtil.isNotEmpty(i)) {
                        return i;
                    }
                    return 0;
                }
            });
        } catch (ExecutionException e) {
            log.info("sendCount is error --"+e);
            return 0;
        }
    }

    @Override
    public List<Map<Object, Object>> findClick(String templateName, String startTime, String endTime) {

        try {
            //过期时间五分钟
            return MetricUtil.cache.get(templateName, new Callable<List<Map<Object, Object>>>() {
                @Override
                public List<Map<Object, Object>> call() throws Exception {
                    log.info("## 短信点击findClick [" + templateName + "] ");
                    List list = activityInfoMapper.findClick(templateName, startTime, endTime);
                    if (StringUtil.isNotEmpty(list)) {
                        return listMap(list);
                    }
                    return null;
                }
            });
        } catch (ExecutionException e) {
            log.info("smsClick is error --"+e);
            return null;
        }
    }

    @Override
    public List<Map<Object, Object>> findReptile(String templateName, String startTime, String endTime) {

        try {
            return MetricUtil.gen.get(templateName, new Callable<List<Map<Object, Object>>>() {
                @Override
                public List<Map<Object, Object>> call() throws Exception {
                    log.info("## 爬虫点击量findReptile [" + templateName + "] ");
                    List list = activityInfoMapper.findReptile(templateName, startTime, endTime);
                    if (StringUtil.isNotEmpty(list)) {
                        return listMap(list);
                    }
                    return null;
                }
            });
        } catch (ExecutionException e) {
            log.info("smsReptile is error --"+e);
            return null;
        }
        //return listMap(list);
    }

    @Override
    public boolean updateRosterNum(int rosterNum, String name) throws DataAccessException{
        try {
            activityInfoMapper.updateRosterNum(rosterNum,name);
            log.info("## updateRosterNum name  " ,name);
        } catch (Exception e) {
            log.error("Exception:{}",e);
            return false;
        }
        return true;
    }

    //list 转 List<Map<Object, Object>>
    public List<Map<Object, Object>> listMap(List list){
        if (StringUtil.isNotEmpty(list)){
            List<Map<Object, Object>> list_map = new ArrayList<>();
            Map<Object, Object> map = new HashedMap();
            if (StringUtil.isNotEmpty(list)) {
                for (int i = 0; i < list.size(); i++) {
                    JSONArray jsonArray= JSONArray.parseArray(JSON.toJSONString(list.get(i)));
                    map.put(jsonArray.get(0),jsonArray.get(1));
                }
                list_map.add(map);
            }
            return list_map;
        }
        return null;
    }

}
