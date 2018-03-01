package threadlocal;

import java.text.SimpleDateFormat;

/**
 * 对应单个线程的日期格式化
 */
public class PerThreadFormatter {
    private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy/MM/dd");
        }
    };

    public static SimpleDateFormat getFormatter() {
        System.out.println("threadlocal所属线程：" + Thread.currentThread().getName());
        return threadLocal.get();
    }

}
