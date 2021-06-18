package com.sense.newots.util;

import java.math.BigDecimal;

/** 
*  浮点数操作，加减乘除，保证浮点数精度不缺失
*/
public class MathUtils {

    // 默认除法运算精度
    private static final int DEF_DIV_SCALE = 10;

    /**
     * 提供精确的加法运算
     * 
     * @param v1
     * @param v2
     * @return double
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算
     * @param v1 :减数
     * @param v2 :被减数
     * @return double
     */
    public static double reduce(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算
     * @param v1 
     * @param v2
     * @return double
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /** 
    *  提供精确的除法运算(默认精度为精确到小数点后10位)
    * @param v1 :除数
    * @param v2 :被除数
    * @return double     
    */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * ( 相对 )精确除法运算 . 当发生除不尽情况时 , 由scale参数指 定精度 , 以后数字四舍五入
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位
     * @return 两个参数的商(BigDecimal)
     */
    public static BigDecimal div(BigDecimal v1, BigDecimal v2, Integer scale) {
        if (null == v1) {
            return BigDecimal.ZERO;
        }
        if (null == v2) {
            v2 = BigDecimal.ONE;
        }

        if (v2.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("除数不能为0");
        }

        if (scale < 0) {
            throw new IllegalArgumentException("精确度不能小于0");
        }

        return v1.divide(v2, scale, BigDecimal.ROUND_HALF_UP);
    }

    /** 
    *  提供精确的除法运算
    * @param v1    :除数
    * @param v2 :被除数
    * @param scale :精度
    * @return double     
    */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * ( 相对 )精确除法运算 , 当发生除不尽情况时 , 精确到 小数点以后2位 , 以后数字四舍五入
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商(String)
     */
    public static String div(String v1, String v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * ( 相对 )精确除法运算 . 当发生除不尽情况时 , 由scale参数指 定精度 , 以后数字四舍五入
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位
     * @return 两个参数的商(String)
     */
    public static String div(String v1, String v2, Integer scale) {
        if (null == v1) {
            return "0";
        }
        if (null == v2) {
            v2 = "1";
        }
        BigDecimal b1 = new BigDecimal(v1.trim());
        BigDecimal b2 = new BigDecimal(v2.trim());
        return String.valueOf(div(b1, b2, scale));
    }
    /** 
    *  提供精确的四舍五入运算
    * @param v :浮点数
    * @param scale :精度
    * @return double     
    */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
}