package com.wt.test.rocketmq.concurrent;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WuTian
 * @date 2018-12-03 10:43
 * @description
 */
public class ConcurrentHashMapTest {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        map.get("key1");
    }
}
