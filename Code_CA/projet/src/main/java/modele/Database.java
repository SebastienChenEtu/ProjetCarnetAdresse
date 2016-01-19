package modele;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


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
            requete.executeUpdate("PRAGMA foreign_keys = ON;");
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

    // il faudra mettre en place les delete on cascade
    public void creationTables() throws SQLException{
    	requete = connexion.createStatement();
    	String sqlCreationTableGroupe =  "CREATE TABLE IF NOT EXISTS GROUPE " +
                "(IDGROUPE INT PRIMARY KEY     NOT NULL," +
                " NOM           VARCHAR2(50) NOT NULL)";
        requete.executeUpdate(sqlCreationTableGroupe);

        String sqlCreationTableAdresse =  "CREATE TABLE IF NOT EXISTS ADRESSE " +
                "(IDADRESSE INT PRIMARY KEY     NOT NULL," +
                "IDCONTACT INT CONSTRAINT fk_adresse_contact references contact(idcontact)  on delete cascade," +
                "ADRESSE           VARCHAR2(50) NOT NULL)";
        requete.executeUpdate(sqlCreationTableAdresse);

        String sqlCreationTableMail =  "CREATE TABLE IF NOT EXISTS MAIL " +
                "(IDMAIL INT PRIMARY KEY     NOT NULL," +
                "IDCONTACT INT CONSTRAINT fk_mail_contact references contact(idcontact) on delete cascade," +
                " MAIL           VARCHAR2(50) NOT NULL)"; 	// TODO transact pour vérifier que le mail est OK
        requete.executeUpdate(sqlCreationTableMail);

        String sqlCreationTableTelephone =  "CREATE TABLE IF NOT EXISTS TELEPHONE " +
                "(IDTELEPHONE INT  PRIMARY KEY   NOT NULL," +
        		"IDCONTACT INT CONSTRAINT fk_telephonecontact_contact references contact(idcontact) on delete cascade," +
                "TELEPHONE           VARCHAR2(50) NOT NULL)";
        requete.executeUpdate(sqlCreationTableTelephone);

        String sqlCreationTableContact = "CREATE TABLE IF NOT EXISTS CONTACT " +
                "(IDCONTACT INT PRIMARY KEY     NOT NULL," +
                " IDGROUPE        VARCHAR2(50) CONSTRAINT fk_contact_groupe REFERENCES GROUPE (IDGROUPE), " +
                " NOM           VARCHAR2(50)  NOT NULL, " +
                " FAVORIS         BOOLEAN," +
                " PRENOM        VARCHAR2(50), " +
                " DDN			DATE, " +
                " PHOTO       BLOB, " +
                " FAX      		VARCHAR2(50))";
        requete.executeUpdate(sqlCreationTableContact);
    }

    public static void main(String[] args) throws Exception {
		Database db = new Database("Database.db");
		db.connexion();
		DAO dao = new DAO(db);


// test pour importer une image locale
		File monImage = new File(".\\adrien.jpg");
		FileInputStream istreamImage = new FileInputStream(monImage);

		List<Adresse> adrPourC =  new LinkedList<Adresse>();
		List<Mail> mailsPourC = new LinkedList<Mail>();
		List<Telephone> telsPourC = new LinkedList<Telephone>();

		adrPourC.add(new Adresse(1, "adresse C"));
		mailsPourC.add(new Mail(1, "mail C"));
		mailsPourC.add(new Mail(2, "Mail D"));
		telsPourC.add(new Telephone(1, "Tel C"));
		telsPourC.add(new Telephone(2, "Tel D"));

		Contact c = new Contact(1,"test","test",new java.sql.Date(1),"fax",0,istreamImage, true);
		c.setAdresses(adrPourC);
		c.setMails(mailsPourC);
		c.setTelephones(telsPourC);

		Groupe g1 = new Groupe(0,"Groupe par défaut");
		Groupe g2 = new Groupe(2,"nop");

		try {
		System.out.println(dao.CreerGroupe(g1));
		System.out.println(dao.CreerGroupe(g2));
		System.out.println(dao.TrouverGroupe("nop"));


		System.out.println(dao.CreerContact(c));
		System.out.println(dao.TrouverContact(1));
		Contact testAdressesC = dao.TrouverContact(1);
		for (Adresse adresse : testAdressesC.getAdresses()) {
			System.out.println(adresse.getAdresse());
		}

		System.out.println(dao.SupprimerContact(c));
		System.out.println(dao.SupprimerGroupe(g1));
		System.out.println(dao.SupprimerGroupe(g2));
		}
		catch(Exception e)
		{
			System.err.println(e.toString());
		}

		// test pour lire une image récupérée directement dans la BD
//				ResultSet rs = db.requete.executeQuery("select photo from contact where idcontact = 1");
//				byte[] imgData = null;
//				if (rs.next()) {
//		            imgData = rs.getBytes("photo");//Here r1.getBytes() extract byte data from resultSet
//		        }
//				ByteArrayInputStream stream = new ByteArrayInputStream(imgData);
//				BufferedImage image = ImageIO.read(stream);

		// pour lire l'image dans une JFrame
//		BufferedImage image = ImageIO.read(istreamImage);
//        JLabel label = new JLabel(new ImageIcon(image));
//        JFrame f = new JFrame();
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        f.getContentPane().add(label);
//        f.pack();
//        f.setLocation(200,200);
//        f.setVisible(true);


	}
}