package semaphore;

import java.util.ArrayList;
import java.util.List;

/**
 * 生产者消费者缓冲区
 */
public class Buffer {
    private List<String> items = new ArrayList<String>();

    public void addItem(String item) {
        items.add(item);
    }

    public String getNewestItem() {
        return items.get(items.size() - 1);
    }

}
