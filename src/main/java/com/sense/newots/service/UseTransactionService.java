package com.sense.newots.service;

import com.sense.newots.object.service.RosterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by senseinfo on 2019/1/14.
 */
@Service
public class UseTransactionService {

    @Resource
    private RosterService rosterDAO;


    @Transactional
    public int updateRosterResult(String batchName){
        return rosterDAO.updateRosterResult(batchName);
    }


//    @Transactional(rollbackFor = Exception.class)
//    public void updateStatus(String batchId) throws Exception{
//        RosterBatchInfo rosterBatchInfo = new RosterBatchInfo();
//        rosterBatchInfo.setStatus(0);
//        rosterBatchInfo.setId(batchId);
//        rosterBatchDAO.updateRosterBatchInfo(rosterBatchInfo);
//        rosterDAO.updateInfoByStatus(batchId);
//    }

}
