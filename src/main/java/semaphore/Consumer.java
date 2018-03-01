package semaphore;

import java.util.concurrent.Semaphore;

public class Consumer implements Runnable {
    private final Semaphore producerSemaphore;
    private final Semaphore consumerSemaphore;
    private Buffer buffer;

    public Consumer(Semaphore producerSemaphore, Semaphore consumerSemaphore, Buffer buffer) {
        this.producerSemaphore = producerSemaphore;
        this.consumerSemaphore = consumerSemaphore;
        this.buffer = buffer;

    }

    private void take() {
        try {
            consumerSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("消费：" + buffer.getNewestItem());
        producerSemaphore.release();
    }


    public void run() {
        for (int i = 0; i < 11222; i++) {
            take();
        }
    }
}
