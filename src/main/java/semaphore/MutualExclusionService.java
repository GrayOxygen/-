package semaphore;

import java.util.concurrent.Semaphore;

/**
 * 信号量实现多线程下互斥访问
 */
public class MutualExclusionService {
    private final Semaphore semaphore;

    public MutualExclusionService(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public void mutualExclusion() throws InterruptedException {
        semaphore.acquire();
        System.out.println(Thread.currentThread().getName() + "-->开始执行业务逻辑..." + semaphore);
        Thread.sleep(2000);
        System.out.println(Thread.currentThread().getName() + "-->执行业务逻辑结束...");
        semaphore.release();
    }
}
