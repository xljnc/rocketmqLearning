package com.wt.test.rocketmq.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WuTian
 * @date 2018-12-03 14:50
 * @description
 */
public class JvmCrashTest {
    public static void main(String[] args) {
        case2();
    }

    private static void case1() {
        List<Byte[]> list = new ArrayList<>();
        while (true) {
            Byte[] bytes = new Byte[1024];
            list.add(bytes);
        }
    }

    private static void case2() {
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
