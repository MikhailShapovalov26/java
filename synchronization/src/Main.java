import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    static int COUNT = 1000;
    static int processedCount;

    public static void main(String[] args) throws InterruptedException {
        Thread generatorThread = new Thread(() -> {
            for (int i = 0; i < COUNT; i++) {
                int tempCount = countSymbolString(generateRoute("RLRFR", 100));
                synchronized (sizeToFreq) {
                    sizeToFreq.merge(tempCount, 1, Integer::sum);
                }
                processedCount++;
            }
        });
        generatorThread.start();
        generatorThread.join();

        if(!sizeToFreq.isEmpty()) {
            Map.Entry<Integer, Integer> maxEntry = Collections.max(
                    sizeToFreq.entrySet(),
                    Map.Entry.comparingByValue()
            );

            System.out.printf("Самое частое количество повторений %d (встретилось %d раз)\n"
                    ,maxEntry.getKey()
                    ,maxEntry.getValue());
            System.out.println("Другие размеры:");
            sizeToFreq.forEach((key, value) -> {
                if(!key.equals(maxEntry.getKey())){
                    System.out.printf("- %d (%d раз)\n", key, value);
                }
            });
        }
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