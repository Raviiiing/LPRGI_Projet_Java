import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;

/**
 * <p>
 *     Cette classe permet de prendre en charge un lecteur.
 * </p>
 * @author Yann AMOURS
 * @version 1.0
 * @see Serializable
 * @see ObjectInputStream
 * @see ObjectOutputStream
 * @see Socket
 * @see List
 */
public class Lecteur implements Serializable {
    private String nom;
    private String prenom;

    /**
     * @param nom Nom du lecteur
     * @param prenom Prénom du lecteur
     */
    public Lecteur(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    /**
     * Constructeur vide, principalement utilisé pour l'affichage des lecteurs
     */
    public Lecteur(){ }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String toString(){
        return "Nom: " + nom + " Prénom: " + prenom;
    }

    /**
     * <p>
     *     Cette méthode permet de récupérer la liste des lecteurs.
     *     Puis, elle affiche la liste des lecteurs.
     *     Elle est utilisée dans la classe Client.
     * </p>
     * @param HOST Adresse IP du serveur
     * @param PORT Port du serveur
     * @see Socket
     * @see ObjectInputStream
     * @see ObjectOutputStream
     * @see ObjectRequest
     * @see ObjectResponse
     */
    public void voirListe(String HOST, int PORT){
        System.out.println("LECTEUR > Liste des lecteurs:");
        try {
            // On se connecte au serveur.
            Socket socket = new Socket(HOST, PORT);
            Logger.log("Connexion établie", LogLevel.DEBUG);
            // On envoie une requête au serveur.
            Logger.log("Demande d'affichage des lecteurs", LogLevel.DEBUG);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(new ObjectRequest("afficher lecteurs", null));

            // On récupère la réponse du serveur.
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectResponse objectResponse = (ObjectResponse) ois.readObject();
            Logger.log("Réponse reçue: " + objectResponse.getMessage(), LogLevel.DEBUG);

            // On affiche la liste des lecteurs.
            List<Lecteur> result = (List<Lecteur>) objectResponse.getResult();
            for (Lecteur l : result) {
                System.out.println(l);
            }
            // On ferme la connexion avec le serveur.
            socket.close();
        } catch (Exception e) {
            Logger.log(e.toString(), LogLevel.ERROR);
        }
    }

    /**
     * <p>
     *     Cette méthode permet d'ajouter un lecteur.
     *     Elle est utilisée dans la classe Client.
     * </p>
     * @param host Adresse IP du serveur
     * @param port Port du serveur
     * @see Socket
     * @see ObjectInputStream
     * @see ObjectOutputStream
     * @see ObjectRequest
     * @see ObjectResponse
     */
    public void ajouterLecteur(String host, int port){
        try {
            // On se connecte au serveur.
            Socket socket = new Socket(host, port);
            Logger.log("Connexion établie", LogLevel.DEBUG);

            // On envoie une requête au serveur.
            Logger.log("Envoi du lecteur", LogLevel.DEBUG);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(new ObjectRequest("ajouter lecteur", this));

            // On récupère la réponse du serveur.
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectResponse objectResponse = (ObjectResponse) ois.readObject();
            Logger.log(objectResponse.getMessage(), LogLevel.INFO);

            // On ferme la connexion avec le serveur.
            socket.close();
        } catch (Exception e) {
            Logger.log(e.toString(), LogLevel.ERROR);
        }
    }
}
