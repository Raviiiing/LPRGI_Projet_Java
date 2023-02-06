/**
 * <p>
 *     Cette classe implémente le client.
 * </p>
 * @author Yann AMOURS
 * @version 1.0
 * @see Client
 */
public class ClientMain {
    public static void main(String[] args) {
        Client client = new Client("localhost", 1234);
        client.start();
    }
}
