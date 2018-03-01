package latch;

import java.util.concurrent.CountDownLatch;

public class AnalysisInfosService implements Runnable {
    private final CountDownLatch countDownLatch;

    public AnalysisInfosService(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public void run() {
        try {
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + "数据分析完成...");
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
