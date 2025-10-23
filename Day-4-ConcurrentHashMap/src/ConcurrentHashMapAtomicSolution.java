import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentHashMapAtomicSolution {
    public static void main(String[] args) throws InterruptedException {
        ConcurrentHashMap<String, Integer> counterMap = new ConcurrentHashMap<>();
        counterMap.put("counter", 0);
        ExecutorService executor = Executors.newFixedThreadPool(10);

        try {
            for (int i = 0; i < 10; i++) {
                executor.submit(() -> {
                    for (int j = 0; j < 1000; j++) {
                        // Đây là cách làm ĐÚNG và NGUYÊN TỬ
                        counterMap.compute("counter", (key, value) -> {
                            return value + 1; // Nếu có rồi, tăng lên 1
                        });
                    }
                });
            }
        } finally {
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.MINUTES);
        }

        // Kết quả LUÔN LUÔN là 10,000
        System.out.println("Giá trị cuối cùng của counter: " + counterMap.get("counter"));
    }
}