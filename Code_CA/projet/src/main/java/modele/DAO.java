package modele;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
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
			return TrouverContact(idContact);
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
		Contact ancienContact;
		PreparedStatement ps;
		if((ancienContact = TrouverContact(idContactAModifier)) == null){
			throw new Exception("Aucun Contact de ce nom n'existe !");
		}
		/*
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
		 */

		try
		{

			if (!ancienContact.getNom().equals(contactSouhaite.getNom()))
			{
				ps = db.connexion.prepareStatement("UPDATE CONTACT SET NOM = ? WHERE IDCONTACT = ?");
				ps.setString(1, contactSouhaite.getNom());
				ps.setInt(2, idContactAModifier);
				ps.execute();
			}

			if (!ancienContact.getPrenom().equals(contactSouhaite.getPrenom()))
			{
				ps = db.connexion.prepareStatement("UPDATE CONTACT SET PRENOM = ? WHERE IDCONTACT = ?");
				ps.setString(1, contactSouhaite.getPrenom());
				ps.setInt(2, idContactAModifier);
				ps.execute();
			}

			if (ancienContact.getFax() != (contactSouhaite.getFax()))
			{
				ps = db.connexion.prepareStatement("UPDATE CONTACT SET FAX = ? WHERE IDCONTACT = ?");
				ps.setString(1, contactSouhaite.getFax());
				ps.setInt(2, idContactAModifier);
				ps.execute();
			}

			if (ancienContact.getFavoris() != contactSouhaite.getFavoris())
			{
				ps = db.connexion.prepareStatement("UPDATE CONTACT SET FAVORIS = ? WHERE IDCONTACT = ?");
				ps.setInt(1, contactSouhaite.getFavoris() ? 1 : 0);
				ps.setInt(2, idContactAModifier);
				ps.execute();
			}

			if (ancienContact.getIdGroupe() != (contactSouhaite.getIdGroupe()))
			{
				ps = db.connexion.prepareStatement("UPDATE CONTACT SET IDGROUPE = ? WHERE IDCONTACT = ?");
				ps.setInt(1, contactSouhaite.getIdGroupe());
				ps.setInt(2, idContactAModifier);
				ps.execute();
			}

			if (ancienContact.getDdn() != (contactSouhaite.getDdn()))
			{
				ps = db.connexion.prepareStatement("UPDATE CONTACT SET DDN = ? WHERE IDCONTACT = ?");
				ps.setDate(1, (Date)contactSouhaite.getDdn());
				ps.setInt(2, idContactAModifier);
				ps.execute();
			}

			if(!isPhotoEqual(ancienContact.getPhoto(), contactSouhaite.getPhoto()))
			{
				ps = db.connexion.prepareStatement("UPDATE CONTACT SET PHOTO = ? WHERE IDCONTACT = ?");
//				System.out.println(ancienContact.getPhoto());
//				System.out.println(contactSouhaite.getPhoto());
				ps.setBinaryStream(1, contactSouhaite.getPhoto(), 10000000);
				ps.setInt(2, idContactAModifier);
				ps.execute();
				System.out.println("UPDATED!!");
			}

			// Pour les adresses d'un contact
			int differenceEntreListesAdresses = contactSouhaite.getAdresses().size() - ancienContact.getAdresses().size();

			if(differenceEntreListesAdresses >= 0)
			{
				for(int i = 0; i < ancienContact.getAdresses().size() ; i++)
				{
					Adresse adresseEnCoursSouhaite = contactSouhaite.getAdresses().get(i);
					Adresse adresseEnCoursAncien = ancienContact.getAdresses().get(i);

					if(!adresseEnCoursSouhaite.getAdresse().equals(adresseEnCoursAncien.getAdresse()))
					{
						ps = db.connexion.prepareStatement("UPDATE ADRESSE SET ADRESSE = ? WHERE IDCONTACT = ? AND IDADRESSE = ?");
						ps.setString(1, adresseEnCoursSouhaite.getAdresse());
						ps.setInt(2, idContactAModifier);
						ps.setInt(3, adresseEnCoursAncien.getIdAdresse());
						ps.execute();
					}
					if(adresseEnCoursSouhaite.getIdType() != adresseEnCoursAncien.getIdType())
					{
						ps = db.connexion.prepareStatement("UPDATE ADRESSE SET IDTYPE = ? WHERE IDCONTACT = ? AND IDADRESSE = ?");
						ps.setInt(1, adresseEnCoursSouhaite.getIdType());
						ps.setInt(2, idContactAModifier);
						ps.setInt(3, adresseEnCoursAncien.getIdAdresse());
						ps.execute();
					}
				}
				for(int i = ancienContact.getAdresses().size() ; i < contactSouhaite.getAdresses().size() ; i++)
				{
					Adresse adresseEnCoursSouhaite = contactSouhaite.getAdresses().get(i);

					ps = db.connexion.prepareStatement("insert into adresse(idcontact, adresse, idtype) values (?, ?, ?)");
					ps.setInt(1, idContactAModifier);
					ps.setString(2, adresseEnCoursSouhaite.getAdresse());
					ps.setInt(3, adresseEnCoursSouhaite.getIdType());
					ps.execute();
				}
			}
			else
			{
				for(int i = 0; i < contactSouhaite.getAdresses().size() ; i++)
				{
					Adresse adresseEnCoursSouhaite = contactSouhaite.getAdresses().get(i);
					Adresse adresseEnCoursAncien = ancienContact.getAdresses().get(i);

					if(!adresseEnCoursSouhaite.getAdresse().equals(adresseEnCoursAncien.getAdresse()))
					{
						ps = db.connexion.prepareStatement("UPDATE ADRESSE SET ADRESSE = ? WHERE IDCONTACT = ? AND IDADRESSE = ?");
						ps.setString(1, adresseEnCoursSouhaite.getAdresse());
						ps.setInt(2, idContactAModifier);
						ps.setInt(3, adresseEnCoursAncien.getIdAdresse());
						ps.execute();
					}
					if(adresseEnCoursSouhaite.getIdType() != adresseEnCoursAncien.getIdType())
					{
						ps = db.connexion.prepareStatement("UPDATE ADRESSE SET IDTYPE = ? WHERE IDCONTACT = ? AND IDADRESSE = ?");
						ps.setInt(1, adresseEnCoursSouhaite.getIdType());
						ps.setInt(2, idContactAModifier);
						ps.setInt(3, adresseEnCoursAncien.getIdAdresse());
						ps.execute();
					}
				}
				for(int i = contactSouhaite.getAdresses().size() ; i < ancienContact.getAdresses().size() ; i++)
				{
					Adresse adresseEnCoursAncien = ancienContact.getAdresses().get(i);

					ps = db.connexion.prepareStatement("delete from adresse where idadresse = ?");
					ps.setInt(1, adresseEnCoursAncien.getIdAdresse());
					ps.execute();
				}
			}


			/*			// pour les adresses d'un contact
			if(contactSouhaite.getAdresses() != null)
			{
				ps = db.connexion.prepareStatement("DELETE FROM ADRESSE WHERE idContact = ?");
				ps.setInt(1, idContactAModifier);
				ps.execute();

				for (Adresse adr : contactSouhaite.getAdresses()) {
					ps = db.connexion.prepareStatement("insert into adresse(idcontact, adresse, idtype) values (?, ?, ?)");
					ps.setInt(1, idContactAModifier);
					ps.setString(2, adr.getAdresse());
					ps.setInt(3, adr.getIdType());
					// on update
					ps.execute();
				}
			}
			 */
			// il faut ensuite comparer en collection
			// et modifier là où il y a des différences
			// voir http://stackoverflow.com/questions/19155283/simple-way-to-compare-2-arraylists

			// pour les mails d'un contact
			int differenceEntreListesMails = contactSouhaite.getMails().size() - ancienContact.getMails().size();

			if(differenceEntreListesMails >= 0)
			{
				for(int i = 0; i < ancienContact.getMails().size() ; i++)
				{
					Mail mailEnCoursSouhaite = contactSouhaite.getMails().get(i);
					Mail mailEnCoursAncien = ancienContact.getMails().get(i);

					if(!mailEnCoursSouhaite.getMail().equals(mailEnCoursAncien.getMail()))
					{
						ps = db.connexion.prepareStatement("UPDATE MAIL SET MAIL = ? WHERE IDCONTACT = ? AND IDMAIL = ?");
						ps.setString(1, mailEnCoursSouhaite.getMail());
						ps.setInt(2, idContactAModifier);
						ps.setInt(3, mailEnCoursAncien.getIdMail());
						ps.execute();
					}
					if(mailEnCoursSouhaite.getIdType() != mailEnCoursAncien.getIdType())
					{
						ps = db.connexion.prepareStatement("UPDATE MAIL SET IDTYPE = ? WHERE IDCONTACT = ? AND IDMAIL = ?");
						ps.setInt(1, mailEnCoursSouhaite.getIdType());
						ps.setInt(2, idContactAModifier);
						ps.setInt(3, mailEnCoursAncien.getIdMail());
						ps.execute();
					}
				}
				for(int i = ancienContact.getMails().size() ; i < contactSouhaite.getMails().size() ; i++)
				{
					Mail mailEnCoursSouhaite = contactSouhaite.getMails().get(i);

					ps = db.connexion.prepareStatement("insert into mail(idcontact, mail, idtype) values (?, ?, ?)");
					ps.setInt(1, idContactAModifier);
					ps.setString(2, mailEnCoursSouhaite.getMail());
					ps.setInt(3, mailEnCoursSouhaite.getIdType());
					ps.execute();
				}
			}
			else
			{
				for(int i = 0; i < contactSouhaite.getMails().size() ; i++)
				{
					Mail mailEnCoursSouhaite = contactSouhaite.getMails().get(i);
					Mail mailEnCoursAncien = ancienContact.getMails().get(i);

					if(!mailEnCoursSouhaite.getMail().equals(mailEnCoursAncien.getMail()))
					{
						ps = db.connexion.prepareStatement("UPDATE MAIL SET MAIL = ? WHERE IDCONTACT = ? AND IDMAIL = ?");
						ps.setString(1, mailEnCoursSouhaite.getMail());
						ps.setInt(2, idContactAModifier);
						ps.setInt(3, mailEnCoursAncien.getIdMail());
						ps.execute();
					}
					if(mailEnCoursSouhaite.getIdType() != mailEnCoursAncien.getIdType())
					{
						ps = db.connexion.prepareStatement("UPDATE MAIL SET IDTYPE = ? WHERE IDCONTACT = ? AND IDMAIL= ?");
						ps.setInt(1, mailEnCoursSouhaite.getIdType());
						ps.setInt(2, idContactAModifier);
						ps.setInt(3, mailEnCoursAncien.getIdMail());
						ps.execute();
					}
				}
				for(int i = contactSouhaite.getMails().size() ; i < ancienContact.getMails().size() ; i++)
				{
					Mail mailEnCoursAncien = ancienContact.getMails().get(i);

					ps = db.connexion.prepareStatement("delete from mail where idmail = ?");
					ps.setInt(1, mailEnCoursAncien.getIdMail());
					ps.execute();
				}
			}

			/*
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
			 */
			// pour les telephones d'un contact
			int differenceEntreListesTelephones = contactSouhaite.getTelephones().size() - ancienContact.getTelephones().size();

			if(differenceEntreListesTelephones >= 0)
			{
				for(int i = 0; i < ancienContact.getTelephones().size() ; i++)
				{
					Telephone telEnCoursSouhaite = contactSouhaite.getTelephones().get(i);
					Telephone telEnCoursAncien = ancienContact.getTelephones().get(i);

					if(!telEnCoursSouhaite.getTelephone().equals(telEnCoursAncien.getTelephone()))
					{
						ps = db.connexion.prepareStatement("UPDATE TELEPHONE SET TELEPHONE = ? WHERE IDCONTACT = ? AND IDTELEPHONE = ?");
						ps.setString(1, telEnCoursSouhaite.getTelephone());
						ps.setInt(2, idContactAModifier);
						ps.setInt(3, telEnCoursAncien.getIdTelephone());
						ps.execute();
					}
					if(telEnCoursSouhaite.getIdType() != telEnCoursAncien.getIdType())
					{
						ps = db.connexion.prepareStatement("UPDATE TELEPHONE SET IDTYPE = ? WHERE IDCONTACT = ? AND IDTELEPHONE = ?");
						ps.setInt(1, telEnCoursSouhaite.getIdType());
						ps.setInt(2, idContactAModifier);
						ps.setInt(3, telEnCoursAncien.getIdTelephone());
						ps.execute();
					}
				}
				for(int i = ancienContact.getTelephones().size() ; i < contactSouhaite.getTelephones().size() ; i++)
				{
					Telephone telEnCoursSouhaite = contactSouhaite.getTelephones().get(i);

					ps = db.connexion.prepareStatement("insert into telephone (idcontact, telephone, idtype) values (?, ?, ?)");
					ps.setInt(1, idContactAModifier);
					ps.setString(2, telEnCoursSouhaite.getTelephone());
					ps.setInt(3, telEnCoursSouhaite.getIdType());
					ps.execute();
				}
			}
			else
			{
				for(int i = 0; i < contactSouhaite.getTelephones().size() ; i++)
				{
					Telephone telEnCoursSouhaite = contactSouhaite.getTelephones().get(i);
					Telephone telEnCoursAncien = ancienContact.getTelephones().get(i);

					if(!telEnCoursSouhaite.getTelephone().equals(telEnCoursAncien.getTelephone()))
					{
						ps = db.connexion.prepareStatement("UPDATE TELEPHONE SET TELEPHONE = ? WHERE IDCONTACT = ? AND IDTELEPHONE = ?");
						ps.setString(1, telEnCoursSouhaite.getTelephone());
						ps.setInt(2, idContactAModifier);
						ps.setInt(3, telEnCoursAncien.getIdTelephone());
						ps.execute();
					}
					if(telEnCoursSouhaite.getIdType() != telEnCoursAncien.getIdType())
					{
						ps = db.connexion.prepareStatement("UPDATE TELEPHONE SET IDTYPE = ? WHERE IDCONTACT = ? AND IDTELEPHONE= ?");
						ps.setInt(1, telEnCoursSouhaite.getIdType());
						ps.setInt(2, idContactAModifier);
						ps.setInt(3, telEnCoursAncien.getIdTelephone());
						ps.execute();
					}
				}
				for(int i = contactSouhaite.getTelephones().size() ; i < ancienContact.getTelephones().size() ; i++)
				{
					Telephone telEnCoursAncien = ancienContact.getTelephones().get(i);

					ps = db.connexion.prepareStatement("delete from telephone where idtelephone = ?");
					ps.setInt(1, telEnCoursAncien.getIdTelephone());
					ps.execute();
				}
			}

			/*
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
			 */
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

	public List<Contact> trouverTousContactsGroupe(String nomGroupe) throws SQLException {
		List<Contact> listeTousLesContacts = new LinkedList<Contact>();
		PreparedStatement psContact = db.connexion.prepareStatement("SELECT * FROM CONTACT, GROUPE WHERE CONTACT.IDGROUPE = GROUPE.IDGROUPE AND GROUPE.NOM = ?");
		psContact.setString(1, nomGroupe);
		ResultSet rsContact = psContact.executeQuery();
		try {
			while(rsContact.next()){
				listeTousLesContacts.add(TrouverContact(Integer.parseInt(rsContact.getString("idContact"))));
			}
			Collections.sort(listeTousLesContacts);
		} catch (SQLException e) {
			throw new SQLException(e.toString());
		}
		return listeTousLesContacts;
	}

	public List<Contact> rechercherContactNom(String nom) throws SQLException {
		List<Contact> listeContact = new LinkedList<Contact>();
		PreparedStatement psContact = db.connexion.prepareStatement("SELECT * FROM CONTACT WHERE nom LIKE ?");
		psContact.setString(1, nom + '%');
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

	public Type ModifierType(String nomTypeAModifier, Type typeSouhaite) throws Exception {
		Type typeAModifier;
		if((typeAModifier = TrouverType(nomTypeAModifier)) == null){
			throw new Exception("Aucun type avec ce libelle n'existe !");
		}
		try
		{
			PreparedStatement ps = db.connexion.prepareStatement("UPDATE TYPE "
					+ "SET LIBELLETYPE = ?"
					+ "WHERE idtype = ?");
			ps.setString(1, typeSouhaite.getLibelleType());
			ps.setInt(2, typeAModifier.getIdType());
			ps.execute();
			return TrouverType(typeSouhaite.getLibelleType());
		}
		catch(Exception e)
		{
			throw new Exception(e.toString());
		}
	}

	private boolean isPhotoEqual(InputStream i1, InputStream i2) throws IOException {
		byte[] buf1 = new byte[64 *1024];
		byte[] buf2 = new byte[64 *1024];
		try {
			DataInputStream d2 = new DataInputStream(i2);
			int len;
			while ((len = i1.read(buf1)) > 0) {
				d2.readFully(buf2,0,len);
				for(int i=0;i<len;i++)
					if(buf1[i] != buf2[i]){
						return false;
					}
			}
			return d2.read() < 0;
		} catch(EOFException ioe) {
			return false;
		} finally {
			i1.close();
			i2.close();
		}
	}

}