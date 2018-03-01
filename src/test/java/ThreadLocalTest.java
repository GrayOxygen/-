import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import threadlocal.PerThreadFormatter;

import java.util.Date;

/**
 * ThreadLocal是什么
 * 线程封闭的一种线程安全实现方式
 * 即让变量不能共享，而是每个线程拥有对象的单独拷贝
 * <p>
 * 何时用
 * 比如事务、security context、数据库连接
 */
public class ThreadLocalTest {
    private Date date;

    @BeforeClass
    public void init() {
        date = new Date();
    }

    @Test(threadPoolSize = 5, invocationCount = 4)
    public void testThreadLocal() {
        System.out.println(Thread.currentThread().getName() + "----" + PerThreadFormatter.getFormatter().format(date));
    }
}
