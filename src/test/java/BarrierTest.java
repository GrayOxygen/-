import barrier.BattleService;
import barrier.PresentService;
import org.testng.annotations.Test;

import java.util.concurrent.CyclicBarrier;

/**
 * Barrier是什么
 * 若干线程必须等待一个线程执行完才会执行，与countdownlatch的区别是，可重用
 * 若干线程调用了CyclicBarrier的await方法，进入阻塞状态就叫做parties
 * <p>
 * 如何用
 * 如下面例子所示
 * 值得注意的是：
 * 如果调用await的线程，interrupt了或者await超时则不会再阻塞
 * 如果其他线程interrupt另一个party，则抛出异常BrokernBarrierException
 * reser方法可以重置barrier为初始状态，其他正在waiting或者还没reach到barrier的线程将会停止运行，抛出BrokenBarrierException
 * <p>
 * <p>
 * 何时用
 * 如“所有运动员都到场了，才能开始比赛”的场景
 */
public class BarrierTest {
    @Test
    public void testBarrier() throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new PresentService());
        Thread t1 = new Thread(new BattleService(cyclicBarrier), "线程1");
        Thread t2 = new Thread(new BattleService(cyclicBarrier), "线程2");
        Thread t3 = new Thread(new BattleService(cyclicBarrier), "线程3");
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        //结果类似如下，三个线程reach到barrier，即等待present service执行完，barrier就打开，三开线程开始执行
        //但是发现present service好像都是根据那三个parties中选一个线程来执行的，我的理由是提升效率，因为其中一个在wait
        //执行完present service再去执行自己的线程提升效率
//        线程2选手们已经到位了，准备就绪...
//        线程2表演开始...
//        线程1表演开始...
//        线程3表演开始...
    }
}