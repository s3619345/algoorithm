package com.sun.josepfu;

/**
 @desc 约瑟夫问题
        单项环形链表经典问题
 设编号为1，2，… n 的n 个人围坐一圈，约定编号为k（1<=k<=n）的人从1 开始报数，数到
 m 的那个人出列，它的下一位又从1 开始报数，数到m 的那个人又出列，依次类推，直到所有人出列为止，由此
 产生一个出队编号的序列。
 @date 2021-02-13 10:04:32
 @author gentleman
 */

public class Josepfu {
    public static void main(String[] args) {
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        circleSingleLinkedList.addBoy(5);
        circleSingleLinkedList.showBoy();
        circleSingleLinkedList.countBoy(1,2,5);
    }

    //创建一个环形的单项链表
    static class CircleSingleLinkedList {
        //创建一个first节点，当前没有编号
        private Boy first = null;

        //添加小孩节点，构建成一个环形的链表
        public void addBoy(int nums) {
            //nums用来做数据校验
            if (nums < 1) {
                System.out.println("nums的值不正确");
                return;
            }
            //辅助指针，帮助构建环形链表
            Boy curBoy = null;
            //使用for来创建我们的环形链表
            for (int i = 1; i <= nums; i++) {
                //根据编号，创建小孩节点
                Boy boy = new Boy(i);
                //如果是第一个小孩
                if (i == 1) {
                    first = boy;
                    //构成环
                    first.setNext(first);
                    //让curBoy指向第一个小孩
                    curBoy = first;
                } else {
                    curBoy.setNext(boy);
                    boy.setNext(first);
                    curBoy = boy;
                }
            }
        }

        //遍历当前的链表
        public void showBoy() {
            //判断链表是否为空
            if (first == null) {
                System.out.println("没有任何小孩");
                return;
            }
            //因为first不能动，因此我们仍然使用一个辅助指针完成遍历
            Boy curBoy = first;
            while (true) {
                System.out.println("小孩的编号" + curBoy.getNo());
                //说明已经遍历完毕
                if (curBoy.getNext() == first) {
                    break;
                }
                //curBoy后移
                curBoy = curBoy.getNext();
            }
        }

        //根据用户的输入，计算出小孩的出圈顺序
        public void countBoy(int startNo, int countNum, int nums) {
            //先对数据进行校验
            if (first == null || startNo < 1 || startNo > nums) {
                System.out.println("参数输入有误，请重新输入");
                return;
            }
            //创建要给辅助指针，帮助完成小孩出圈
            Boy helper = first;
            //需求创建一个辅助指针变量helper，事先应该指向环形链表的最后这个节点
            while (true) {
                //说明helper指向最后小孩节点
                if (helper.getNext() == first) {
                    break;
                }
                helper = helper.getNext();
            }
            //小孩报数前，先让first和helper移动k-1次
            for (int j = 0; j < startNo - 1; j++) {
                first = first.getNext();
                helper = helper.getNext();
            }
            //当小孩报数时，让first和helper指针同时移动m-1次，然后出圈
            //这里是一个循环操作，知道圈中只有一个节点
            while (true) {
                //说明圈中只有一个节点
                if (helper == first) {
                    break;
                }
                //让frist和helper指针同时移动countNum-1
                for (int j = 0; j < countNum - 1; j++) {
                    first = first.getNext();
                    helper = helper.getNext();
                }
                //这时first指向的节点，就是小孩出圈的顺序
                System.out.println("小孩出圈"+first.getNo());
                //这时将first指向的小孩节点出圈
                first=first.getNext();
                helper.setNext(first);
            }
            System.out.println("最后留在圈中的小孩"+first.getNo());
        }
    }

    //创建一个Boy类，表示一个节点
   static class Boy {
        //编号
        private int no;
        //指向下一个节点，默认null
        private Boy next;

        public Boy(int no) {
            this.no = no;
        }

        public int getNo() {
            return no;
        }

        public Boy getNext() {
            return next;
        }

        public void setNo(int no) {
            this.no = no;
        }

        public void setNext(Boy next) {
            this.next = next;
        }
    }
}
