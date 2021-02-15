package com.sun.linkedlist;

/**
 * @author gentleman
 * @description ...
 * @create 2020-11-06-21:42
 */
public class DoubleLinkedListDemo {
    public static void main(String[] args) {
        //创建节点
        Animal animal1 = new Animal(1, "狗", "黄色");
        Animal animal2 = new Animal(2, "小白兔", "白色");
        Animal animal3 = new Animal(3, "熊猫", "黑白色");
        Animal animal4 = new Animal(4, "熊", "棕色");
        //创建双向链表
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        doubleLinkedList.add(animal1);
        doubleLinkedList.add(animal2);
        doubleLinkedList.add(animal3);
        doubleLinkedList.add(animal4);
        //测试遍历
        doubleLinkedList.list();
        //测试修改
        Animal newAnimal = new Animal(4, "熊", "黑色");
        doubleLinkedList.update(newAnimal);
        doubleLinkedList.list();
        //测试删除
        doubleLinkedList.del(4);
        doubleLinkedList.list();
    }

}

/**
 * 创建一个双向链表的类
 *
 */
class DoubleLinkedList {
    //初始化一个头节点，不存放具体的数据
    Animal head = new Animal(0, "", "");

    //返回头节点
    public Animal getHead() {
        return head;
    }

    //遍历双向链表
    public void list() {
        //判断链表是否为空
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        //头节点不能动，需要一个辅助变量进行遍历
        Animal temp = head.next;
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

    //添加节点到双向链表的最后
    public void add(Animal animal) {
        //head节点不能移动，因此我们需要一个辅助遍历temp
        Animal temp = head;
        //遍历链表到最后
        while (true) {
            //找到链表的最后
            if (temp.next == null) {
                break;
            }
            //将temp后移
            temp = temp.next;
        }
        //退出循环表示temp指向了链表的最后
        //形成双向链表
        temp.next = animal;
        animal.pre = temp;
    }

    //修改节点的内容，与单链表一致
    public void update(Animal animal) {
        //判断是否为空
        if (head.next == null) {
            System.out.println("链表为空~");
            return;
        }
        //找到需要修改的节点，根据no编号修改
        //定义一个辅助变量
        Animal temp = head.next;
        //表示是否找到该节点
        boolean flag = false;
        while (true) {
            if (temp == null) {
                //表示遍历完链表
                break;
            }
            //找到修改的节点
            if (temp.no == animal.no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        //根据flag判断是否找到要修改的节点
        if (flag) {
            temp.name = animal.name;
            temp.color = animal.color;
        } else {
            System.out.println("没有找到该节点" + animal.no);
        }
    }

    //删除节点
    //对于双向链表，我们可以直接找到要删除的这个节点
    //找到可以直接删除
    public void del(int no) {
        //判断当前链表是否为空
        if (head.next == null) {
            System.out.println("链表为空，不能删除");
            return;
        }
        //定义辅助变量
        Animal temp = head.next;
        //标志删除节点的位置
        boolean flag = false;
        while (true) {
            if (temp == null) {
                break;
            }
            if (temp.no == no) {
                //找到待删除节点的前一个节点temp
                flag = true;
                break;
            }
            //temp后移，遍历
            temp = temp.next;
        }
        //判断flag
        if (flag) {
            //表示找到进行删除
            temp.pre.next = temp.next;
            //如果是最后一个节点，不需要执行，否则会出现空指针异常
            if (temp.next != null) {
                temp.next.pre = temp.pre;
            }
        } else {
            System.out.println("要删除的节点不存在");
        }
    }
}

/**
 *定义一个动物类
 */
class Animal {
    public int no;
    public String name;
    public String color;
    //指向下一个节点,默认为null
    public Animal next;
    //指向前一个节点,默认为null;
    public Animal pre;

    public Animal(int no, String name, String color) {
        this.no = no;
        this.name = name;
        this.color = color;
    }
    @Override
    public String toString() {
        return "Animal{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}