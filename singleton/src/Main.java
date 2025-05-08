import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {

        Logger logger = Logger.getInstance();
        logger.log("Запускаем программу");
        logger.log("Просим пользователя ввести входные данные для списка");
        System.out.print("Размер списка: ");
        int long1 = Integer.parseInt(s.nextLine());
        logger.log("Введите размер списка: " + long1);
        System.out.print("Верхняя граница: ");
        int upperLimit = Integer.parseInt(s.nextLine());
        logger.log("Введите верхнюю границу для значений: " + upperLimit);

        logger.log("Создаём и наполняем список");
        System.out.print("Список: ");
        String randomList = s.nextLine();
        List<Integer> source = new ArrayList<>(long1);
        String[] numbers = randomList.trim().split("\\s+");
        if (long1 != numbers.length) {
            throw new IllegalArgumentException("Кол-во символов не равно: " + long1);
        } else {
            for (String s : numbers) {
                if (Integer.parseInt(s) < upperLimit) {
                    source.add(Integer.valueOf(s));
                }
            }
        }
        logger.log("Вот случайный список: " + randomList);
        logger.log("Просим пользователя ввести входные данные для фильтрации");

        System.out.print("Введите порог для фильтра: ");
        int filterInt = Integer.parseInt(s.nextLine());
        Filter filter = new Filter(filterInt);
        List<Integer> result = filter.filterOut(source);
        logger.log("Выводим результат на экран");
        System.out.print("Отфильтрованный список: ");
        result.forEach(System.out::print);
        System.out.println();
        logger.log("Завершаем программу");


    }
}