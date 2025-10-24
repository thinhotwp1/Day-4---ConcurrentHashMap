import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentHashMapAtomicSolution {
    public static void main(String[] args) throws InterruptedException {
        /**
         * Bằng cách dùng compute(), ConcurrentHashMap đảm bảo rằng toàn bộ thao tác "lấy giá trị, tính toán, và cập nhật" cho key "counter"
         * sẽ được thực hiện như một hành động nguyên tử, được bảo vệ bởi khóa của bucket đó.

         * Bên trong hàm ConcurrentHashMap.compute(), ConcurrentHashMap sẽ tính hashCode() của key và đặt Synchonize ngay trên node đầu tiên của bucket đó,
         * vì vậy khi luồng khác muốn update thì cần đợi khối Synchonize chạy xong -> Thread safe

         * Nếu trong môi trường đa luồng, cần cập nhật một key trong map nhiều lần thì cần dùng hàm ConcurrentHashMap.compute()
         * -> Tóm lại, nếu cần dùng map trong môi trường đa luồng thì dùng ConcurrentHashMap và ConcurrentHashMap.compute()
         * --> Cần chú ý khi khối ConcurrentHashMap.compute() chạy thì nó sẽ khóa các luồng khác, vì vậy logic tính toán cần được tối ưu nhất để tránh performance
         */
        ConcurrentHashMap<String, Integer> counterMap = new ConcurrentHashMap<>();
        counterMap.put("counter", 0);
        ExecutorService executor = Executors.newFixedThreadPool(10);

        try {
            for (int i = 0; i < 10; i++) {
                executor.submit(() -> {
                    for (int j = 0; j < 1000; j++) {
                        // Đây là cách làm ĐÚNG và NGUYÊN TỬ
                        counterMap.compute("counter", (key, counter) -> counter + 1);
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