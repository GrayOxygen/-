package barrier;

/**
 * 确认所有player都到位的服务
 * 到位后BattleService开始执行自己battle表演
 */
public class PresentService implements Runnable {
    public void run() {
        try {
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + "选手们已经到位了，准备就绪...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
