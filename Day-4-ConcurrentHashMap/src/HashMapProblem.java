import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HashMapProblem {
    public static void main(String[] args) throws InterruptedException {
        // Dùng HashMap - KHÔNG thread-safe
        Map<Integer, String> map = new HashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(10);

        try {
            for (int i = 0; i < 10; i++) {
                final int threadId = i;
                executor.submit(() -> {
                    for (int j = 0; j < 1000; j++) {
                        // Tính một key duy nhất cho mỗi lần put
                        int key = (threadId * 1000) + j;
                        map.put(key, "Value-" + key); // 10 thread tranh nhau update map -> map sau mỗi lần update của luồng này ghi đè map của luồng khác -> miss match data
                    }
                });
            }
        } finally {
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.MINUTES);
        }

        // Kết quả thường sẽ KHÁC 10,000, ví dụ 7418
        // Tệ hơn: Chương trình có thể bị treo hoặc văng ConcurrentModificationException
        System.out.println("Kích thước cuối cùng của HashMap: " + map.size());

        // Xem ConcurrentHashMapSolution để thấy cách giải quyết vấn đề này
    }
}
