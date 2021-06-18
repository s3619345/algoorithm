package com.sense.newots.object;
import com.sense.newots.impl.acd.Agent;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TaskContainer {
    public static Map<String, AutoCallThread> autoCallActivityMap = new ConcurrentHashMap<>();
    public static Map<String, String> callRosterMap = new ConcurrentHashMap();

    public static Map<String, String> callTaskMap = new ConcurrentHashMap();

    public static Map<String, Agent> callAgentMap = new HashMap();

    public static Map<String, Integer> callState = new ConcurrentHashMap();
}
