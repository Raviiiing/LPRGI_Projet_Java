import java.util.Scanner;

/**
 *     <p>
 *         Le client permet de prendre en charge les interactions avec l'utilisateur.
 *         Il agit en fonction des choix de l'utilisateur.
 *     </p>
 * @author Yann AMOURS
 * @version 1.0
 * @see Client
 * @see Scanner
 * @see Livre
 * @see Lecteur
 */
public class Client {

    private final String PREFIX = "CLIENT > ";
    private String HOST;
    private int PORT;

    /**
     * @param host L'adresse IP du serveur
     * @param port Le port du serveur
     */
    public Client(String host, int port){
        this.HOST = host;
        this.PORT = port;
    }

    /**
     * @see Client
     * @see Client#start()
     *    <p>
     *        Cette méthode permet de démarrer le client.
     *        Elle permet de prendre en charge les interactions avec l'utilisateur.
     *        Elle agit en fonction des choix de l'utilisateur.
     *    </p>
     */
    public void start(){
        System.out.println(PREFIX + "Démarage du client");
        System.out.println(PREFIX + "Merci de lire la documentation ainsi que le Readme.md, pour plus d'informations");
        System.out.println("\n");
        // Lancement du scanner pour la saisie de l'utilisateur via la console (System.in)
        Scanner sc = new Scanner(System.in);
        boolean continuer = true;
        while (continuer) {
            // En attente de la saisie de l'utilisateur
            String commande = sc.nextLine();
            // Gestion de la saisie de l'utilisateur
            switch (commande) {
                case "ajouter livre" -> {
                    // Demande de saisie des informations du livre
                    System.out.print(PREFIX + "Titre: ");
                    String titre = sc.nextLine();
                    System.out.print(PREFIX + "Auteur: ");
                    String auteur = sc.nextLine();
                    System.out.print(PREFIX + "Date de publication: ");
                    String datePublication = sc.nextLine();
                    Livre livre = new Livre(titre, auteur, datePublication);
                    // Envoi du livre au serveur via la méthode interne à l'objet Livre
                    livre.ajouterLivre(HOST, PORT);
                }
                case "ajouter lecteur" -> {
                    // Demande de saisie des informations du lecteur
                    System.out.print(PREFIX + "Nom: ");
                    String nom = sc.nextLine();
                    System.out.print(PREFIX + "Prénom: ");
                    String prenom = sc.nextLine();
                    Lecteur lecteur = new Lecteur(nom, prenom);
                    // Envoi du lecteur au serveur via la méthode interne à l'objet Lecteur
                    lecteur.ajouterLecteur(HOST, PORT);
                }
                case "afficher livres" -> {
                    // Gestion simple de l'affichage des livres via la méthode interne à l'objet Livre
                    Livre livre = new Livre();
                    livre.voirListe(HOST, PORT);
                }
                case "afficher lecteurs" -> {
                    // Gestion simple de l'affichage des lecteurs via la méthode interne à l'objet Lecteur
                    Lecteur lecteur = new Lecteur();
                    lecteur.voirListe(HOST, PORT);
                }
                case "quitter" -> {
                    // Arrêt du client
                    System.out.println(PREFIX + "Arrêt du client");
                    continuer = false;
                }
                case "aide" -> {
                    // Affichage de l'aide
                    System.out.println(PREFIX + "Liste des commandes:");
                    System.out.println(PREFIX + "ajouter livre");
                    System.out.println(PREFIX + "ajouter lecteur");
                    System.out.println(PREFIX + "afficher livres");
                    System.out.println(PREFIX + "afficher lecteurs");
                    System.out.println(PREFIX + "quitter");
                }
                default -> Logger.log("Commande inconnue, liste des commandes: aide", LogLevel.WARNING);
            }
        }
        // Fermeture du scanner car le client est arrêté
        sc.close();
    }
}
