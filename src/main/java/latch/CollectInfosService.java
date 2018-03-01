package latch;

import java.util.concurrent.CountDownLatch;

public class CollectInfosService implements Runnable {
    private final CountDownLatch countDownLatch;

    public CollectInfosService(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + "收集信息完毕...");
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
