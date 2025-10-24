import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentHashMapProblem {
    public static void main(String[] args) throws InterruptedException {
        Map<String, Integer> counterMap = new ConcurrentHashMap<>();
        counterMap.put("counter", 0);

        ExecutorService executor = Executors.newFixedThreadPool(10);

        try {
            for (int i = 0; i < 10; i++) {
                executor.submit(() -> {
                    /**
                     * Đây là một lưu ý quan trọng cho Senior. ConcurrentHashMap bảo vệ cấu trúc, nhưng nó không tự động làm cho các thao tác phức hợp trở nên nguyên tử.
                     *
                     * Vấn đề (Cách làm sai): Giả sử chúng ta muốn 10 luồng cùng tăng một bộ đếm có key là "counter".
                     */
                    for (int j = 0; j < 1000; j++) {
                        int counter = counterMap.get("counter");
                        counterMap.put("counter", counter + 1);

                        /**
                         * Đây là một Tình trạng tranh đua (Race Condition).
                         *
                         * Luồng A đọc, currentValue = 0.
                         * Luồng B đọc, currentValue = 0.
                         * Luồng A ghi, put("counter", 0 + 1). Map["counter"] = 1.
                         * Luồng B ghi, put("counter", 0 + 1). Map["counter"] = 1.
                         *
                         * Kết quả sai: Đáng lẽ phải là 2, nhưng cuối cùng lại là 1.
                         */
                    }
                });
            }
        } finally {
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.MINUTES);
        }

        // Kết quả sai, ví dụ 2251
        System.out.println("counter của ConcurrentHashMap khi không dùng hàm compute: " + counterMap.get("counter"));
        // Xem ConcurrentHashMapComputeSolution để thấy cách giải quyết vấn đề này
    }
}
