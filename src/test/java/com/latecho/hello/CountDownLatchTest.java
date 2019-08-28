package com.latecho.hello;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Wythe Chao
 * @email hi@latecho.me
 * @description
 */
@Slf4j
public class CountDownLatchTest implements Runnable {

    static final CountDownLatch latch = new CountDownLatch(10);
    static final CountDownLatchTest demo = new CountDownLatchTest();

    @Override
    public void run() {
        try {
            Thread.sleep(new Random().nextInt(10) * 1000);
            log.debug("check complete");
        } catch (InterruptedException e) {
            log.error("线程睡眠出现异常，错误信息：{}", e.getMessage(), e);
        } finally {
            latch.countDown();
        }
    }

    @Test
    public void testCheck() throws Exception{
        ExecutorService exec = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            exec.submit(demo);
        }
        // 等待检查，等待所有的线程都执行完毕
        latch.await();

        log.debug("Fire!");
        // 关闭线程池
        exec.shutdown();
    }

}
