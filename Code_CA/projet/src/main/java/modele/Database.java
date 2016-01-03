package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Cette classe fait l'interface avec la base de donnÃ©es.
 * @author CA Team
 * @versio 0.1
 */
public class Database

{
    private String      dbName;
    public  Connection  connexion;
    private Statement   requete;

    /**
     * Constructeur de la classe Database
     * @param dbName Le nom de la base de donnÃ©es
     */
    public Database (String dbName){
        // Charge le driver sqlite JDBC en utilisant le class loader actuel
        try{
            Class.forName("org.sqlite.JDBC");
        }catch (ClassNotFoundException e1){
            System.err.println(e1.getMessage());
        }
        this.dbName     = dbName;
        this.connexion = null;
    }

    /**
     * Ouvre la base de donnÃ©es spÃ©cifiÃ©e
     * @return True si la connection Ã  Ã©tÃ© rÃ©ussie. False sinon.
     */
    public boolean connexion (){
        try{
            // Etabli la connection
            connexion = DriverManager.getConnection("jdbc:sqlite:"+this.dbName);
            // DÃ©clare l'objet qui permet de faire les requÃªtes
            requete = connexion.createStatement();
            creationTables();
            requete.executeUpdate("PRAGMA synchronous = OFF;");
            requete.setQueryTimeout(30);
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Ferme la connection Ã  la base de donnÃ©es
     * @return True si la connection a bien Ã©tÃ© fermÃ©e. False sinon.
     */
    public boolean deconnexion (){
        try{
            if(connexion != null)
                connexion.close();
             return true;
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Permet de faire une requÃªte SQL
     * @param requete La requÃªte SQL (avec un ";" Ã  la fin)
     * @return Un ResultSet contenant le rÃ©sultat de la requÃªte
     */
    public ResultSet getResultatRequete (String requete){
        try{
            return this.requete.executeQuery(requete);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Permet de modifier une entrÃ©e de la base de donnÃ©es.</br>
     * @param requete La requete SQL de modification
     */
    public void setValeur (String requete){
        try{
            this.requete.executeUpdate(requete);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Permet de créer les tables si elles n'existent pas
     */
    public void creationTables() throws SQLException{
    	requete = connexion.createStatement();
    	String sqlCreationTableGroupe =  "CREATE TABLE IF NOT EXISTS GROUPE " +
                "(IDROUPE INT PRIMARY KEY     NOT NULL," +
                " NOM           CHAR(50) NOT NULL)";
        requete.executeUpdate(sqlCreationTableGroupe);


        String sqlCreationTableContact = "CREATE TABLE IF NOT EXISTS CONTACT " +
                "(ID INT PRIMARY KEY     NOT NULL," +
                " NOM           CHAR(50)  NOT NULL, " +
                " PRENOM        CHAR(50)      , " +
                " DDN			DATE, " +
                " ADRESSE       CHAR(50), " +
                " TELEPHONE     CHAR(50), " +
                " FAX      		CHAR(50), " +
                " MAIL       	CHAR(50), " +
                " GROUPE        CHAR(50) CONSTRAINT fk_contact_groupe REFERENCES GROUPE (idGroupe), " +
                " FAVORIS       BOOLEAN, " +
                " PHOTO         BLOB)";
        requete.executeUpdate(sqlCreationTableContact);
    }

    public static void main(String[] args) {
		Database db = new Database("Database.db");
		db.connexion();
	}
}