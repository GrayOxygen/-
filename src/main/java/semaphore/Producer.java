package semaphore;

import java.util.concurrent.Semaphore;


/**
 * 生产者
 */
public class Producer implements Runnable {
    private final Semaphore producerSemaphore;
    private final Semaphore consumerSemaphore;
    private Buffer buffer;

    public Producer(Semaphore producerSemaphore, Semaphore consumerSemaphore, Buffer buffer) {
        this.producerSemaphore = producerSemaphore;
        this.consumerSemaphore = consumerSemaphore;
        this.buffer = buffer;
    }

    private void put(String item) {
        try {
            producerSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        buffer.addItem(item);
        System.out.println("生产了" + item);
        consumerSemaphore.release();
    }

    public void run() {
        for (int i = 0; i < 12222; i++) {
            put("货物" + i);
        }
    }
}
