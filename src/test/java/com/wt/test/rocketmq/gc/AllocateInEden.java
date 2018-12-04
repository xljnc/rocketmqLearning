package com.wt.test.rocketmq.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WuTian
 * @date 2018-12-03 14:46
 * @description
 */
public class AllocateInEden {
    public static void main(String[] args) {
        List<Byte[]> list = new ArrayList<>();
        Byte[] obj1 = new Byte[1024*1024];
        Byte[] obj2 = new Byte[1024*1024];
        Byte[] obj3 = new Byte[1024*1024];
        Byte[] obj4 = new Byte[1024*1024];
    }
}
