import blockingqueue.MsgConsumer;
import blockingqueue.MsgProducer;
import org.testng.annotations.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 阻塞队列用于实现生产者消费者模式十分方便，不像wait notify那样写起来十分繁琐
 */
public class BlockingQueueTest {
    @Test
    public void testBlockingQueue() throws InterruptedException {
        BlockingQueue blockingQueue = new LinkedBlockingQueue();
        Thread producer = new Thread(new MsgProducer(blockingQueue));
        Thread consumer = new Thread(new MsgConsumer(blockingQueue));
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }
}
