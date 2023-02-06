import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     Cette classe permet de prendre en charge le serveur.
 *     Elle permet de gérer les requêtes des clients.
 *     Elle permet de gérer la base de données.
 * </p>
 * @author Yann AMOURS
 * @version 1.0
 */
public class Server {

    private static final int PORT = 1234; // Port à utiliser pour les connexions réseau
    private final ServerSocket serverSocket; // Socket du serveur
    private boolean running; // Etat du serveur

    /**
     * @throws IOException En cas d'erreur lors de la création du socket
     */
    public Server() throws IOException {
        serverSocket = new ServerSocket(PORT);
    }

    /**
     * <p>
     *     Cette méthode permet de démarrer le serveur.
     *     Elle permet de gérer les requêtes des clients.
     * </p>
     * @throws IOException En cas d'erreur lors de la création du socket
     */
    public void start() throws IOException {
        running = true;
        // Création d'un Gestionnaire de requêtes pour gérer les requêtes
        GestionBD gestionBD = new GestionBD();
        // Création des tables si elles n'existent pas
        gestionBD.createTables();
        while (running) {
            // En attente de la connexion d'un client
            Socket socket = serverSocket.accept();
            Logger.log("Nouvelle connexion", LogLevel.INFO);
            // Traitement de la requête du client
            try{
                // Création des flux d'entrée et de sortie
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectRequest objectRequest = (ObjectRequest) ois.readObject();
                Logger.log("Requête reçue: " + objectRequest.getType(), LogLevel.DEBUG);
                boolean success = false; // Indique si la requête a été traitée avec succès
                // Selon le type de requête du client
                switch (objectRequest.getType()) {
                    case "ajouter lecteur" -> {
                        Lecteur lecteur = (Lecteur) objectRequest.getObject();
                        // Ajout du lecteur dans la base de données
                        success = gestionBD.addLecteur(lecteur);
                        // Envoi de la réponse au client via le flux de sortie en fonction du succès de la requête
                        if (success) {
                            oos.writeObject(new ObjectResponse("ajouter lecteur", "Lecteur ajouté!", null));
                        } else {
                            oos.writeObject(new ObjectResponse("ajouter lecteur", "Erreur lors de l'ajout du lecteur, vérifiez les arguments envoyé!", null));
                        }
                    }
                    case "ajouter livre" -> {
                        Livre livre = (Livre) objectRequest.getObject();
                        // Ajout du livre dans la base de données
                        success = gestionBD.addLivre(livre);
                        // Envoi de la réponse au client via le flux de sortie en fonction du succès de la requête
                        if (success) {
                            oos.writeObject(new ObjectResponse("ajouter livre", "Livre ajouté!", null));
                        } else {
                            oos.writeObject(new ObjectResponse("ajouter livre", "Livre non ajouté! Vérifier les arguments envoyé! ", null));
                        }
                    }
                    case "afficher livres" ->{
                        // Récupération de la liste des livres dans la base de données via le Gestionnaire de requêtes
                        // Envoi de la réponse au client via le flux de sortie
                        oos.writeObject(new ObjectResponse("afficher livres", "Liste des livres:", gestionBD.getLivres()));
                    }
                    case "afficher lecteurs" ->{
                        // Récupération de la liste des lecteurs dans la base de données via le Gestionnaire de requêtes
                        // Envoi de la réponse au client via le flux de sortie
                        oos.writeObject(new ObjectResponse("afficher lecteurs", "Liste des lecteurs:", gestionBD.getLecteurs()));
                    }
                }
                // Fermeture des flux
                oos.close();
                ois.close();
            }catch (Exception e){
                Logger.log(e.toString(), LogLevel.ERROR);
            }
        }
    }

    /**
     * <p>
     *     Cette méthode permet d'arrêter le serveur.
     * </p>
     */
    public void stop(){
        running = false;
    }
}