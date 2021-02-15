package com.sun.insertionsort;

/**
 * @author gentleman
 * @description 插入排序算法
 * 插入排序（Insertion-Sort）的算法描述是一种简单直观的排序算法。
 * 它的工作原理是通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，
 * 找到相应位置并插入。插入排序在实现上，通常采用in-place排序（即只需用到O(1)的额外空间的排序），
 * 因而在从后向前扫描过程中，需要反复把已排序元素逐步向后挪位，为最新元素提供插入空间。
 * @create 2020-11-02-20:51
 */
public class InsertionSort {
    public static void main(String[] args) {
         int[] nums={2,4,1,5,3};
        int[] inertionSort = getInertionSort(nums);
        for (int i = 0; i <inertionSort.length ; i++) {
            System.out.println(inertionSort[i]);
        }
    }

     static int[] getInertionSort(int[] array) {
        int len = array.length;
        //基本情况下的数组可以直接返回
        if (array == null || len == 0 || len == 1) {
            return array;
        }
        //current来接收数组中的值
        int current;
        for (int i = 0; i < len - 1; i++) {
            //第一个数默认已排序，从第二个数开始
            current = array[i + 1];
            //前一个数的下标
            int proIdx = i;
            //拿当前的数与之前已排序序列逐一往前比较
            //如果比较的数据比当前的数大，就把该数往后挪一步
            while (proIdx >= 0 && current < array[proIdx]) {
                array[proIdx + 1] = array[proIdx];
                proIdx--;
            }
            //while循环跳出说明找到了位置
            array[proIdx + 1] = current;
        }
        return array;
    }
    
}
