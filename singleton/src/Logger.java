import java.util.Date;

public class Logger {
    protected int num = 1;
    Date date = new Date();

    public void log(String msg) {
        System.out.println("[" + date.toString() + " " + num++ + "] " + msg);
    }

    private static Logger logger = null;

    private Logger() {
    }

    public static Logger getInstance() {
        synchronized (Logger.class) {
            if (logger == null) {
                logger = new Logger();
            }
        }
        return logger;
    }
}