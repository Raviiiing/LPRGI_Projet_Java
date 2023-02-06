import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     Cette classe permet de gérer la base de données.
 * </p>
 * @author Yann AMOURS
 * @version 1.0
 */
public class GestionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/mabd?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; // URL de la BD
    private static final String USERNAME = "root"; // Nom d'utilisateur de la BD
    private static final String PASSWORD = "root"; // Mot de passe de la BD
    private final Connection connection; // Connexion à la BD

    /**
     * @throws RuntimeException En cas d'erreur lors de la connexion à la BD
     */
    public GestionBD(){
        try {
            // Ajout du driver JDBC pour MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            Logger.log(e.toString(), LogLevel.ERROR);
        }
        try {
            // Connexion à la BD avec les informations de connexion
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            Logger.log(e.toString(), LogLevel.ERROR);
            throw new RuntimeException("Erreur de connexion à la base de données");
        }
    }

    /**
     * <p>
     *     Cette méthode permet de créer les tables de la BD si elles n'existent pas.
     *     Elle permet de créer la table "lecteur" si elle n'existe pas.
     *     Elle permet de créer la table "livre" si elle n'existe pas.
     * </p>
     */
    public void createTables(){
        // Requêtes SQL pour créer les tables
        String createTableLivre = "CREATE TABLE IF NOT EXISTS livres (id INT NOT NULL AUTO_INCREMENT, titre VARCHAR(100), auteur VARCHAR(100), date_publication DATE, PRIMARY KEY (id));";
        String createTableAuteur = "CREATE TABLE IF NOT EXISTS lecteurs (id INT NOT NULL AUTO_INCREMENT, nom VARCHAR(100), prenom VARCHAR(100), PRIMARY KEY (id));";
        try {
            // Préparation de la requête pour créer la table "livre"
            PreparedStatement preparedStatement = connection.prepareStatement(createTableLivre);
            // Exécution de la requête pour créer la table "livre"
            preparedStatement.executeUpdate();
            Logger.log("Table livres: OK", LogLevel.DEBUG);

            // Préparation de la requête pour créer la table "lecteur"
            preparedStatement = connection.prepareStatement(createTableAuteur);
            // Exécution de la requête pour créer la table "lecteur"
            preparedStatement.executeUpdate();
            Logger.log("Table auteurs: OK", LogLevel.DEBUG);
        } catch (SQLException e) {
            Logger.log(e.toString(), LogLevel.ERROR);
        }
    }

    /**
     * <p>
     *     Cette méthode permet de retourner une liste de tous les livres de la BD.
     * </p>
     * @return La liste des livres de la BD
     */
    public List<Livre> getLivres() {
        List<Livre> livres = new ArrayList<>();
        // Requête SQL pour récupérer tous les livres de la BD
        String selectLivres = "SELECT * FROM livres;";
        try {
            // Préparation de la requête pour récupérer tous les livres de la BD
            PreparedStatement preparedStatement = connection.prepareStatement(selectLivres);
            // Exécution de la requête pour récupérer tous les livres de la BD
            preparedStatement.executeQuery();
            // Récupération du résultat de la requête
            ResultSet resultSet = preparedStatement.getResultSet();
            // Traitement du résultat de la requête
            while (resultSet.next()){
                livres.add(new Livre(resultSet.getString("titre"), resultSet.getString("auteur"), resultSet.getDate("date_publication").toString()));
            }
        } catch (SQLException e) {
            Logger.log(e.toString(), LogLevel.ERROR);
        }
        return livres;
    }

    /**
     * <p>
     *     Cette méthode permet de retourner une liste de tous les lecteurs de la BD.
     * </p>
     * @return La liste des lecteurs de la BD
     */
    public List<Lecteur> getLecteurs() {
        List<Lecteur> lecteurs = new ArrayList<>();
        // Requête SQL pour récupérer tous les lecteurs de la BD
        String selectLecteur = "SELECT * FROM lecteurs;";
        try {
            // Préparation de la requête pour récupérer tous les lecteurs de la BD
            PreparedStatement preparedStatement = connection.prepareStatement(selectLecteur);
            // Exécution de la requête pour récupérer tous les lecteurs de la BD
            preparedStatement.executeQuery();
            // Récupération du résultat de la requête
            ResultSet resultSet = preparedStatement.getResultSet();
            // Traitement du résultat de la requête
            while (resultSet.next()){
                lecteurs.add(new Lecteur(resultSet.getString("nom"), resultSet.getString("prenom")));
            }
        } catch (SQLException e) {
            Logger.log(e.toString(), LogLevel.ERROR);
        }
        return lecteurs;
    }

    /**
     * <p>
     *     Cette méthode permet d'ajouter un livre à la BD.
     * </p>
     * @param livre Le livre à ajouter à la BD
     * @return true si l'ajout s'est bien passé, false sinon
     */
    public boolean addLivre(Livre livre){
        // Requête SQL pour ajouter un livre à la BD
        String insertLivre = "INSERT INTO livres (titre, auteur, date_publication) VALUES (?, ?, ?);";
        try {
            // Préparation de la requête pour ajouter un livre à la BD
            PreparedStatement preparedStatement = connection.prepareStatement(insertLivre);
            // Ajout des paramètres à la requête
            preparedStatement.setString(1, livre.getTitre());
            preparedStatement.setString(2, livre.getAuteur());
            preparedStatement.setDate(3, java.sql.Date.valueOf(livre.getDate_publication()));
            // Exécution de la requête pour ajouter un livre à la BD
            preparedStatement.executeUpdate();
            Logger.log("Livre added to database", LogLevel.DEBUG);
            return true;
        } catch (Exception e) {
            Logger.log(e.toString(), LogLevel.ERROR);
            return false;
        }
    }

    /**
     * <p>
     *     Cette méthode permet d'ajouter un lecteur à la BD.
     * </p>
     * @param lecteur Le lecteur à ajouter à la BD
     * @return true si l'ajout s'est bien passé, false sinon
     */
    public boolean addLecteur(Lecteur lecteur){
        // Requête SQL pour ajouter un lecteur à la BD
        String insertLecteur = "INSERT INTO lecteurs (nom, prenom) VALUES (?, ?);";
        try {
            // Préparation de la requête pour ajouter un lecteur à la BD
            PreparedStatement preparedStatement = connection.prepareStatement(insertLecteur);
            // Ajout des paramètres à la requête
            preparedStatement.setString(1, lecteur.getNom());
            preparedStatement.setString(2, lecteur.getPrenom());
            // Exécution de la requête pour ajouter un lecteur à la BD
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            Logger.log(e.toString(), LogLevel.ERROR);
            return false;
        }
    }
}
