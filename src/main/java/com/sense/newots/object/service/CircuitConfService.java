package com.sense.newots.object.service;


import com.sense.newots.object.entity.CircuitConf;

import java.util.List;

/**
 @desc ...
 @date 2021-05-24 09:12:09
 @author szz
 */
public interface CircuitConfService {
    List<CircuitConf> getCircuitListStrartTime();

    List<CircuitConf> getCircuitListEndTime();

    boolean updateCircuit(CircuitConf circuitConf);

    boolean updateCircuitByStatus();

    List<CircuitConf> getCircuitInfos(String isRun, String activityName, String prefix);

    int getCircuitInfoNum();

    List<CircuitConf> findByActivityId(String activityName);

    boolean saveCircuit(CircuitConf circuit);

    boolean delCircuit(Integer id);
}
