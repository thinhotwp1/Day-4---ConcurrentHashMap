import utils.HashMapUtils;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap; // <-- THAY ĐỔI Ở ĐÂY
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentHashMapSolution {
    public static void main(String[] args) throws InterruptedException {
        /**
         * Trước ConcurrentHashMap, Hashtable là giải pháp để tránh lỗi sai lệch của HashMap trong môi trường đa luồng
         * Thay vì synchonized lock ở bucket, Hashtable chỉ đơn giản là lock toàn bộ node trong table và cho update tuần tự
         * -> Điều này sẽ khiến performance tụt nghiêm trọng
         */

        Map<Integer, String> hashtable = new Hashtable<>();
        ExecutorService executorHashtable = Executors.newFixedThreadPool(10);
        long start = System.nanoTime();
        HashMapUtils.testPerformanceMap(hashtable, executorHashtable);
        long end = System.nanoTime();
        long time1 = end - start;
        // Kết quả LUÔN LUÔN là 10,000
        System.out.println("Kích thước cuối cùng của Hashtable: " + hashtable.size() + ", thời gian chạy của Hashtable: " + time1 + " ns");


        Map<Integer, String> concurrentHashMap = new ConcurrentHashMap<>();
        ExecutorService executorConcurrentHashMap = Executors.newFixedThreadPool(10);
        start = System.nanoTime();
        HashMapUtils.testPerformanceMap(concurrentHashMap, executorConcurrentHashMap);
        end = System.nanoTime();
        long time2 = end - start;
        // Kết quả LUÔN LUÔN là 10,000
        System.out.println("Kích thước cuối cùng của ConcurrentHashMap: " + concurrentHashMap.size() + ", thời gian chạy của ConcurrentHashMap: " + time2 + " ns");

        System.out.println(" ---> ConcurrentHashMap nhanh hơn Hashtable ~ " + time1 / time2 + " lần");

        // ==> Xem ConcurrentHashMapProblem để thấy vấn đề của ConcurrentHashMap

    }
}