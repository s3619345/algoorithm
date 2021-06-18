package com.sense.newots.util;

/**
 */

import java.util.HashMap;

/**
 *HashMap 的代理类 实现链式添加元素
 * example：  HashMapProxy hashMapProxy   = new HashMapProxy();
 *            hashMapProxy.putObject("a","b").putObject("c","d");
 * @param <K>
 * @param <V>
 */
public class HashMapProxy<K,V> extends HashMap<K,V> {

    /**
     * @Description 重写HashMap的所有构造函数---start
     * @Date 2020/4/14 14:03
     */
    public HashMapProxy() {
        super();
    }
 /*
  public HashMapProxy(int initialCapacity) {
        super(initialCapacity);
    }


    public HashMapProxy(Map<? extends K, ? extends V> m) {
        super(m);
    }


  public HashMapProxy(int initialCapacity, float loadFactor) {
        super(initialCapacity,loadFactor);
    }*/
    //重写HashMap的所有构造函数---end

    /**
     * 对 HashMap 的 put() 的方法进行封转返回  HashMapProxy 来实现 链式添加
     * @param key
     * @param value
     * @return
     */
    public HashMapProxy putObject(K key,V value){
        this.put(key, value);
        return this;
    }

}