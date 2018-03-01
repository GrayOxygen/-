package blockingqueue;

import java.util.concurrent.BlockingQueue;

public class MsgConsumer implements Runnable {
    private final BlockingQueue blockingQueue;

    public MsgConsumer(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = (Message) blockingQueue.take();
                System.out.println("消费了======" + message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
