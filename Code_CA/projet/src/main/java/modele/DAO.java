package modele;

import java.io.ByteArrayInputStream;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import service.ServiceCarnetAdresse;

public class DAO{

	Database db;
	ServiceCarnetAdresse service;

	public DAO(Database db){
		this.db = db;
	}

	public Contact TrouverContact(int id) throws SQLException{
		Contact contact = new Contact();
		List<Adresse> adresses = new LinkedList<Adresse>();
		List<Mail> mails = new LinkedList<Mail>();
		List<Telephone> telephones = new LinkedList<Telephone>();

		PreparedStatement psSimpleContact = db.connexion.prepareStatement("SELECT * FROM CONTACT WHERE idcontact = ? ");
		psSimpleContact.setInt(1, id);

		PreparedStatement psAdressesContact = db.connexion.prepareStatement("SELECT * FROM ADRESSE WHERE idcontact = ? ");
		psAdressesContact.setInt(1, id);

		PreparedStatement psMailsContacts = db.connexion.prepareStatement("SELECT * FROM MAIL WHERE idcontact = ? ");
		psMailsContacts.setInt(1, id);

		PreparedStatement psTelephonesContacts = db.connexion.prepareStatement("SELECT * FROM TELEPHONE WHERE idcontact = ? ");
		psTelephonesContacts.setInt(1, id);

		ResultSet rsContactSimple = psSimpleContact.executeQuery();
		ResultSet rsAdressesContact = psAdressesContact.executeQuery();
		ResultSet rsMailsContact = psMailsContacts.executeQuery();
		ResultSet rsTelephonesContact = psTelephonesContacts.executeQuery();


		try {
			if(!rsContactSimple.next()){
				return null;
			}
			else {
				contact.setIdContact(id);
				contact.setNom(rsContactSimple.getString("nom"));
				contact.setPrenom(rsContactSimple.getString("prenom"));
				contact.setDdn(rsContactSimple.getDate("ddn"));
				contact.setFavoris(rsContactSimple.getBoolean("favoris"));
				contact.setFax(rsContactSimple.getString("fax"));
				contact.setIdGroupe(rsContactSimple.getInt("idgroupe"));

				byte[] imgData = rsContactSimple.getBytes("photo");
				ByteArrayInputStream stream = new ByteArrayInputStream(imgData);
				contact.setPhoto(stream);

				while(rsAdressesContact.next()){
					adresses.add(new Adresse(rsAdressesContact.getInt("idadresse"),rsAdressesContact.getString("adresse"), rsAdressesContact.getInt("idtype")));
				}

				while(rsMailsContact.next())
				{
					mails.add(new Mail(rsMailsContact.getInt("idMail"), rsMailsContact.getString("mail"), rsMailsContact.getInt("idtype")));
				}

				while(rsTelephonesContact.next())
				{
					telephones.add(new Telephone(rsTelephonesContact.getInt("idTelephone"), rsTelephonesContact.getString("telephone"), rsTelephonesContact.getInt("idtype")));
				}

				contact.setAdresses(adresses);
				contact.setMails(mails);
				contact.setTelephones(telephones);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contact;
	}

	// SQL ne renvoie pas d'erreur quand je crée un contact pour un groupe qui n'existe pas ?
	public Contact CreerContact(Contact contact) throws Exception {
		try {
			int isContactFavoris = 0; // en SQLite boolean false|true devient int 0|1
			if(contact.getFavoris())
			{
				isContactFavoris = 1;
			}

			PreparedStatement ps = db.connexion.prepareStatement(" insert into contact(idgroupe, nom,favoris, prenom, ddn, photo, fax) "
					+ "VALUES(?, ?, ?, ?, ?, ?, ?)");
			ResultSet rs;

			ps.setInt(1, contact.getIdGroupe());
			ps.setString(2, contact.getNom());
			ps.setInt(3, isContactFavoris);
			ps.setString(4, contact.getPrenom());
			ps.setDate(5, (Date) contact.getDdn());
			ps.setBinaryStream(6, contact.getPhoto(), 1000000); // ne pas mettre en dur le taille maximale (1000000)
			ps.setString(7, contact.getFax());
			// on crée le contact
			ps.execute();

			ps = db.connexion.prepareStatement("select last_insert_rowid()");
			rs = ps.executeQuery();
			int idContact = 0;

			if(rs.next())
			{
				idContact = rs.getInt(1);
			}

			// pour les adresses d'un contact
			if(contact.getAdresses() != null)
			{
				for (Adresse adr : contact.getAdresses()) {
					ps = db.connexion.prepareStatement("insert into adresse(idcontact, adresse, idtype) values (?, ?, ?)");
					ps.setInt(1, idContact);
					ps.setString(2, adr.getAdresse());
					ps.setInt(3, adr.getIdType());
					// on crée les adresses pour le contact
					ps.execute();
				}
			}
			// pour les mails d'un contact
			if(contact.getMails() != null)
			{
				for (Mail mail : contact.getMails()) {
					ps = db.connexion.prepareStatement("insert into mail(idcontact, mail, idtype) values (?, ?, ?)");
					ps.setInt(1, idContact);
					ps.setString(2, mail.getMail());
					ps.setInt(3, mail.getIdType());
					// on crée les mails pour le contact
					ps.execute();
				}
			}

			// pour les téléphones d'un contact
			if(contact.getTelephones() != null)
			{
				for (Telephone tel : contact.getTelephones()) {
					ps = db.connexion.prepareStatement("insert into telephone(idcontact, telephone, idtype) values (?, ?, ?)");
					ps.setInt(1, idContact);
					ps.setString(2, tel.getTelephone());
					ps.setInt(3, tel.getIdType());
					// on crée les telephones pour le contact
					ps.execute();
				}
			}
			return TrouverContact(contact.getIdContact());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception();
		}
	}

	public boolean SupprimerContact(int idContact) throws Exception {
		if(TrouverContact(idContact) == null){
			throw new Exception("Aucun Contact de ce nom n'existe !");
		}
		try {
			PreparedStatement ps = db.connexion.prepareStatement("DELETE FROM CONTACT WHERE idcontact = ?");
			ps.setInt(1, idContact);
			ps.execute();
			return TrouverContact(idContact) == null;
		}
		catch(Exception e)
		{
			throw new Exception(e.toString());
		}
	}

	// TODO
	// ce serait bien de renvoyer le nouveau contact crée
	// ce n'est pas grave niveau optimisation ?
	public Contact ModifierContact(int idContactAModifier, Contact contactSouhaite) throws Exception {
		if(TrouverContact(idContactAModifier) == null){
			throw new Exception("Aucun Contact de ce nom n'existe !");
		}

		int isContactFavoris = 0; // en SQLite boolean false|true devient int 0|1
		if(contactSouhaite.getFavoris())
		{
			isContactFavoris = 1;
		}

		try {
			PreparedStatement ps = db.connexion.prepareStatement("UPDATE CONTACT "
					+ "SET NOM = ?,"
					+ "PRENOM = ?,"
					+ "FAX = ?,"
					+ "FAVORIS = ?,"
					+ "DDN = ?,"
					+ "IDGROUPE = ?"
					+ "WHERE idcontact = ?");
			ps.setString(1, contactSouhaite.getNom());
			ps.setString(2, contactSouhaite.getPrenom());
			ps.setString(3, contactSouhaite.getFax());
			ps.setInt(4, isContactFavoris);
			ps.setDate(5, (Date) contactSouhaite.getDdn());
			ps.setInt(6, contactSouhaite.getIdGroupe());
			ps.setInt(7, contactSouhaite.getIdContact());
			ps.execute();

			// pour les adresses d'un contact
			if(contactSouhaite.getAdresses() != null)
			{
				ps = db.connexion.prepareStatement("DELETE FROM ADRESSE WHERE idContact = ?");
				ps.setInt(1, idContactAModifier);
				ps.execute();

				for (Adresse adr : contactSouhaite.getAdresses()) {
					/*ps = db.connexion.prepareStatement("UPDATE ADRESSE SET "
							+ "ADRESSE = ?"
							+ "WHERE IDCONTACT = ?");
					ps.setString(1, adr.getAdresse());
					ps.setInt(2, idContactAModifier);
					 */
					ps = db.connexion.prepareStatement("insert into adresse(idcontact, adresse, idtype) values (?, ?, ?)");
					ps.setInt(1, idContactAModifier);
					ps.setString(2, adr.getAdresse());
					ps.setInt(3, adr.getIdType());
					// on update
					ps.execute();
				}
			}
			// pour les mails d'un contact
			if(contactSouhaite.getMails() != null)
			{
				ps = db.connexion.prepareStatement("DELETE FROM MAIL WHERE idContact = ?");
				ps.setInt(1, idContactAModifier);
				ps.execute();

				for (Mail mail : contactSouhaite.getMails()) {
					ps = db.connexion.prepareStatement("insert into MAIL(idcontact, MAIL, idtype) values (?, ?, ?)");
					ps.setInt(1, idContactAModifier);
					ps.setString(2, mail.getMail());
					ps.setInt(3, mail.getIdType());

					// on update
					ps.execute();
				}
			}

			// pour les telephones d'un contact
			if(contactSouhaite.getTelephones() != null)
			{
				ps = db.connexion.prepareStatement("DELETE FROM TELEPHONE WHERE idContact = ?");
				ps.setInt(1, idContactAModifier);
				ps.execute();

				for (Telephone telephone: contactSouhaite.getTelephones()) {
					ps = db.connexion.prepareStatement("insert into TELEPHONE(idcontact, TELEPHONE, idtype) values (?, ?, ?)");
					ps.setInt(1, idContactAModifier);
					ps.setString(2, telephone.getTelephone());
					ps.setInt(3, telephone.getIdType());

					// on update
					ps.execute();
				}
			}
			return TrouverContact(idContactAModifier);
		}
		catch (Exception e)
		{
			throw new Exception(e.toString());
		}
	}

	//
	public Groupe TrouverGroupe(String nomGroupe) throws SQLException{
		Groupe groupeRes = new Groupe();
		List<Contact> listeContacts = new LinkedList<Contact>();

		PreparedStatement psSimpleGroupe = db.connexion.prepareStatement("SELECT * FROM GROUPE WHERE nom = ?");
		psSimpleGroupe.setString(1, nomGroupe);

		PreparedStatement psContactsGroupe = db.connexion.prepareStatement
				("select * from groupe, contact where contact.idgroupe = groupe.idgroupe and  groupe.nom = ?");
		psContactsGroupe.setString(1, nomGroupe);

		ResultSet rsContactsGroupe = psContactsGroupe.executeQuery();
		ResultSet rsSimpleGroupe = psSimpleGroupe.executeQuery();
		try {
			if(!rsSimpleGroupe.next()){
				return null;
			}
			else {
				groupeRes.setIdGroupe(Integer.parseInt(rsSimpleGroupe.getString("idGroupe")));
				groupeRes.setNom(nomGroupe);

				while(rsContactsGroupe.next()){
					listeContacts.add(TrouverContact(rsContactsGroupe.getInt("idContact")));
				}
				groupeRes.setListeContacts(listeContacts);
			}
		} catch (SQLException e) {
			throw new SQLException(e.toString());
		}
		return groupeRes;
	}


	public Groupe ModifierGroupe(String nomGroupeAModifier, Groupe groupeSouhaite) throws Exception {
		Groupe groupeAModifier;
		if((groupeAModifier = TrouverGroupe(nomGroupeAModifier)) == null){
			throw new Exception("Aucun groupe de ce nom n'existe !");
		}
		try
		{
			PreparedStatement ps = db.connexion.prepareStatement("UPDATE GROUPE "
					+ "SET NOM = ?"
					+ "WHERE idgroupe = ?");
			ps.setString(1, groupeSouhaite.getNom());
			ps.setInt(2, groupeAModifier.getIdGroupe());
			ps.execute();
			return TrouverGroupe(groupeSouhaite.getNom());
		}
		catch(Exception e)
		{
			throw new Exception(e.toString());
		}
	}

	public boolean SupprimerGroupe(String nomGroupe) throws Exception {
		Groupe groupeASupprimer;
		if((groupeASupprimer = TrouverGroupe(nomGroupe)) == null){
			throw new Exception("Aucun groupe de ce nom n'existe !");
		}
		try {
			PreparedStatement ps = db.connexion.prepareStatement("DELETE FROM GROUPE WHERE idgroupe = ?");
			ps.setInt(1, groupeASupprimer.getIdGroupe());
			ps.execute();
			return TrouverGroupe(nomGroupe) == null;
		}
		catch(Exception e)
		{
			throw new Exception(e.toString());
		}
	}

	public Groupe CreerGroupe(Groupe groupe) throws Exception {
		if(TrouverGroupe(groupe.getNom()) != null)
		{
			throw new Exception("Un groupe de ce nom existe déjà !");
		}
		PreparedStatement ps;
		try {
			ps = db.connexion.prepareStatement("INSERT INTO GROUPE(NOM) VALUES(?)");
			ps.setString(1, groupe.getNom());
			ps.execute();
			if(groupe.getListeContacts() != null)
			{
				ps = db.connexion.prepareStatement("select last_insert_rowid()");
				ResultSet rs = ps.executeQuery();
				int idGroupe = 0;

				if(rs.next())
				{
					idGroupe = rs.getInt(1);
				}

				for (Contact contact : groupe.getListeContacts()) {
					contact.setIdGroupe(idGroupe);
					ModifierContact(contact.getIdContact(), contact);
				}
			}
			return TrouverGroupe(groupe.getNom());
		}
		catch (Exception e)
		{
			throw new Exception(e.toString());
		}
	}

	public List<Contact> trouverToutContact() throws SQLException {

		List<Contact> listeContacts = new LinkedList<Contact>();
		PreparedStatement psContact = db.connexion.prepareStatement("SELECT * FROM CONTACT");
		ResultSet rsContact = psContact.executeQuery();
		try {
			while(rsContact.next()){
				listeContacts.add(TrouverContact(Integer.parseInt(rsContact.getString("idContact"))));
			}
		} catch (SQLException e) {
			throw new SQLException(e.toString());
		}
		Collections.sort(listeContacts);
		return listeContacts;
	}

	public List<Groupe> trouverToutGroupe() throws SQLException {
		List<Groupe> listeGroupe = new LinkedList<Groupe>();
		PreparedStatement psGroupe = db.connexion.prepareStatement("SELECT * FROM GROUPE ORDER BY NOM");

		//		PreparedStatement psContactsGroupe = db.connexion.prepareStatement("SELECT * FROM CONTACT WHERE idgroupe = ?");
		ResultSet rsGroupe = psGroupe.executeQuery();
		try {
			while(rsGroupe.next()){
				Groupe groupeRes = new Groupe();
				//					List<Contact> listeContacts = new LinkedList<Contact>();
				groupeRes.setIdGroupe(Integer.parseInt(rsGroupe.getString("idGroupe")));
				groupeRes.setNom(rsGroupe.getString("nom"));
				groupeRes = TrouverGroupe(groupeRes.getNom());
				//					psContactsGroupe.setInt(1, groupeRes.getIdGroupe());
				//					ResultSet rsContactsGroupe = psContactsGroupe.executeQuery();
				//					listeContacts.add(TrouverContact(rsContactsGroupe.getInt(curseurContact)));
				//					groupeRes.setListeContacts(listeContacts);
				listeGroupe.add(groupeRes);
				//					curseurContact++;
			}
		} catch (SQLException e) {
			throw new SQLException(e.toString());
		}
		return listeGroupe;
	}

	public List<Contact> trouverToutFavoris() throws SQLException {
		List<Contact> listeTousLesContacts = new LinkedList<Contact>();
		List<Contact> listeTousLesFavoris = new LinkedList<Contact>();
		PreparedStatement psContact = db.connexion.prepareStatement("SELECT * FROM CONTACT");
		ResultSet rsContact = psContact.executeQuery();
		try {
			while(rsContact.next()){
				listeTousLesContacts.add(TrouverContact(Integer.parseInt(rsContact.getString("idContact"))));
			}
			for (Contact contact : listeTousLesContacts) {
				if(contact.getFavoris()){
					listeTousLesFavoris.add(contact);
				}
			}
			Collections.sort(listeTousLesContacts);
		} catch (SQLException e) {
			throw new SQLException(e.toString());
		}
		return listeTousLesFavoris;
	}

	public List<Contact> rechercherContactNom(String nom) throws SQLException {
		List<Contact> listeContact = new LinkedList<Contact>();
		PreparedStatement psContact = db.connexion.prepareStatement("SELECT * FROM CONTACT WHERE nom LIKE ?%");
		psContact.setString(1,nom);
		ResultSet rsContact = psContact.executeQuery();
		try {
			while(rsContact.next()){
				listeContact.add(TrouverContact(Integer.parseInt(rsContact.getString("idContact"))));
			}
		} catch (SQLException e) {
			throw new SQLException(e.toString());
		}
		return listeContact;
	}

	public Type TrouverType(String libelleType) throws SQLException
	{
		Type typeRes = new Type();

		PreparedStatement psType = db.connexion.prepareStatement("SELECT * FROM TYPE WHERE libelletype = ?");
		psType.setString(1, libelleType);

		ResultSet rsType = psType.executeQuery();
		try {
			if(!rsType.next()){
				return null;
			}
			else {
				typeRes.setIdType(Integer.parseInt(rsType.getString("idType")));
				typeRes.setLibelleType(libelleType);
			}
		} catch (SQLException e) {
			throw new SQLException(e.toString());
		}
		return typeRes;
	}

	public Type CreerType(Type type) throws Exception {
		if(TrouverType(type.getLibelleType()) != null)
		{
			throw new Exception("Un type avec ce libellé existe déjà !");
		}
		PreparedStatement ps;
		try {
			ps = db.connexion.prepareStatement("INSERT INTO TYPE(LIBELLETYPE) VALUES(?)");
			ps.setString(1, type.getLibelleType());
			ps.execute();
			return TrouverType(type.getLibelleType());
		}
		catch (Exception e)
		{
			throw new Exception(e.toString());
		}
	}

	public boolean SupprimerType(String libelleType) throws Exception {
		Type typeASupprimer;
		if((typeASupprimer = TrouverType(libelleType)) == null){
			throw new Exception("Aucun type avec ce libelle n'existe !");
		}
		try {
			PreparedStatement ps = db.connexion.prepareStatement("DELETE FROM TYPE WHERE IDTYPE = ?");
			ps.setInt(1, typeASupprimer.getIdType());
			ps.execute();
			return TrouverType(libelleType) == null;
		}
		catch(Exception e)
		{
			throw new Exception(e.toString());
		}
	}


}