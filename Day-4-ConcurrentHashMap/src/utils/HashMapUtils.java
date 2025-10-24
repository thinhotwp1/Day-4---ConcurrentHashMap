package utils;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class HashMapUtils {
    // Add 10.000 phần tử trong môi trường đa luồng
    public static void testPerformanceMap(Map<Integer, String> map, ExecutorService executorService) throws InterruptedException {
        try {
            for (int i = 0; i < 10; i++) {
                final int threadId = i;
                executorService.submit(() -> {
                    for (int j = 0; j < 1000; j++) {
                        int key = (threadId * 1000) + j;
                        map.put(key, "Value-" + key);
                    }
                });
            }
        } finally {
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        }
    }
}
