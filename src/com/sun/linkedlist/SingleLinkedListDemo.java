package com.sun.linkedlist;


import sun.security.util.Length;

import java.util.Stack;
import java.util.logging.Level;

/**
 * @author gentleman
 * @description 实现单链表的增删改查功能
 * @create 2020-11-06-21:44
 */
public class SingleLinkedListDemo {
    public static void main(String[] args) {
        //定义英雄
        HeroNode heroNode1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode heroNode2 = new HeroNode(2, "吴用", "智多星");
        HeroNode heroNode3 = new HeroNode(3, "卢俊义", "玉麒麟");
        HeroNode heroNode4 = new HeroNode(4, "林冲", "豹子头");
        //定义链表
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        //将英雄存入到链表中
//        singleLinkedList.add(heroNode1);
//        singleLinkedList.add(heroNode2);
//        singleLinkedList.add(heroNode4);
//        singleLinkedList.add(heroNode3);
        singleLinkedList.addByOrder(heroNode1);
        singleLinkedList.addByOrder(heroNode2);
        singleLinkedList.addByOrder(heroNode4);
        singleLinkedList.addByOrder(heroNode3);
        //测试修改
        singleLinkedList.update(new HeroNode(2, "无用", "智多星"));
        //测试删除
        singleLinkedList.del(4);
        //测试链表的遍历
        singleLinkedList.list();
        //测试获取链表的长度
        int length = getLength(singleLinkedList.getHead());
        System.out.println(length);
        //测试链表中查找倒数第k个节点
        HeroNode indexNOde = getIndexNOde(singleLinkedList.getHead(), 1);
        System.out.println(indexNOde);
        //测试链表的翻转一
        reversetList(singleLinkedList.getHead());
        singleLinkedList.list();
        //测试单链表的翻转二
        reversePrint(singleLinkedList.getHead());
    }

    /**
     * @param
     * @return
     * @description: 单链表的翻转方式一
     */
    public static void reversetList(HeroNode head) {
        //如果当前链表为空，或者只有一个节点，无需反转，直接返回
        if (head.next == null || head.next.next == null) {
            return;
        }
//定义一个辅助的指针(变量)，帮助我们遍历原来的链表
        HeroNode cur = head.next;
        // 指向当前节点[cur]的下一个节点
        HeroNode next = null;
        HeroNode reverseHead = new HeroNode(0, "", "");
//遍历原来的链表，每遍历一个节点，就将其取出，并放在新的链表reverseHead 的最前端
        while (cur != null) {
            //先暂时保存当前节点的下一个节点，因为后面需要使用
            next = cur.next;
            //将cur 的下一个节点指向新的链表的最前端
            cur.next = reverseHead.next;
            //将cur 连接到新的链表上
            reverseHead.next = cur;
            //让cur 后移
            cur = next;
        }
//将head.next 指向reverseHead.next , 实现单链表的反转
        head.next = reverseHead.next;
    }

    /**
     * @param
     * @return
     * @description: 单链表的翻转 方式二
     * 利用栈先进后出的特点，实现逆序打印
     */
    public static void reversePrint(HeroNode head) {
        //如果链表为空或只有一个节点，无需翻转，直接返回
        if (head.next == null || head.next.next == null) {
            return;
        }
        //创建一个栈，将各个节点压入栈
        Stack<HeroNode> stack = new Stack<>();
        HeroNode cur = head.next;
        //将链表的所有节点压入栈1
        while (cur != null) {
            stack.push(cur);
            //后移节点，依次压入栈
            cur = cur.next;
        }
        //将栈中的节点进行打印出栈
        while (stack.size() > 0) {
            System.out.println(stack.pop());
        }
    }

    /**
     * @param
     * @return
     * @description: 查找单链表中倒数第k个节点
     * 思路：1，编写一个方法，接收head方法，同时接收一个index
     * 2，index表示是倒数第index个节点
     * 3，先把链表从头到尾遍历，得到链表的总长度getLength
     * 4，得到size后，我们从链表的第一个开始遍历（size-index）个，就可以得到
     * 5，如果找到了，则返回该节点，否则返回null
     */
    public static HeroNode getIndexNOde(HeroNode head, int index) {
        //判断链表是否为空
        if (head.next == null) {
            return null;
        }
        //获取链表的长度
        int size = getLength(head);
        //当遍历到size-index位置，就是倒数的第k个节点
        //先做index的检验
        if (index <= 0 || index > size) {
            return null;
        }
        //定义辅助变量。循环到倒数的index
        HeroNode cur = head.next;
        for (int i = 0; i < size - index; i++) {
            cur = cur.next;
        }
        return cur;
    }

    /**
     * @param head 链表的头节点
     * @return
     * @description: 获取单链表的节点的个数（如果是带头节点的链表，要求不记录头节点）
     * @date
     * @author
     */
    public static int getLength(HeroNode head) {
        //空链表
        if (head.next == null) {
            return 0;
        }
        //定义一个辅助变量，没有统计头节点
        int length = 0;
        HeroNode cur = head.next;
        while (cur != null) {
            length++;
            //遍历链表
            cur = cur.next;
        }
        return length;
    }
}

/**
 * @param
 * @author sunzhaozhao
 * @description: 管理我们的英雄，相当于一个单链表
 * @return
 * @date 2020/11/6 21:54
 */
class SingleLinkedList {
    /**
     * @description: 先初始化一个头节点，头节点不要动，不存放具体的数据
     * @param null 1
     * @return
     * @date 2020/11/9 22:51
     * @author sunzhaozhao
     */
    private HeroNode head = new HeroNode(0, "", "");

    //返回头节点
    public HeroNode getHead() {
        return head;
    }

    /**
     * 添加节点到单向链表
     * 思路：当不考虑编号顺序时
     * 1，找到当前链表的最后节点
     * 2，将最后这个节点的next指向新的节点
     */
    public void add(HeroNode heroNode) {
        //因为head节点不能动，因此我们需要一个辅助遍历temp
        HeroNode temp = head;
        //遍历链表，找到最后
        while (true) {
            if (temp.next == null) {
                break;
            }
            //如果没有找到最后，将temp后移
            temp = temp.next;
        }
        //当退出while循环时，temp就指向了链表的最后
        //将这个节点的next指向新的节点
        temp.next = heroNode;
    }

    /**
     * 第二种方式在添加英雄时，根据排名将英雄插入到指定位置
     * 如果有这个排名，则添加失败，并给出提示
     */
    public void addByOrder(HeroNode heroNode) {
        //头节点不能动，需要一个辅助指针
        HeroNode temp = head;
        //标志添加的编号是否存在，默认为false
        boolean flag = false;
        while (true) {
            //当temp在链表的最后时
            if (temp.next == null) {
                break;
            }
            //找到位置，在temp的后面插入
            if (temp.next.no > heroNode.no) {
                break;
            }
            //后移，遍历当前链表
            temp = temp.next;
        }
        //判断flag的值，不能添加，说明编号已经存在
        if (flag) {
            System.out.println("添加的英雄已经存在" + heroNode.no);
        } else {
            //插入到链表中，temp的后面
            heroNode.next = temp.next;
            temp.next = heroNode;
        }
    }

    /**
     * @param
     * @return
     * @description:修改节点的信息，根据no编号来修改，即no编号不能改
     * @date 2020/11/9 22:17
     * @author sunzhaozhao
     */
    public void update(HeroNode newHeroNode) {
        //判断是否为空
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        //找到要修改的节点，根据no编号
        //定义一个辅助变量
        HeroNode temp = head.next;
        boolean flag = false;
        while (true) {
            if (temp == null) {
                //表示遍历完链表
                break;
            }
            if (temp.no == newHeroNode.no) {
                //表示找到
                flag = true;
                break;
            }
            temp = temp.next;
        }
        //根据flag判断是否找到要修改的节点
        if (flag) {
            temp.name = newHeroNode.name;
            temp.nickname = newHeroNode.nickname;
        } else {
            //表示没有找到
            System.out.println("没有找到要修改的节点编号" + newHeroNode.no);
        }
    }

    /**
     * @param
     * @return
     * @description: 删除节点
     * 1，head节点不能动，因此我们需要一个temp辅助节点找到待删除节点的前一个节点
     * 2，说明我们在比较时。时temp.next.no和需要删除的节点的no比较
     * @date
     * @author
     */
    public void del(int no) {
        HeroNode temp = head;
        //标志是否找到待删除节点的位置
        boolean flag = false;
        while (true) {
            if (temp.next == null) {
                break;
            }
            //表示找到待删除节点的位置
            if (temp.next.no == no) {
                flag = true;
                break;
            }
            //temp后移，进行遍历
            temp = temp.next;
        }
        if (flag) {
            //如果找到，进行删除
            temp.next = temp.next.next;
        } else {
            System.out.println("删除的节点不存在" + no);
        }
    }

    /**
     * @param
     * @return
     * @description: 显示链表
     * @date 2020/11/9 22:34
     * @author sunzhaozhao
     */
    public void list() {
        //判断链表是否为空
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        //定义一个辅助变量进行遍历
        HeroNode temp = head.next;
        while (true) {
            //判断是否到链表最后
            if (temp == null) {
                break;
            }
            //输出节点的信息
            System.out.println(temp);
            //将temp后移
            temp = temp.next;
        }
    }
}

/**
 * @author o
 * @description:定义HeroNode，每个HeroNode对象就是一个节点
 * @return
 * @date 2020/11/6 21:50
 */
class HeroNode {
    //编号
    public int no;
    //姓名
    public String name;
    //称号
    public String nickname;
    //指向下一个节点
    public HeroNode next;

    public HeroNode(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}