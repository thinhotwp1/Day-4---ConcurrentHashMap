import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HashMapProblem {
    public static void main(String[] args) throws InterruptedException {
        // Dùng HashMap - KHÔNG thread-safe
        Map<Integer, String> map = new HashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(10);

        HashMapUtils.testPerformanceMap(map, executor);

        // Kết quả thường sẽ KHÁC 10,000, ví dụ 7418
        // Tệ hơn: Chương trình có thể bị treo hoặc văng ConcurrentModificationException
        System.out.println("Kích thước cuối cùng của HashMap: " + map.size());

        // Xem ConcurrentHashMapSolution để thấy cách giải quyết vấn đề này
    }
}
