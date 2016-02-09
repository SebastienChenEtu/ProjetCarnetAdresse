package modele;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.hamcrest.core.IsSame;

import service.ServiceCarnetAdresse;


/**
 * Cette classe fait l'interface avec la base de donnÃƒÂ©es.
 * @author CA Team
 * @versio 0.1
 */
public class Database

{
	static String      dbName;
	static  Connection  connexion;
	static Statement   requete;

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
	 * @return True si la connection ÃƒÂ  ÃƒÂ©tÃƒÂ© rÃƒÂ©ussie. False sinon.
	 */
	public boolean connexion (){
		try{
			if(this.connexion == null){
				// Etabli la connection
				connexion = DriverManager.getConnection("jdbc:sqlite:"+this.dbName);
				// DÃƒÂ©clare l'objet qui permet de faire les requÃƒÂªtes
				requete = connexion.createStatement();
				creationTables();
				requete.executeUpdate("PRAGMA synchronous = OFF;");
				requete.executeUpdate("PRAGMA foreign_keys = ON;");
				requete.executeUpdate("PRAGMA journal_mode=WAL;");
				requete.setQueryTimeout(30);
			}
			return true;
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Ferme la connection ÃƒÂ  la base de donnÃƒÂ©es
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
	 * @param requete La requÃƒÂªte SQL (avec un ";" ÃƒÂ  la fin)
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
				"(IDGROUPE INTEGER PRIMARY KEY  AUTOINCREMENT   NOT NULL," +
				" NOM           VARCHAR2(50) NOT NULL)";
		requete.executeUpdate(sqlCreationTableGroupe);

		String sqlCreationTableAdresse =  "CREATE TABLE IF NOT EXISTS ADRESSE " +
				"(IDADRESSE INTEGER PRIMARY KEY  AUTOINCREMENT   NOT NULL," +
				"IDCONTACT INTEGER,"
				+ "IDTYPE INTEGER DEFAULT 0 CONSTRAINT fk_telephonecontact_type references type(idtype) ON DELETE SET DEFAULT,"
				+ "ADRESSE           VARCHAR2(50) NOT NULL, " +
				"FOREIGN KEY(IDCONTACT) REFERENCES CONTACT(IDCONTACT) on delete cascade)";
		requete.executeUpdate(sqlCreationTableAdresse);

		String sqlCreationTableMail =  "CREATE TABLE IF NOT EXISTS MAIL " +
				"(IDMAIL INTEGER PRIMARY KEY   AUTOINCREMENT  NOT NULL," +
				"IDCONTACT INTEGER,"
				+ "IDTYPE INTEGER DEFAULT 0 CONSTRAINT fk_telephonecontact_type references type(idtype) ON DELETE SET DEFAULT,"
				+ "MAIL           VARCHAR2(50) NOT NULL,"+
				"FOREIGN KEY(IDCONTACT) REFERENCES CONTACT(IDCONTACT) on delete cascade)";
		// TODO transact pour vérifier que le mail est OK
		requete.executeUpdate(sqlCreationTableMail);

		String sqlCreationTableTelephone =  "CREATE TABLE IF NOT EXISTS TELEPHONE " +
				"(IDTELEPHONE INTEGER  PRIMARY KEY  AUTOINCREMENT NOT NULL," +
				"IDCONTACT INTEGER,"
				+ "IDTYPE INTEGER DEFAULT 0 CONSTRAINT fk_telephonecontact_type references type(idtype) ON DELETE SET DEFAULT,"
				+ "TELEPHONE           VARCHAR2(50) NOT NULL,"+
				"FOREIGN KEY(IDCONTACT) REFERENCES CONTACT(IDCONTACT) on delete cascade)";
		requete.executeUpdate(sqlCreationTableTelephone);

		String sqlCreationTableContact = "CREATE TABLE IF NOT EXISTS CONTACT " +
				"(IDCONTACT INTEGER PRIMARY KEY    AUTOINCREMENT NOT NULL," +
				" IDGROUPE        INTEGER DEFAULT 0 CONSTRAINT fk_contact_groupe REFERENCES GROUPE (IDGROUPE) ON DELETE SET DEFAULT, " +
				" NOM           VARCHAR2(50)  NOT NULL, " +
				" FAVORIS         BOOLEAN," +
				" PRENOM        VARCHAR2(50), " +
				" DDN			DATE, " +
				" PHOTO       BLOB, " +
				" FAX      		VARCHAR2(50))";
		requete.executeUpdate(sqlCreationTableContact);

		// TODO il faudra gérer le on delete set default
		String sqlCreationType = "CREATE TABLE IF NOT EXISTS TYPE"
				+ "(IDTYPE INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ "LIBELLETYPE VARCHAR2(50))";
		requete.executeUpdate(sqlCreationType);

		try {
			insertionValeursInitiales();
		}
		catch (SQLException sqlExc)
		{
			System.out.println("certaines valeurs existaient déjà dans la base !");
		}


	}
	public void insertionValeursInitiales() throws SQLException
	{
		String sqlCreationGroupeDefaut = "insert into groupe (idgroupe, nom) values (0, 'Groupe par defaut');";
		requete.executeUpdate(sqlCreationGroupeDefaut);

		String sqlCreationTypeDefaut = "insert into type(idtype, libelletype) values (0, 'Aucun');";
		sqlCreationTypeDefaut = sqlCreationTypeDefaut + "insert into type(idtype, libelletype) values (1, 'Bureau');";
		sqlCreationTypeDefaut = sqlCreationTypeDefaut + "insert into type(idtype, libelletype) values (2, 'Domicile');";
		sqlCreationTypeDefaut = sqlCreationTypeDefaut + "insert into type(idtype, libelletype) values (3, 'Fixe');";
		sqlCreationTypeDefaut = sqlCreationTypeDefaut + "insert into type(idtype, libelletype) values (4, 'autre');";
		requete.executeUpdate(sqlCreationTypeDefaut);
	}



	public static void main(String[] args) throws Exception {
		ServiceCarnetAdresse service = new ServiceCarnetAdresse();

		File monImage = new File(".\\adrien.jpg");
		FileInputStream istreamImage = new FileInputStream(monImage);

		File monImage2 = new File(".\\original.jpeg");
		FileInputStream istreamImage2 = new FileInputStream(monImage2);

		Groupe g1 = new Groupe();
		g1.setNom("oui");

		Groupe g2 = new Groupe();
		g2.setNom("nop");

		g1 = service.CreerGroupe(g1);
		g2 = service.CreerGroupe(g2);

		Type t1 = new Type();
		t1.setLibelleType("Type1");

		t1 = service.CreerType(t1);
		System.out.println(service.TrouverType("Type1"));

		t1 = service.setLibelleType("Type1", "nouveauLibType");
		System.out.println(service.TrouverType("nouveauLibType"));


		Contact c = new Contact("t","test",new java.sql.Date(new Date().getTime()),"fax",2,istreamImage, true);
		java.sql.Date nouvelleDate = new java.sql.Date(new Date().getTime());

		Contact c2 = new Contact("test2","test2",new java.sql.Date(new Date().getTime()),"fax",2,istreamImage, true);
		Contact c3 = new Contact("test3","test3",new java.sql.Date(new Date().getTime()),"fax",2,istreamImage, false);
		Contact c4 = new Contact("test4","test4",new java.sql.Date(new Date().getTime()),"fax",2,istreamImage, true);

		List<Adresse> adrPourC =  new LinkedList<Adresse>();
		List<Mail> mailsPourC = new LinkedList<Mail>();
		List<Telephone> telsPourC = new LinkedList<Telephone>();

		adrPourC.add(new Adresse("adresse C", 1));
		adrPourC.add(new Adresse("Adresse D", 1));
		mailsPourC.add(new Mail("mail C", 1));
		mailsPourC.add(new Mail("Mail D", 1));
		telsPourC.add(new Telephone("Tel C", 1));
		telsPourC.add(new Telephone("Tel D", 1));
		c.setAdresses(adrPourC);
		c2.setMails(mailsPourC);
		c3.setTelephones(telsPourC);

		service.CreerContact(c);
		service.CreerContact(c2);
		service.CreerContact(c3);
		service.CreerContact(c4);

		for(int i = 0; i < 20 ; i++)
		{
			service.CreerContact(c);
		}
	}
}
//
//		System.out.println(service.TrouverContact(1));
//
//		System.out.println("groupe id = 2 : " + service.TrouverGroupe(2));
//		System.out.println("type id = 2 : " + service.TrouverType(2));
//
//		//		c = service.setPhoto(1, istreamImage2);
//		//		c = service.setPrenomContact(1, "nouveauPrenom");
//		//		c = service.setFavoris(1, false);
//		//		c = service.setFax(1, "nouveauFax");
//		//		c = service.setDDN(1, nouvelleDate);
//		//		c = service.setGroupe(1, g1);
//
//		//		System.out.println("SUPPRESSION : " + service.SupprimerContact(1));
//
//		telsPourC.removeAll(telsPourC);
//		telsPourC.add(new Telephone("nouvelle Adresse", 5));
//		telsPourC.add(new Telephone("nouvelle Adresse", 1));
//
//		System.out.println( " -------------------------------- ");
//		c = service.setTelephones(1, telsPourC);
//
//		service.SupprimerType("nouveauLibType");
//
//		System.out.println("********************");
//		List<Contact> listContacts = service.rechercheContactNom("te");
//		for (Contact contact : listContacts) {
//			System.out.println(contact.getIdContact() + " - " + contact.getNom());
//		}
//
//		System.out.println(service.TrouverTousType());
////		System.out.println(service.setPhoto(1, istreamImage2));
//
//
////		BufferedImage image = ImageIO.read(c.getPhoto());
////
////        JLabel label = new JLabel(new ImageIcon(image));
////        JFrame f = new JFrame();
////        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////        f.getContentPane().add(label);
////        f.pack();
////        f.setLocation(200,200);
////        f.setVisible(true);
//
////		service.setPhoto(1, istreamImage2);
////		System.out.println(service.TrouverContact(1));
////
////		BufferedImage image2 = ImageIO.read(c.getPhoto());
////
////        JLabel label2 = new JLabel(new ImageIcon(image2));
////        JFrame f2 = new JFrame();
////        f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////        f2.getContentPane().add(label2);
////        f2.pack();
////        f2.setLocation(200,200);
////        f2.setVisible(true);
//
//
//
//		//		System.out.println(service.ExporterFavoris());
//
//		//		System.out.println( service.SupprimerContact(1));
//		//		System.out.println( service.SupprimerContact(2));
//		//		System.out.println( service.SupprimerContact(4));
//
//		//		service.FusionnerGroupe(g1, g2, "nouveauGroupe");
//
//		//		System.out.println(service.SupprimerContact(1));
//
//
//		//		Groupe g3 = service.FusionnerGroupe(g1, g2, "fusion");
//		//
//		//		g2 = service.setNomGroupe(g2, "nouveauNom");
//		//		g2 = service.setNomGroupe(g2, "test");
//
//
//		//		Contact seb = new Contact();
//		//		seb.setNom("chen");
//		//		seb.setPrenom("sebastien");
//
//	}
//}
///*	public static void main(String[] args) throws Exception {
//		Database db = new Database("Database.db");
//		db.connexion();
//		DAO dao = new DAO(db);
//// test pour importer une image locale
//		File monImage = new File(".\\adrien.jpg");
//		FileInputStream istreamImage = new FileInputStream(monImage);
//		Groupe g = new Groupe();
//		g.setNom("testGroupe");
//		dao.CreerGroupe(g);
//		Contact c = new Contact("test","test",new java.sql.Date(2000,01,22),"fax", 1,istreamImage, false);
//		dao.CreerContact(c);
//		//test de filtre
//		Contact seb = new Contact();
//		seb.setNom("chen");
//		seb.setPrenom("sebastien");
//		Contact adrien = new Contact();
//		adrien.setNom("bertuzzi");
//		adrien.setPrenom("adrien");
//		Contact patrick = new Contact();
//		patrick.setNom("chen");
//		patrick.setPrenom("prenom");
//		Groupe testGroupe = new Groupe();
//		testGroupe.setListeContacts(new LinkedList<Contact>());
//		testGroupe.getListeContacts().add(seb);
//		testGroupe.getListeContacts().add(adrien);
//		testGroupe.getListeContacts().add(patrick);
//		Groupe asc = testGroupe;
//		ServiceCarnetAdresse s = new ServiceCarnetAdresse();
//		asc = s.trieContactAsc(asc);
//		for(Contact contact : asc.getListeContacts()){
//			System.out.println("----filtre asc----");
//			System.out.println(contact.getNom()+" "+contact.getPrenom());
//		}
//		asc = s.trieContactDesc(asc);
//		for(Contact contact : asc.getListeContacts()){
//			System.out.println("----filtre desc----");
//			System.out.println(contact.getNom()+" "+contact.getPrenom());
//		}
//		// test pour lire une image récupérée directement dans la BD
//				ResultSet rs = db.requete.executeQuery("select photo from contact where idcontact = 1");
//				byte[] imgData = null;
//				if (rs.next()) {
//		            imgData = rs.getBytes("photo");//Here r1.getBytes() extract byte data from resultSet
//		        }
//				System.out.println(imgData);
//				ByteArrayInputStream stream = new ByteArrayInputStream(imgData);
//				BufferedImage image = ImageIO.read(stream);
//		// pour lire l'image dans une JFrame
////		BufferedImage image = ImageIO.read(istreamImage);
//        JLabel label = new JLabel(new ImageIcon(image));
//        JFrame f = new JFrame();
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        f.getContentPane().add(label);
//        f.pack();
//        f.setLocation(200,200);
//        f.setVisible(true);
//	}
//}*/