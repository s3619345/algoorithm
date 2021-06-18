package com.sense.newots.object.dao;


import com.sense.newots.object.entity.CircuitConf;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 @desc ...
 @date 2021-05-21 17:57:10
 @author szz
 */


public interface CircuitConfMapper {
    List<CircuitConf> getCircuitListStrartTime();

    List<CircuitConf> getCircuitListEndTime();

    int updateCircuit(CircuitConf circuitConf);

    int updateCircuitByStatus();

    List<CircuitConf> getCircuitInfos(@Param("isRun") String isRun, @Param("activityName") String activityName, @Param("perfix") String prefix);

    int getCircuitInfoNum();

    List<CircuitConf> findByActivityId(String activityName);

    int saveCircuit(CircuitConf circuit);

    int  delCircuit(Integer id);
}
