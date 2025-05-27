import java.util.*;

public class Main {
    //    public static final Map<Integer, Integer> sizeToFreq = Collections.synchronizedMap(new HashMap<>());
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    static int COUNT = 100;
    static int processedCount;
    static int maxValueNew = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread();
        Thread thread3 = new Thread();


        Thread finalThread = thread3;
        thread1 = new Thread(() -> {
            for (int i = 0; i < COUNT; i++) {
                synchronized (sizeToFreq) {
                    int tempCount = countSymbolString(generateRoute("RLRFR", 100));
                    sizeToFreq.merge(tempCount, 1, Integer::sum);
                    processedCount++;
                    sizeToFreq.notifyAll();
                }
            }
            finalThread.interrupt();

        });

        thread3 = new Thread(() -> {
            synchronized (sizeToFreq) {
                while (!Thread.interrupted()) {
                    try {
                        sizeToFreq.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    for (int i : sizeToFreq.keySet()) {
                        if (sizeToFreq.get(i) > maxValueNew) {
                            maxValueNew = sizeToFreq.get(i);
                            System.out.println("У нас новый лидер: " + maxValueNew);
                        }
                    }
                }

            }
        });

        thread1.setPriority(Thread.MAX_PRIORITY);
        thread3.setPriority(Thread.MIN_PRIORITY);

        thread1.start();
        thread3.start();
        try {
            thread1.join(); // Ожидаем завершения thread1
            thread3.join(); // Ожидаем завершения thread2
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread3.interrupt();


//        new Thread(() -> {
//            synchronized (sizeToFreq) {
//
//                while (processedCount < COUNT) {
//                    try {
//                        sizeToFreq.wait();
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//
//                int maxKey = 0;
//                int maxValue = 0;
//
//                for (int i : sizeToFreq.keySet()) {
//                    if (sizeToFreq.get(i) > maxValue) {
//                        maxKey = i;
//                        maxValue = sizeToFreq.get(i);
//                    }
//                }
//                System.out.printf("Самое частое количество повторений %d (встретилось %d раз)\n", maxKey, maxValue);
//                sizeToFreq.remove(maxKey);
//                System.out.println("Другие размеры:");
//                for (int keys : sizeToFreq.keySet()) {
//                    System.out.printf("- %d (%d раз) \n", keys, sizeToFreq.get(keys));
//                }
//
//            }
//        }).start();
//

    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static int countSymbolString(String string) {
        int result = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == 'R') {
                result++;
            }
        }
        return result;
    }
}