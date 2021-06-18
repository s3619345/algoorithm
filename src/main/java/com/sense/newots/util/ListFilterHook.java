package com.sense.newots.util;

/**
 * Created by cookie on 2018/6/13 0013.
 */
public interface ListFilterHook<T> {
    boolean match(T t);
}
