package barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 每个BattleService线程表示一个选手的battle表演
 * 必须等PresentService确认所有选手都已到位才能进行表演
 */
public class BattleService implements Runnable {
    private final CyclicBarrier cyclicBarrier;

    public BattleService(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    public void run() {
        try {
            cyclicBarrier.await();
            System.out.println(Thread.currentThread().getName() + "表演开始...");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
