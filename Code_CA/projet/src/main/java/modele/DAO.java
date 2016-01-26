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

				int curseurAdresse = 1;
				int curseurMail = 1;
				int curseurTelephone = 1;

				while(rsAdressesContact.next()){
					adresses.add(new Adresse(rsAdressesContact.getInt(curseurAdresse),rsAdressesContact.getString("adresse")));
					curseurAdresse++;
				}

				while(rsMailsContact.next())
				{
					mails.add(new Mail(rsMailsContact.getInt(curseurMail), rsMailsContact.getString("mail")));
					curseurMail++;
				}

				while(rsTelephonesContact.next())
				{
					telephones.add(new Telephone(rsTelephonesContact.getInt(curseurTelephone), rsTelephonesContact.getString("telephone")));
					curseurTelephone++;
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

			if(TrouverContact(contact.getIdContact()) != null){
				// pour les adresses d'un contact
				if(contact.getAdresses() != null)
				{
					int idAdresseTempo = 0; // par défaut le 1er idAdresse est à 0++, sinon max(idadresse) ne renvoie rien

					ps = db.connexion.prepareStatement("select max(idadresse) from adresse");
					rs = ps.executeQuery();
					if(rs.next())
					{
						idAdresseTempo = rs.getInt(1);
					}
					for (Adresse adr : contact.getAdresses()) {
						ps = db.connexion.prepareStatement("insert into adresse(idadresse, idcontact, adresse) values (?, ?, ?)");
						ps.setInt(1, idAdresseTempo++);
						ps.setInt(2, contact.getIdContact());
						ps.setString(3, adr.getAdresse());
						// on crée les adresses pour le contact
						ps.execute();
					}
				}
				// pour les mails d'un contact
				if(contact.getMails() != null)
				{
					int idMailTempo = 0; // par défaut 0++ comme idAdresseTempo

					ps = db.connexion.prepareStatement("select max(idmail) from mail");
					rs = ps.executeQuery();
					if(rs.next())
					{
						idMailTempo = rs.getInt(1);
					}

					for (Mail mail : contact.getMails()) {
						ps = db.connexion.prepareStatement("insert into mail(idmail, idcontact, mail) values (?, ?, ?)");
						ps.setInt(1, idMailTempo++);
						ps.setInt(2, contact.getIdContact());
						ps.setString(3, mail.getMail());
						// on crée les mails pour le contact
						ps.execute();
					}
				}

				// pour les téléphones d'un contact
				if(contact.getTelephones() != null)
				{
					int idTelTempo = 0; // par défaut 0++ comme idAdresseTempo

					ps = db.connexion.prepareStatement("select max(idtelephone) from telephone");
					rs = ps.executeQuery();
					if(rs.next())
					{
						idTelTempo = rs.getInt(1);
					}

					for (Telephone tel : contact.getTelephones()) {
						ps = db.connexion.prepareStatement("insert into telephone(idtelephone, idcontact, telephone) values (?, ?, ?)");
						ps.setInt(1, idTelTempo++);
						ps.setInt(2, contact.getIdContact());
						ps.setString(3, tel.getTelephone());
						// on crée les telephones pour le contact
						ps.execute();
					}
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

	public boolean SupprimerContact(Contact contact) throws Exception {
		if(TrouverContact(contact.getIdContact()) == null){
			throw new Exception("Aucun Contact de ce nom n'existe !");
		}
		try {
			PreparedStatement ps = db.connexion.prepareStatement("DELETE FROM CONTACT WHERE idcontact = ?");
			ps.setInt(1, contact.getIdContact());
			ps.execute();
			return TrouverContact(contact.getIdContact()) == null;
		}
		catch(Exception e)
		{
			throw new Exception(e.toString());
		}
	}

	// TODO
	// ce serait bien de renvoyer le nouveau contact crée
	// ce n'est pas grave niveau optimisation ?
	public Contact ModifierContact(Contact contactAModifier, Contact contactSouhaite) throws Exception {
		if(TrouverContact(contactAModifier.getIdContact()) == null){
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
			return TrouverContact(contactAModifier.getIdContact());
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
			ps.setString(2, groupe.getNom());
			ps.execute();
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
			if(!rsContact.next()){
				return null;
			}
			else {
				while(rsContact.next()){
					listeContacts.add(TrouverContact(Integer.parseInt(rsContact.getString("idContact"))));
				}
			}
		} catch (SQLException e) {
			throw new SQLException(e.toString());
		}
		Collections.sort(listeContacts);
		return listeContacts;
	}

	public List<Groupe> trouverToutGroupe() throws SQLException {
		List<Groupe> listeGroupe = new LinkedList<Groupe>();
		PreparedStatement psGroupe = db.connexion.prepareStatement("SELECT * FROM GROUPE");

//		PreparedStatement psContactsGroupe = db.connexion.prepareStatement("SELECT * FROM CONTACT WHERE idgroupe = ?");
		ResultSet rsGroupe = psGroupe.executeQuery();
		try {
			if(!rsGroupe.next()){
				return null;
			}
			else {
//				int curseurContact = 1;

				while(rsGroupe.next()){
					Groupe groupeRes = new Groupe();
//					List<Contact> listeContacts = new LinkedList<Contact>();
					groupeRes.setIdGroupe(Integer.parseInt(rsGroupe.getString("idGroupe")));
					groupeRes.setNom(rsGroupe.getString("nom"));
					TrouverGroupe(groupeRes.getNom());
//					psContactsGroupe.setInt(1, groupeRes.getIdGroupe());
//					ResultSet rsContactsGroupe = psContactsGroupe.executeQuery();
//					listeContacts.add(TrouverContact(rsContactsGroupe.getInt(curseurContact)));
//					groupeRes.setListeContacts(listeContacts);
					service.trieContactAsc(groupeRes);
					listeGroupe.add(groupeRes);
//					curseurContact++;
				}
			}
		} catch (SQLException e) {
			throw new SQLException(e.toString());
		}
		return listeGroupe;
	}

	public List<Contact> trouverToutFavoris() throws SQLException {
		List<Contact> listeContacts = new LinkedList<Contact>();
		PreparedStatement psContact = db.connexion.prepareStatement("SELECT * FROM CONTACT");
		ResultSet rsContact = psContact.executeQuery();
		try {
			if(!rsContact.next()){
				return null;
			}
			else {
				while(rsContact.next()){
					listeContacts.add(TrouverContact(Integer.parseInt(rsContact.getString("idContact"))));
				}
				for(Contact contact : listeContacts){
					if(contact.getFavoris() == false){
						listeContacts.remove(contact);
					}
				}
				Collections.sort(listeContacts);
			}
		} catch (SQLException e) {
			throw new SQLException(e.toString());
		}
		return listeContacts;
	}

	public List<Contact> rechercherContactNom(String nom) throws SQLException {
		List<Contact> listeContact = new LinkedList<Contact>();
		PreparedStatement psContact = db.connexion.prepareStatement("SELECT * FROM CONTACT WHERE nom LIKE ?% ");
		psContact.setString(1,nom);
		ResultSet rsContact = psContact.executeQuery();
		try {
			if(!rsContact.next()){
				return null;
			}
			else {
				while(rsContact.next()){
					listeContact.add(TrouverContact(Integer.parseInt(rsContact.getString("idContact"))));
				}
			}
		} catch (SQLException e) {
			throw new SQLException(e.toString());
		}
		return listeContact;
	}




}