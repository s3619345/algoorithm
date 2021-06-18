package com.sense.newots.service;

import com.sense.newots.object.entity.CircuitConf;
import com.sense.newots.object.service.CircuitConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by senseinfo on 2019/3/13.
 */
@Service
public class CircuitTransactionService {

    @Autowired
    private CircuitConfService circuitConfDAO;

    @Transactional(readOnly = true)
    public List<CircuitConf> getCircuitListStrartTime(){
        return circuitConfDAO.getCircuitListStrartTime();
    }

    @Transactional(readOnly = true)
    public List<CircuitConf> getCircuitListEndTime(){
        return circuitConfDAO.getCircuitListEndTime();
    }
    @Transactional
    public boolean updateCircuit(CircuitConf circuitConf){
        return circuitConfDAO.updateCircuit(circuitConf);
    }
    @Transactional
    public boolean updateCircuitByStatus(){
        return circuitConfDAO.updateCircuitByStatus();
    }


}
