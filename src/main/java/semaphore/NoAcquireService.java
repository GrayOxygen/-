package semaphore;

import java.util.concurrent.Semaphore;

/**
 * 不acquire permit，直接release一个permit
 * 于是可以在一个非owner的线程中进行release别的线程acquire到的permit，这和Lock不同
 */
public class NoAcquireService {
    public static void justRelease(Semaphore semaphore) {
        semaphore.release();
    }
}
