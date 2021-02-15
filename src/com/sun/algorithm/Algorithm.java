package com.sun.algorithm;

import java.util.ArrayList;

/**
 * @author gentleman
 * @description 二叉排序法
 * @create 2020-10-30-20:06
 */
public class Algorithm {
    public static void main(String[] args) {
        int[] array = {1, 4, 5, 7, 10, 12, 15};
        ArrayList<Object> objects = new ArrayList<>();
//        int i = biSearch(array, 10);
       int i = binarySearch(array, 0, array.length - 1, 10);
        System.out.println(i);
    }

    //非递归实现
    static int biSearch(int[] array, int a) {
        //io表示数组的第一位
        int io = 0;
        //hi为数组的总长度
        int hi = array.length - 1;
        //mid表示中位数
        int mid;
        while (io <= hi) {
            //先算出数组的中位数
            mid = (io + hi) >> 1;
            //判断数组中间的书是否等于传入的数
            if (array[mid] == a) {
                //如果等于直接返回数据中数据的位置
                return mid + 1;
                //如果数组的中位数比a小
            } else if (array[mid] < a) {
                //将数组的最小值io设置为数组的中位数
                io = mid + 1;
                //如果数组的中位数比a大
            } else {
                //将数组的最大值hi设置为数组的中位数
                hi = mid - 1;
            }
        }
        return -1;
    }

    //递归实现
    static int binarySearch(int[] array, int low, int high, int num) {
        //判断数组中的最小值与最大值
        if (low <= high) {
            //算出中位数
            int mid = (low + high) >> 1;
            //判断当数组的中间位的数的值是否等于传入的值
            if (array[mid] == num) {
                //返回该位置的值
                return mid+1;
                //如果数组中间位置的值小于传入的值
            } else if (array[mid] < num) {
                //将最小值化为中位数+1
                low = mid + 1;
            } else {
                //否则将最大值设为中位数-1
                high = mid - 1;
            }
            return binarySearch(array, low, high, num);
        }
        return -1;
    }
}
