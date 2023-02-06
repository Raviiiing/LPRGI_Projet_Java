/**
 * <p>
 *     Cette classe implémente le serveur.
 * </p>
 * @author Yann AMOURS
 * @version 1.0
 * @see Server
 */
public class ServerMain {
    public static void main(String[] args) {
        try {
            // Création du serveur
            Server server = new Server();
            // Démarrage du serveur
            server.start();
            // Arrêt du serveur
            server.stop();
        } catch (Exception e) {
            Logger.log(e.toString(), LogLevel.ERROR);
        }
    }
}
