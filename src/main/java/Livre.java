import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;

/**
 * <p>
 *     Cette classe permet de prendre en charge un livre.
 * </p>
 * @author Yann AMOURS
 * @version 1.0
 * @see Serializable
 * @see ObjectInputStream
 * @see ObjectOutputStream
 * @see Socket
 */
public class Livre implements Serializable {
    private String titre;
    private String auteur;
    private String date_publication;

    /**
     * @param titre Titre du livre
     * @param auteur Auteur du livre
     * @param date_publication Date de publication du livre (format : YYYY-MM-DD)
     */
    public Livre(String titre, String auteur, String date_publication) {
        this.titre = titre;
        this.auteur = auteur;
        this.date_publication = date_publication;
    }

    /**
     * Constructeur vide, principalement utilisé pour l'affichage des livres
     */
    public Livre(){ }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getDate_publication() {
        return date_publication;
    }

    public void setDate_publication(String date_publication) {
        this.date_publication = date_publication;
    }


    public String toString(){
        return "Titre: " + titre + " Auteur: " + auteur + " Date de publication: " + date_publication;
    }

    /**
     * <p>
     *     Cette méthode permet de récupérer la liste des livres.
     *     Puis, elle affiche la liste des livres.
     *     Elle est utilisée dans la classe Client.
     * </p>
     * @param HOST Adresse IP du serveur
     * @param PORT Port du serveur
     */
    public void voirListe(String HOST, int PORT){
        System.out.println("LIVRE > Liste des livres:");
        try {
            // Connexion au serveur
            Socket socket = new Socket(HOST, PORT);
            Logger.log("Connexion établie", LogLevel.DEBUG);

            // Envoi de la requête
            Logger.log("Demande d'affichage des livres", LogLevel.DEBUG);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(new ObjectRequest("afficher livres", null));

            // Réception de la réponse
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectResponse objectResponse = (ObjectResponse) ois.readObject();
            Logger.log("Réponse reçue: " + objectResponse.getMessage(), LogLevel.DEBUG);

            // Traitement de la réponse (affichage des livres)
            List<Livre> result = (List<Livre>) objectResponse.getResult();
            for (Livre l : result) {
                System.out.println(l);
            }
            // Fermeture de la connexion
            socket.close();
        } catch (Exception e) {
            Logger.log(e.toString(), LogLevel.ERROR);
        }
    }

    /**
     * <p>
     *     Cette méthode permet d'ajouter un livre.
     *     Elle est utilisée dans la classe Client.
     * </p>
     * @param host Adresse IP du serveur
     * @param port Port du serveur
     */
    public void ajouterLivre(String host, int port){
        try {
            // Connexion au serveur
            Socket socket = new Socket(host, port);
            Logger.log("Connexion établie", LogLevel.DEBUG);

            // Envoi de la requête au serveur
            Logger.log("Envoi du livre", LogLevel.DEBUG);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(new ObjectRequest("ajouter livre", this));

            // Réception de la réponse du serveur
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectResponse objectResponse = (ObjectResponse) ois.readObject();
            Logger.log(objectResponse.getMessage(), LogLevel.INFO);

            // Fermeture de la connexion
            socket.close();
        } catch (Exception e) {
            Logger.log(e.toString(), LogLevel.ERROR);
        }
    }

}
