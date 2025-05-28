import java.util.*;

public class Main {
    //    public static final Map<Integer, Integer> sizeToFreq = Collections.synchronizedMap(new HashMap<>());
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    static int COUNT = 100;
    static int processedCount;
    static int maxValueNew = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread thread1;
        Thread thread3;


        thread1 = new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (int i = 0; i < COUNT; i++) {
                synchronized (sizeToFreq) {
                    int tempCount = countSymbolString(generateRoute("RLRFR", 100));
                    sizeToFreq.merge(tempCount, 1, Integer::sum);
                    processedCount++;
                    sizeToFreq.notifyAll();
                }
            }

        });

        thread3 = new Thread(() -> {
            synchronized (sizeToFreq) {

                while (!Thread.interrupted()) {
                    try {
                        sizeToFreq.wait();
                    } catch (InterruptedException e) {
                        break;
                    }
                    if (Thread.interrupted()) {
                        break;
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


        thread1.start();
        thread3.start();
        thread1.join();
        thread3.interrupt();


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