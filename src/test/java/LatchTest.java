import latch.AnalysisInfosService;
import latch.CollectInfosService;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.CountDownLatch;

/**
 * 闭锁测试
 * <p>
 * 是什么？
 * 必须等待其他若干线程执行完才会执行，wait和notify也能实现同样效果，但是更麻烦
 * 另外，CountDownLatch是一次性的，Barrier是可重用的
 * <p>
 * 如何用？
 * CountDownLatch的await方法让当前线程等待计数器递减为0或者其他线程interrupted了才会执行
 * 其他线程调用CountDownLatch的countDown方法使计数器减1
 * <p>
 * 何时用？
 * 当一个线程需要等待其他的一个或多个线程执行完，才能执行的情况
 */
public class LatchTest {
    private static CountDownLatch countDownLatch;

    @BeforeClass
    public void init() {
        countDownLatch = new CountDownLatch(1);
    }

    @Test
    public void testLatch() throws InterruptedException {
        //定义计数器数字为1，保证先收集再分析
        Thread t1 = new Thread(new CollectInfosService(countDownLatch));
        Thread t2 = new Thread(new AnalysisInfosService(countDownLatch));
        t2.start();
        t1.start();
        t1.join();
        t2.join();
    }
}
