import java.util.Map;
import java.util.concurrent.ConcurrentHashMap; // <-- THAY ĐỔI Ở ĐÂY
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentHashMapSolution {
    public static void main(String[] args) throws InterruptedException {
        // Dùng ConcurrentHashMap - Thread-safe
        Map<Integer, String> map = new ConcurrentHashMap<>(); // <-- THAY ĐỔI Ở ĐÂY
        ExecutorService executor = Executors.newFixedThreadPool(10);

        try {
            for (int i = 0; i < 10; i++) {
                final int threadId = i;
                executor.submit(() -> {
                    for (int j = 0; j < 1000; j++) {
                        int key = (threadId * 1000) + j;
                        map.put(key, "Value-" + key);
                    }
                });
            }
        } finally {
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.MINUTES);
        }

        // Kết quả LUÔN LUÔN là 10,000
        System.out.println("Kích thước cuối cùng của ConcurrentHashMap: " + map.size());
    }
}