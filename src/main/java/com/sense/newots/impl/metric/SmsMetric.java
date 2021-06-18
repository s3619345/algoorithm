package com.sense.newots.impl.metric;

import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @Author : ZhangPeiPei
 * @Description : TODO
 * @Date : 2021/1/27 11:14
 * @Versions : 1.0
 **/
@Data
@ToString
public class SmsMetric {

    private String bid;
    private int sendCount; //发送总数
    private List hours_click; //以十分钟统计点击数量
    private List hours_reptile;//以十分钟统计爬虫数量
    private Map<String,Integer> answerMap; //人工应答
    private Map<String,Integer> clickMap; // 点击


    public void clear() {
        this.hours_click.clear();
        this.clickMap.clear();
        this.answerMap.clear();
    }
}
