package com.sense.newots.util;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.sense.newots.impl.metric.MetricUtil;
import com.sense.newots.impl.metric.SmsMetric;
import com.sense.newots.object.entity.RosterBatchInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
public class StringUtil {

    /**
     * 判断对象是否为空
     *
     * @param obj 对象
     * @return {@code true}: 为空<br>{@code false}: 不为空
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof CharSequence && (obj.toString().trim().length() == 0 || "null".equalsIgnoreCase(obj.toString().trim()) )) {
            return true;
        }
        if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            return true;
        }
        if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;
        }

        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < object.length; i++) {
                if (!isEmpty(object[i])) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }

        return false;
    }
    /**
     * 判断对象是否非空
     *
     * @param obj 对象
     * @return {@code true}: 非空<br>{@code false}: 空
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }



    public static byte[] hexStringToBytes(String hexString) {
        if ((hexString == null) || (hexString.equals(""))) {
            return null;

        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = ((byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[(pos + 1)])));

        }
        return d;

    }


    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);

    }


    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if ((src == null) || (src.length <= 0)) {
            return null;

        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);

            }
            stringBuilder.append(hv);

        }
        return stringBuilder.toString();

    }


    public static void main(String[] args) {
        String gen_str = bytesToHexString("py".getBytes());
        System.out.println(gen_str);
        System.out.println(new String(hexStringToBytes(gen_str)));

        RosterBatchInfo info = new RosterBatchInfo();
        info.setBatchId("1");
        info.setDomain("com");

        MetricUtil.addCompleteBatchInfo(info);

        RosterBatchInfo myBatchInfo = new RosterBatchInfo();

        myBatchInfo.setBatchId("1");
        myBatchInfo.setDomain("com");
        myBatchInfo.setCallRound(1);


        MetricUtil.completeBatchs.remove(myBatchInfo);


        System.out.println(MetricUtil.completeBatchs);
    }


    //MD5 加密 小写
    public static String LowercaseMd5Encryption(String value){
        String result = null;
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
            md5.update((value).getBytes("UTF-8"));
        }catch (NoSuchAlgorithmException error){
            error.printStackTrace();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        byte b[] = md5.digest();
        int i;
        StringBuffer buf = new StringBuffer("");

        for(int offset=0; offset<b.length; offset++){
            i = b[offset];
            if(i<0){
                i+=256;
            }
            if(i<16){
                buf.append("0");
            }
            buf.append(Integer.toHexString(i));
        }

        result = buf.toString();
        return result;
    }

    public static SmsMetric list(SmsMetric metric, List<Map<Object,Object>> list1, List<Map<Object,Object>> list2){
        //log.info("clicklist1 -------"+list1);
        //log.info("genlist2 -------"+list2);
        if (StringUtil.isEmpty(list1) && StringUtil.isEmpty(list2)){
            metric.setHours_click(list1);
            metric.setHours_reptile(list2);
            return metric;
        }else if (StringUtil.isEmpty(list1) && StringUtil.isNotEmpty(list2)){
            list1 = new ArrayList<Map<Object,Object>>();
            Map<Object,Object> clickMap = new HashedMap();
            for (Object key:list2.get(0).keySet()
                 ) {
                clickMap.put(key,0);
            }
            list1.add(clickMap);
        }else if (StringUtil.isEmpty(list2) && StringUtil.isNotEmpty(list1)){
            list2 = new ArrayList<Map<Object,Object>>();
            Map<Object,Object> genMap = new HashedMap();
            for (Object key:list1.get(0).keySet()
                    ) {
                genMap.put(key,0);
            }
            list2.add(genMap);
        }else {
            Map<Object,Object> map1 = list1.get(0);
            Map<Object,Object> map2 = list2.get(0);
            MapDifference<Object, Object> difference = Maps.difference(map1,map2);
            // 是否有差异，返回boolean
            boolean areEqual = difference.areEqual();
            if (areEqual){
                log.info("两个list没有差异");
            }else {
                log.info("两个list有差异");
                // 键只存在于左边Map的映射项
                Map<Object, Object> onlyOnLeft = difference.entriesOnlyOnLeft();
                //System.out.println("键只存在于第一个clickMap的映射项:" + onlyOnLeft);
                if (StringUtil.isNotEmpty(onlyOnLeft)){
                    for (Object key : onlyOnLeft.keySet()) {
                        //System.out.println("key= " + key + " and value= " + onlyOnLeft.get(key));
                        map2.put(key,0);
                    }
                }
                // 键只存在于右边Map的映射项
                Map<Object, Object> entriesOnlyOnRight = difference.entriesOnlyOnRight();
                //System.out.println("键只存在于第二个reptileMap的映射项:" + entriesOnlyOnRight);
                if (StringUtil.isNotEmpty(entriesOnlyOnRight)){
                    for (Object key : entriesOnlyOnRight.keySet()) {
                        //System.out.println("key= " + key + " and value= " + entriesOnlyOnRight.get(key));
                        map1.put(key,0);
                    }
                }
            }

        }
        metric.setHours_click(list1);
        metric.setHours_reptile(list2);
        return metric;
    }

}
