/**
 * <p>
 *     Enumération des différents niveaux de log.
 * </p>
 * @author Yann AMOURS
 * @version 1.0
 */
enum LogLevel {
    DEBUG,
    INFO,
    WARNING,
    ERROR
}

/**
 * <p>
 *     Cette classe permet de logger des messages dans la console.
 *     Elle permet de logger des messages de différents niveaux.
 * </p>
 * @author Yann AMOURS
 * @version 1.0
 * @see LogLevel
 */
public class Logger {
    private static final boolean log = true;

    /**
     * <p>
     *     Cette méthode permet de logger un message d'un niveau donné.
     * </p>
     * @param message Le message à logger.
     * @param logLevel Le niveau de log.
     */
    public static void log(String message, LogLevel logLevel) {
        switch (logLevel) {
            case DEBUG:
                if(log){
                    System.out.println("\033[0;32mDEBUG > " + message + "\033[0m");
                }
                break;
            case INFO:
                System.out.println("\033[0;34mINFO > " + message + "\033[0m");
                break;
            case WARNING:
                System.out.println("\033[0;33mWARNING > " + message + "\033[0m");
                break;
            case ERROR:
                System.out.println("\033[0;31mERROR > " + message + "\033[0m");
                break;
            default:
                System.out.println("CLIENT > "+message);
                break;
        }
    }
}
