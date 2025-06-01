import java.util.Scanner;

public class ConsoleIO implements InputOutput {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    @Override
    public void print(String message) {
        System.out.print(message);
    }

    @Override
    public void printf(String format, Object... args) {
        System.out.printf(format, args);
    }
}