package modele;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 * Cette classe fait l'interface avec la base de donnÃƒÂ©es.
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
     * @param dbName Le nom de la base de donnÃƒÂ©es
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
     * Ouvre la base de donnÃƒÂ©es spÃƒÂ©cifiÃƒÂ©e
     * @return True si la connection ÃƒÂ  ÃƒÂ©tÃƒÂ© rÃƒÂ©ussie. False sinon.
     */
    public boolean connexion (){
        try{
            // Etabli la connection
            connexion = DriverManager.getConnection("jdbc:sqlite:"+this.dbName);
            // DÃƒÂ©clare l'objet qui permet de faire les requÃƒÂªtes
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
     * Ferme la connection ÃƒÂ  la base de donnÃƒÂ©es
     * @return True si la connection a bien ÃƒÂ©tÃƒÂ© fermÃƒÂ©e. False sinon.
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
     * Permet de faire une requÃƒÂªte SQL
     * @param requete La requÃƒÂªte SQL (avec un ";" ÃƒÂ  la fin)
     * @return Un ResultSet contenant le rÃƒÂ©sultat de la requÃƒÂªte
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
     * Permet de modifier une entrÃƒÂ©e de la base de donnÃƒÂ©es.</br>
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
     * Permet de crÃ©er les tables si elles n'existent pas
     */
    public void creationTables() throws SQLException{
    	requete = connexion.createStatement();
    	String sqlCreationTableGroupe =  "CREATE TABLE IF NOT EXISTS GROUPE " +
                "(IDGROUPE INT PRIMARY KEY     NOT NULL," +
                " NOM           VARCHAR2(50) NOT NULL)";
        requete.executeUpdate(sqlCreationTableGroupe);
        
        String sqlCreationTableAdresse =  "CREATE TABLE IF NOT EXISTS ADRESSE " +
                "(IDADRESSE INT PRIMARY KEY     NOT NULL," +
                " ADRESSE           VARCHAR2(50) NOT NULL)";
        requete.executeUpdate(sqlCreationTableAdresse);

        String sqlCreationTableMail =  "CREATE TABLE IF NOT EXISTS MAIL " +
                "(IDMAIL INT PRIMARY KEY     NOT NULL," +
                " MAIL           VARCHAR2(50) NOT NULL)"; 	// TODO transact pour vérifier que le mail est OK
        requete.executeUpdate(sqlCreationTableMail);       
        
        String sqlCreationTableTypeTelephone =  "CREATE TABLE IF NOT EXISTS TYPE_TELEPHONE " +
                "(IDTYPETELEPHONE INT PRIMARY KEY     NOT NULL," +
                "TYPE           VARCHAR2(50) NOT NULL)"; 
        requete.executeUpdate(sqlCreationTableTypeTelephone);  
        
        String sqlCreationTableTelephone =  "CREATE TABLE IF NOT EXISTS TELEPHONE " +
                "(IDMAIL INT PRIMARY KEY     NOT NULL," +
        		"IDTYPETELEPHONE INT CONSTRAINT fk_telephone_typetelephone references TYPE_TELEPHONE(IDTYPETELEPHONE)" +
                "TELEPHONE           VARCHAR2(50) NOT NULL)"; 
        requete.executeUpdate(sqlCreationTableTelephone);  

        String sqlCreationTableContact = "CREATE TABLE IF NOT EXISTS CONTACT " +
                "(IDCONTACT INT PRIMARY KEY     NOT NULL," +
                " NOM           VARCHAR2(50)  NOT NULL, " +
                " PRENOM        VARCHAR2(50)      , " +
                " DDN			DATE, " +
                " ADRESSE       VARCHAR2(50), " +
                " TELEPHONE     VARCHAR2(50), " +
                " FAX      		VARCHAR2(50), " +
                " MAIL       	VARCHAR2(50), " +
                " IDGROUPE        VARCHAR2(50) CONSTRAINT fk_contact_groupe REFERENCES GROUPE (IDGROUPE), " +
                " PHOTO       BLOB, " +
                " FAVORIS         BOOLEAN)";
        requete.executeUpdate(sqlCreationTableContact);
        
        
        
        
        
    }

    public static void main(String[] args) throws Exception {
		Database db = new Database("Database.db");
		db.connexion();
		DAO dao = new DAO(db);

		Contact c = new Contact(1,"Contact1","", null,"","","","",1,null,false);

		System.out.println(dao.CreerGroupe(new Groupe(2,"nop")));
		System.out.println(dao.TrouverGroupe("nop").getNom());
		System.out.println(dao.CreerContact(c));
		System.out.println(dao.TrouverContact(1));
//		System.out.println(dao.SupprimerContact(c));
	}
}