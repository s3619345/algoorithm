package com.sense.newots.util;

import org.quartz.Trigger;
import org.springframework.cglib.beans.BeanMap;

import java.util.*;

/**
 * Created by cookie on 2018/6/13 0013.
 */
public class Collections{

    private java.util.Collections collections;

    public static <T> List filter(List<T> list, com.sense.newots.util.ListFilterHook filterHook) {
        List<T> filterList = new ArrayList<>();
        for (T t : list) {
            if (filterHook.match(t)) {
                filterList.add(t);
            }
        }
        return filterList;
    }

    public static <T> List sort(List<T> list, Comparator<T> comparator) {
        java.util.Collections.sort(list, comparator);
        return list;
    }


    /**
     * 将对象装换为map
     *
     * @param bean
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将map装换为javabean对象
     *
     * @param map
     * @param bean
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }


    public static List computeFireTimes(Trigger trigg, org.quartz.Calendar cal, int numTimes,int intvel) {
        LinkedList lst = new LinkedList();
        Trigger t = (Trigger)trigg.clone();
        if(t.getNextFireTime() == null) {
            t.computeFirstFireTime(cal);
        }

        for(int i = 0; i < numTimes; ++i) {
            Calendar calendar=Calendar.getInstance();
            calendar.add(Calendar.MINUTE,  intvel);
            Date d = calendar.getTime();
            if(d == null) {
                break;
            }

            lst.add(d);
            t.triggered(cal);
        }

        return java.util.Collections.unmodifiableList(lst);
    }


    public static int getMinute() {
        Calendar  calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.MINUTE);
    }
    public static void main(String[] args) {

       /* Map<String, Object> map = new HashMap<String, Object>();
        map.put("fileName","fileName");
        UploadInfo uploadInfo = mapToBean(map,new UploadInfo());
*/
            String cron= "0 */15 5,6,7 * ? ";
        cron = cron.replaceFirst("\\*\\/", Collections.getMinute()-1+"/");

        System.out.println(cron);
        //System.out.println(beanToMap(uploadInfo));
    }
}
