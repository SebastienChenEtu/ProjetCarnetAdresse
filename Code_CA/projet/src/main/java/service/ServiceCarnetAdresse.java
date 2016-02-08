package service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.springframework.stereotype.Service;

import modele.Adresse;
import modele.Contact;
import modele.DAO;
import modele.Database;
import modele.Groupe;
import modele.Mail;
import modele.Telephone;
import modele.Type;

@Service
public class ServiceCarnetAdresse {
	Database db;
	DAO dao;
	// Ne serait-ce pas plus optimal de faire des requêtes SQL simples pour chaque update qu'on souhaite
	// plutôt qu'instancier un objet contact à chaque fois et utiliser une méthode "modifier" du DAO
	// (même si c'est moins "facile" ?) - Ou alors optimer la méthode "modifier" en soi directement
	// en faisant un update uniquement sur ce qui est différent entre contactAModifier et contactSouhaite

	// Je pense qu'il faudrait optimiser la méthode "Modifier" vu comme c'est parti.
	// Sinon le couple DAO/service a peu d'intérêt ?..


	public ServiceCarnetAdresse()
	{
		this.db = new Database("Database.db");
		this.db.connexion();
		this.dao = new DAO(db);
	}

	public Contact CreerContact(Contact contact) throws Exception
	{
		return this.dao.CreerContact(contact);
	}

	public Contact TrouverContact(int idContact) throws SQLException
	{
		return this.dao.TrouverContact(idContact);
	}

	public Groupe CreerGroupe(Groupe groupe) throws Exception
	{
		return this.dao.CreerGroupe(groupe);
	}
	
	public Groupe TrouverGroupe(String nom) throws SQLException{
		return this.dao.TrouverGroupe(nom);
	}
	
	public Groupe ModifierGroupe(String nom,Groupe groupe) throws Exception
	{
		return this.dao.ModifierGroupe(nom, groupe);
	}
	
	public Contact ModifierContact(int idContact,Contact contact) throws Exception
	{
		return this.dao.ModifierContact(idContact, contact);
	}
	
	public Groupe setNomGroupe(String nomAncienGroupe, String nom) throws Exception
	{
		Groupe nouveauGroupe = this.dao.TrouverGroupe(nomAncienGroupe);
		nouveauGroupe.setNom(nom);
		return this.dao.ModifierGroupe(nomAncienGroupe, nouveauGroupe);
	}

	public Type setLibelleType(String nomAncienType, String nom) throws Exception
	{
		Type nouveauType = this.dao.TrouverType(nomAncienType);
		nouveauType.setLibelleType(nom);
		return this.dao.ModifierType(nomAncienType, nouveauType);
	}

	public Contact setNomContact(int idContactAModifier, String nom) throws Exception
	{
		Contact nouveauContact = TrouverContact(idContactAModifier);
		nouveauContact.setNom(nom);
		return this.dao.ModifierContact(idContactAModifier, nouveauContact);
	}

	public Contact setPrenomContact(int idContactAModifier, String prenom) throws Exception
	{
		Contact nouveauContact = TrouverContact(idContactAModifier);
		nouveauContact.setPrenom(prenom);
		return this.dao.ModifierContact(idContactAModifier, nouveauContact);
	}

	public Contact setFax(int idContactAModifier, String fax) throws Exception
	{
		Contact nouveauContact = TrouverContact(idContactAModifier);
		nouveauContact.setFax(fax);
		return this.dao.ModifierContact(idContactAModifier, nouveauContact);
	}

	public Contact setFavoris (int idContactAModifier, boolean isFavoris) throws Exception
	{
		Contact nouveauContact = TrouverContact(idContactAModifier);
		nouveauContact.setFavoris(isFavoris);
		return this.dao.ModifierContact(idContactAModifier, nouveauContact);
	}

	public Contact setDDN (int idContactAModifier, Date ddn) throws Exception
	{
		Contact nouveauContact = TrouverContact(idContactAModifier);
		nouveauContact.setDdn(ddn);
		return this.dao.ModifierContact(idContactAModifier, nouveauContact);
	}

	// TODO
	public Contact setPhoto(int idContactAModifier, InputStream photo) throws Exception
	{
		Contact nouveauContact = TrouverContact(idContactAModifier);
		//nouveauContact.setPhoto(photo);
		//return this.dao.ModifierContact(idContactAModifier, nouveauContact);

		ResultSet rs = db.requete.executeQuery("select photo from contact where idcontact = 1");
		byte[] imgData = null;
		if (rs.next()) {
            imgData = rs.getBytes("photo");//Here r1.getBytes() extract byte data from resultSet
        }
		ByteArrayInputStream stream = new ByteArrayInputStream(imgData);
		System.out.println(stream);
		BufferedImage image2 = ImageIO.read(stream);

        JLabel label2 = new JLabel(new ImageIcon(image2));
        JFrame f2 = new JFrame();
        f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f2.getContentPane().add(label2);
        f2.pack();
        f2.setLocation(200,200);
        f2.setVisible(true);


		//nouveauContact.setPhoto(stream);
		return this.dao.ModifierContact(idContactAModifier, nouveauContact);
	}

	public Contact setGroupe(int idContactAModifier, Groupe groupe) throws Exception
	{
		Contact nouveauContact = TrouverContact(idContactAModifier);
		nouveauContact.setIdGroupe(groupe.getIdGroupe());
		return this.dao.ModifierContact(idContactAModifier, nouveauContact);
	}

	public Groupe trieContactAsc(Groupe groupe){
		List<Contact> trieAsc = new LinkedList<Contact>();
		trieAsc = groupe.getListeContacts();
		Collections.sort(trieAsc);
		groupe.setListeContacts(trieAsc);
		return groupe;
	}

	public Groupe trieContactDesc(Groupe groupe){
		Groupe groupeDesc = trieContactAsc(groupe);
		List<Contact> trieDesc = new LinkedList<Contact>();
		for(int i = groupeDesc.getListeContacts().size()-1; i>=0 ; i--){
			trieDesc.add(groupe.getListeContacts().get(i));
		}
		groupe.setListeContacts(trieDesc);
		return groupe;
	}

	public List<Contact> trouverToutContact() throws SQLException{
		return this.dao.trouverToutContact();
	}

	public List<Groupe> trouverToutGroupe() throws SQLException{
		return this.dao.trouverToutGroupe();
	}

	public List<Contact> trouverToutFavoris() throws SQLException{
		return this.dao.trouverToutFavoris();
	}

	public List<Contact> rechercheContactNom(String nom) throws SQLException{
		return this.dao.rechercherContactNom(nom);

	}
	public boolean SupprimerContact(int idContact) throws Exception
	{
		return this.dao.SupprimerContact(idContact);
	}

	public boolean SupprimerGroupe(String nomGroupe) throws Exception
	{
		return this.dao.SupprimerGroupe(nomGroupe);
	}

	// TODO
	// il faut récuperer la liste de contacte des groupes
	// il faut mettre à jour l'id des contacts sur le nouveau groupe crée
	public Groupe FusionnerGroupe(Groupe g1, Groupe g2, String nomGroupe) throws Exception
	{
		List<Contact> listeContacts = new LinkedList<Contact>();
		g1 = this.dao.TrouverGroupe(g1.getNom());
		g2 = this.dao.TrouverGroupe(g2.getNom());
		listeContacts.addAll(g1.getListeContacts());
		listeContacts.addAll(g2.getListeContacts());
		this.dao.SupprimerGroupe(g1.getNom());
		this.dao.SupprimerGroupe(g2.getNom());
		Groupe groupe = new Groupe();
		groupe.setListeContacts(listeContacts);
		groupe.setNom(nomGroupe);
		return this.dao.CreerGroupe(groupe);
	}

	public Contact setAdresses(int idContactAModifier,List<Adresse> listeAdresses) throws Exception{
		Contact nouveauContact = TrouverContact(idContactAModifier);
		nouveauContact.setAdresses(listeAdresses);
		return this.dao.ModifierContact(idContactAModifier, nouveauContact);
	}

	public Contact setMails(int idContactAModifier, List<Mail> listeMails) throws Exception{
		Contact nouveauContact = TrouverContact(idContactAModifier);
		nouveauContact.setMails(listeMails);
		return this.dao.ModifierContact(idContactAModifier, nouveauContact);
	}

	public Contact setTelephones(int idContactAModifier, List<Telephone> listeTelephones) throws Exception{
		Contact nouveauContact = TrouverContact(idContactAModifier);
		nouveauContact.setTelephones(listeTelephones);
		return this.dao.ModifierContact(idContactAModifier, nouveauContact);
	}

	public Type CreerType(Type type) throws Exception
	{
		return this.dao.CreerType(type);
	}

	public Type TrouverType(String libelleType) throws SQLException
	{
		return this.dao.TrouverType(libelleType);
	}

	public boolean SupprimerType(String libelleType) throws Exception
	{
		return this.dao.SupprimerType(libelleType);
	}

	public boolean ExporterFavoris() throws Exception
	{
		List<Contact> contactFavoris = trouverToutFavoris();
		String sqlInsertions = "create temp table _variables(last_rowid integer);" + '\n';

		try {

			for (Contact contact : contactFavoris) {

				String sqlTpContactSimple = "insert into contact(idgroupe, nom,favoris, prenom, ddn, fax) "
						+ "VALUES(";
				//sqlTpContactSimple = sqlTpContactSimple + contact.getIdContact() + ", ";
				//sqlTpContactSimple = sqlTpContactSimple + contact.getIdGroupe() + " , '";
				sqlTpContactSimple = sqlTpContactSimple + "0 , '";
				sqlTpContactSimple = sqlTpContactSimple + contact.getNom() + "' ,";
				sqlTpContactSimple = sqlTpContactSimple + "1, '";
				sqlTpContactSimple = sqlTpContactSimple + contact.getPrenom() + "',";
				sqlTpContactSimple = sqlTpContactSimple + contact.getDdn()+ " ,'";
				sqlTpContactSimple = sqlTpContactSimple + contact.getFax()+ "' );";

				sqlInsertions = sqlInsertions + '\n' +sqlTpContactSimple + '\n';

				sqlInsertions = sqlInsertions + "insert into _variables values ((select last_insert_rowid() from contact));" + '\n';

				for (Adresse adr : contact.getAdresses()) {
					String sqlTpContactAdresses = "insert into adresse (idcontact, adresse, idtype) values (";
					//sqlTpContactAdresses = sqlTpContactAdresses + adr.getIdAdresse() + ", ";
					// sqlTpContactAdresses = sqlTpContactAdresses + contact.getIdContact() + ", '";
					sqlTpContactAdresses = sqlTpContactAdresses + "(select max(last_rowid) from _variables), '";
					sqlTpContactAdresses = sqlTpContactAdresses + adr.getAdresse() + "', ";
					sqlTpContactAdresses = sqlTpContactAdresses + adr.getIdType() + ");";

					sqlInsertions = sqlInsertions + '\n' + sqlTpContactAdresses;
				}

				for (Mail mail : contact.getMails()) {
					String sqlTpContactMails= "insert into mail(idcontact, mail, idtype) values (";
					//sqlTpContactMails = sqlTpContactMails + mail.getIdMail() + ", ";
					//sqlTpContactMails = sqlTpContactMails + contact.getIdContact() + ", '";
					sqlTpContactMails = sqlTpContactMails+ "(select max(last_rowid) from _variables) , '";
					sqlTpContactMails = sqlTpContactMails + mail.getMail() + "', ";
					sqlTpContactMails = sqlTpContactMails + mail.getIdType() + ");";

					sqlInsertions = sqlInsertions + '\n' + sqlTpContactMails;
				}

				for (Telephone tel : contact.getTelephones()) {
					String sqlTpContactTelephones = "insert into telephone(idcontact, telephone, idtype) values (";
					//sqlTpContactTelephones = sqlTpContactTelephones + tel.getIdTelephone() + ", ";
					//sqlTpContactTelephones = sqlTpContactTelephones + contact.getIdContact() + ", '";
					sqlTpContactTelephones = sqlTpContactTelephones + "(select max(last_rowid) from _variables), '";
					sqlTpContactTelephones = sqlTpContactTelephones + tel.getTelephone() + "', ";
					sqlTpContactTelephones = sqlTpContactTelephones + tel.getIdType() + ");";

					sqlInsertions = sqlInsertions + '\n' + sqlTpContactTelephones + '\n';
				}
			}

			sqlInsertions = sqlInsertions + '\n' + "drop table _variables;";
			FileWriter exportFile = new FileWriter("favoris.sql", true);
			exportFile.write(sqlInsertions);
			exportFile.close();
			return true;
		}
		catch (Exception e)
		{
			throw new Exception(e.toString());
		}
	}

	public boolean ExporterBase() throws Exception
	{
		try {
			System.getProperty("user.dir");
			Runtime.getRuntime().exec("cmd.exe /c sqlite3.exe Database.db .dump > Recovery.sql");
			return true;
		}
		catch (Exception e)
		{
			throw new Exception(e.toString());
		}
	}

	// TODO
	public boolean ImporterBase() throws Exception
	{
		try {
			System.getProperty("user.dir");
			Runtime.getRuntime().exec("cmd.exe /c sqlite3.exe NewDatabase.db < Recovery.sql");
			return true;
		}
		catch (Exception e)
		{
			throw new Exception(e.toString());
		}
	}

	public boolean ImporterFavoris() throws Exception
	{
		try
		{
			System.getProperty("user.dir");
			Runtime.getRuntime().exec("cmd.exe /c  sqlite3.exe Database.db < import_favoris.txt");
			return true;
		}
		catch (Exception e)
		{
			throw new Exception(e.toString());
		}
	}

	public boolean ImporterContactsGroupe(String nomGroupe) throws Exception
	{
		try
		{
			System.getProperty("user.dir");
			String nomFichierTxt = "import_" + nomGroupe + ".txt";
			FileWriter exportFile = new FileWriter(nomFichierTxt, true);
			exportFile.write(".read contacts_" + nomGroupe + ".sql");
			exportFile.close();
			Runtime.getRuntime().exec("cmd.exe /c  sqlite3.exe Database.db < " + nomFichierTxt);
			return true;
		}
		catch (Exception e)
		{
			throw new Exception(e.toString());
		}
	}


	public boolean ExporterContactsGroupe(String nomGroupe) throws Exception
	{
		{
			List<Contact> contactGroupe = this.dao.trouverTousContactsGroupe(nomGroupe);
			String sqlInsertions = "create temp table _variables(last_rowid_contact integer, last_rowid_groupe integer);";
			sqlInsertions = sqlInsertions + '\n' + "insert into groupe (nom) values('" + nomGroupe + "');" + '\n';
			sqlInsertions = sqlInsertions + "insert into _variables (last_rowid_groupe) values ((select last_insert_rowid() from groupe));" + '\n';

			try {

				for (Contact contact : contactGroupe) {

					String sqlTpContactSimple = "insert into contact(idgroupe, nom,favoris, prenom, ddn, fax) "
							+ "VALUES(";
					//sqlTpContactSimple = sqlTpContactSimple + contact.getIdContact() + ", ";
					//sqlTpContactSimple = sqlTpContactSimple + contact.getIdGroupe() + " , '";
					sqlTpContactSimple = sqlTpContactSimple + "(select max(last_rowid_groupe) from _variables) , '";
					sqlTpContactSimple = sqlTpContactSimple + contact.getNom() + "' ,";
					sqlTpContactSimple = sqlTpContactSimple + "1, '";
					sqlTpContactSimple = sqlTpContactSimple + contact.getPrenom() + "',";
					sqlTpContactSimple = sqlTpContactSimple + contact.getDdn()+ " ,'";
					sqlTpContactSimple = sqlTpContactSimple + contact.getFax()+ "' );";

					sqlInsertions = sqlInsertions + '\n' +sqlTpContactSimple + '\n';

					sqlInsertions = sqlInsertions + "insert into _variables (last_rowid_contact) values ((select last_insert_rowid() from contact));" + '\n';

					for (Adresse adr : contact.getAdresses()) {
						String sqlTpContactAdresses = "insert into adresse (idcontact, adresse, idtype) values (";
						//sqlTpContactAdresses = sqlTpContactAdresses + adr.getIdAdresse() + ", ";
						// sqlTpContactAdresses = sqlTpContactAdresses + contact.getIdContact() + ", '";
						sqlTpContactAdresses = sqlTpContactAdresses + "(select max(last_rowid_contact) from _variables), '";
						sqlTpContactAdresses = sqlTpContactAdresses + adr.getAdresse() + "', ";
						sqlTpContactAdresses = sqlTpContactAdresses + adr.getIdType() + ");";

						sqlInsertions = sqlInsertions + '\n' + sqlTpContactAdresses;
					}

					for (Mail mail : contact.getMails()) {
						String sqlTpContactMails= "insert into mail(idcontact, mail, idtype) values (";
						//sqlTpContactMails = sqlTpContactMails + mail.getIdMail() + ", ";
						//sqlTpContactMails = sqlTpContactMails + contact.getIdContact() + ", '";
						sqlTpContactMails = sqlTpContactMails+ "(select max(last_rowid_contact) from _variables) , '";
						sqlTpContactMails = sqlTpContactMails + mail.getMail() + "', ";
						sqlTpContactMails = sqlTpContactMails + mail.getIdType() + ");";

						sqlInsertions = sqlInsertions + '\n' + sqlTpContactMails;
					}

					for (Telephone tel : contact.getTelephones()) {
						String sqlTpContactTelephones = "insert into telephone(idcontact, telephone, idtype) values (";
						//sqlTpContactTelephones = sqlTpContactTelephones + tel.getIdTelephone() + ", ";
						//sqlTpContactTelephones = sqlTpContactTelephones + contact.getIdContact() + ", '";
						sqlTpContactTelephones = sqlTpContactTelephones + "(select max(last_rowid_contact) from _variables), '";
						sqlTpContactTelephones = sqlTpContactTelephones + tel.getTelephone() + "', ";
						sqlTpContactTelephones = sqlTpContactTelephones + tel.getIdType() + ");";

						sqlInsertions = sqlInsertions + '\n' + sqlTpContactTelephones + '\n';
					}
				}

				sqlInsertions = sqlInsertions + '\n' + "drop table _variables;";
				FileWriter exportFile = new FileWriter("contacts_" + nomGroupe + ".sql", true);
				exportFile.write(sqlInsertions);
				exportFile.close();
				return true;
			}
			catch (Exception e)
			{
				throw new Exception(e.toString());
			}
		}
	}
	
	
	public List<Contact> trouverTousContactsGroupe(String nomGroupe) throws NumberFormatException, Exception {
		return this.dao.trouverTousContactsGroupe(nomGroupe);
	}
}
