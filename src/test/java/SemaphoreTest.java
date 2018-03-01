import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import semaphore.*;

import java.util.concurrent.Semaphore;

/**
 * Semaphore：持有若干数量的permit许可，各线程从semaphore中获得permit，往下执行，无许可时阻塞到permit被release将拿到permit为止，从而实现资源共享
 * <p>
 * 信号量大于0表示还有permit，小于等于0表示无permit线程阻塞
 * <p>
 * Semaphore使用场景：
 * 线程池、数据库连接池（信号量即池的大小），还可以实现生产者消费者模式
 * <p>
 * Semaphore与Lock的区别：
 * 1，Lock.unLock()前必须调用lock方法，semaphore可直接调用release方法无需acquire permit
 * 2，semaphore可在一个非owner的线程上实现死锁恢复，Lock无法做到
 */

public class SemaphoreTest {
    /**
     * 多线程下互斥访问
     * <p>因为下面的服务共用一个semaphore，permit为1，一个线程拿到后另一个线程必须等到release后才能拿到permit
     *
     * @throws InterruptedException
     */
    MutualExclusionService mutualExclusionService;
    private Semaphore consumeSemaphore;
    private Semaphore produceSemaphore;
    private Buffer buffer;

    /**
     * 1，release可在非owner线程之外的线程上来释放permit，并非要求acquire和release在同一个线程中
     * 2，如果没有acquire，直接release，permit仍然会加1
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(1);
        semaphore.acquire();
        //0
        System.out.println("业务逻辑执行中...可用许可数量：" + semaphore.availablePermits());
        NoAcquireService.justRelease(semaphore);
        //1
        System.out.println("业务逻辑执行完...可用许可数量：" + semaphore.availablePermits());
        Semaphore onlyReleaseSemaphore = new Semaphore(0);
        //0
        System.out.println("可用许可数量：" + onlyReleaseSemaphore.availablePermits());
        onlyReleaseSemaphore.release();
        //1
        System.out.println("可用许可数量：" + onlyReleaseSemaphore.availablePermits());

//       Semaphore consumeSemaphore = new Semaphore(0);
//       Semaphore produceSemaphore = new Semaphore(1);
//       Buffer buffer = new Buffer();
//
//        MsgProducer producer = new MsgProducer(produceSemaphore, consumeSemaphore, buffer);
//        Consumer consumer = new Consumer(produceSemaphore, consumeSemaphore, buffer);
//        new Thread(producer).start();
//        new Thread(consumer).start();
    }

    @BeforeClass
    public void before() {
        mutualExclusionService = new MutualExclusionService(new Semaphore(1));
        //消费者的信号量，设置为0，在生产之前阻塞从而先生产再消费的顺序
        consumeSemaphore = new Semaphore(0);
        produceSemaphore = new Semaphore(1);
        buffer = new Buffer();
    }

    /**
     * 多线程下互斥访问
     * <p>
     * 设置了线程池大小为2，调用2两次(理解为两个线程各去调用一次)
     *
     * @throws InterruptedException
     */
    @Test(threadPoolSize = 2, invocationCount = 2, timeOut = 100000)
    public void mutualExclusion() throws InterruptedException {
        //打印结果类似下面这种，一个线程开始到执行结束后才会开始另一个线程
//        TestNG-methods-1-->开始执行业务逻辑...java.util.concurrent.Semaphore@74a77258[Permits = 0]
//        TestNG-methods-1-->执行业务逻辑结束...
//        TestNG-methods-2-->开始执行业务逻辑...java.util.concurrent.Semaphore@74a77258[Permits = 0]
//        TestNG-methods-2-->执行业务逻辑结束...
        mutualExclusionService.mutualExclusion();
    }

    /**
     * 值得注意的一点：main方法会保证子线程执行完，而junit，testNG会出现测试方法先执行完，子线程未执行完就已经退出jvm
     * <p>
     * 为了用testNG，我就没在原来的类里边写Thread.start了，而是在把producer和consumer作为runnable，在外部start，此时就可join了
     */
    @Test(threadPoolSize = 2, invocationCount = 2, timeOut = 100000)
    public void produceConsume() throws InterruptedException {
        Producer producer = new Producer(produceSemaphore, consumeSemaphore, buffer);
        Consumer consumer = new Consumer(produceSemaphore, consumeSemaphore, buffer);
        Thread t1 = new Thread(producer);
        Thread t2 = new Thread(consumer);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

}
