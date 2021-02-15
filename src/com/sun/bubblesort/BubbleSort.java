package com.sun.bubblesort;

/**
 * @author gentleman
 * @description 冒泡排序算法，按照从小到大排序
 * @create 2020-11-01-17:10
 */
public class BubbleSort {
    public static void main(String[] args) {
        int[] nums = {2, 3, 4, 65, 1, 345, 23, 2};
        int[] bubbleSort = getBubbleSort(nums);
        for (int i = 0; i < bubbleSort.length; i++) {
            System.out.println(bubbleSort[i]);
        }
    }

    static int[] getBubbleSort(int[] nums) {
        //获取数组的长度
        int len = nums.length;
        if (len == 0 || len == 1) {
            return nums;
        }
        //外层循环控制总共需要遍历的次数
        for (int i = 0; i < len; i++) {
            //内层循环控制判断数组中的数需要遍历的次数
            for (int j = 0, subLen = len - 1 - i; j < subLen; j++) {
                if (nums[j + 1] < nums[j]) {
                    int temp = nums[j + 1];
                    nums[j + 1] = nums[j];
                    nums[j] = temp;
                }
            }
        }
        return nums;
    }
}
