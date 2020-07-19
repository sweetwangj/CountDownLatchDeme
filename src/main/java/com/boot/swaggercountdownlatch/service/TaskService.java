package com.boot.swaggercountdownlatch.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@Service
public class TaskService {
    @Autowired
    Executor threadPoolTaskExecutor;

    public void doTaskV1()
    {
        long start=System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            task1();
            task2();
            task3();
            task4();
        }
       long end=System.currentTimeMillis();
        System.out.println("接口耗时 "+ (end-start));
    }

    private void task1() {
        try {
            Thread.sleep(1000);
            System.out.println("task1执行完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void task2() {
        try {
            Thread.sleep(1000);
            System.out.println("task2执行完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void task3() {
        try {
            Thread.sleep(1000);
            System.out.println("task3执行完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void task4() {
        try {
            Thread.sleep(1000);
            System.out.println("task4执行完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public  void doTaskV2() {
        long start=System.currentTimeMillis();
        // 创建线程池，其中核心线程10，也是我期望的最大并发数，最大线程数和队列大小都为30，即我的总任务数
        //ThreadPoolExecutor threadPoolTaskExecutor = new ThreadPoolExecutor(10, 30, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(30));


        // 模拟遍历参数集合
        for (int i = 0; i < 5; i++) {
            // 初始化CountDownLatch，大小为30
            CountDownLatch countDownLatch = new CountDownLatch(4);
            // 往线程池提交任务
            threadPoolTaskExecutor.execute(() -> {
                task1();
                // 子线程完成，countDownLatch执行countDown
                countDownLatch.countDown();
            });
            threadPoolTaskExecutor.execute(() -> {
                task2();
                // 子线程完成，countDownLatch执行countDown
                countDownLatch.countDown();
            });
            threadPoolTaskExecutor.execute(() -> {
                task3();
                // 子线程完成，countDownLatch执行countDown
                countDownLatch.countDown();
            });
            threadPoolTaskExecutor.execute(() -> {
                task4();
                // 子线程完成，countDownLatch执行countDown
                countDownLatch.countDown();
            });

            // 阻塞当前线程，知道所有子线程都执行countDown方法才会继续执行
            try {
                countDownLatch.await(1,TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 打印线程池运行状态
//            System.out.println("线程池中线程数目：" + threadPoolTaskExecutor.getPoolSize() + "，队列中等待执行的任务数目：" +
//                    threadPoolTaskExecutor.getQueue().size() + "，已执行结束的任务数目：" + threadPoolTaskExecutor.getCompletedTaskCount());
//        }
            // 标记多线程关闭，但不会立马关闭
//        threadPoolTaskExecutor.shutdown();
        }
        // 打印线程池运行状态
//        System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +
//                executor.getQueue().size() + "，已执行结束的任务数目：" + executor.getCompletedTaskCount());

        long end=System.currentTimeMillis();
        System.out.println("接口耗时 "+ (end-start));
    }
}
