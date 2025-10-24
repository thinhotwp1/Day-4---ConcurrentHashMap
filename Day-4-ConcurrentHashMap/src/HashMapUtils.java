import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class HashMapUtils {
    // Add 10.000 phần tử
    static void testPerformanceMap(Map<Integer, String> concurrentHashMap, ExecutorService executorConcurrentHashMap) throws InterruptedException {
        try {
            for (int i = 0; i < 10; i++) {
                final int threadId = i;
                executorConcurrentHashMap.submit(() -> {
                    for (int j = 0; j < 1000; j++) {
                        int key = (threadId * 1000) + j;
                        concurrentHashMap.put(key, "Value-" + key);
                    }
                });
            }
        } finally {
            executorConcurrentHashMap.shutdown();
            executorConcurrentHashMap.awaitTermination(1, TimeUnit.MINUTES);
        }
    }
}
