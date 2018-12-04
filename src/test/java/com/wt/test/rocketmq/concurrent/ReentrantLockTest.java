package com.wt.test.rocketmq.concurrent;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author WuTian
 * @date 2018-12-03 14:36
 * @description
 */
public class ReentrantLockTest {
    public static void main(String[] args) {
        final ReentrantLock lock = new ReentrantLock();
      try {
          lock.lock();
          lock.lock();
          new Thread(() -> {
              try {
                  lock.lock();
                  System.out.println("get lock.");
              } finally {
                  lock.unlock();
              }
          }).start();
      }finally {
          lock.unlock();
      }

    }
}
