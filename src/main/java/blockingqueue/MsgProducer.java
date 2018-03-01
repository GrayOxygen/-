package blockingqueue;

import java.util.concurrent.BlockingQueue;

public class MsgProducer implements Runnable {
    private final BlockingQueue blockingQueue;

    public MsgProducer(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            Message message = new Message();
            message.setId(i + "");
            message.setContent("我是第" + i + "条消息");
            try {
                blockingQueue.put(message);
                System.out.println("生产了=====" + message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
