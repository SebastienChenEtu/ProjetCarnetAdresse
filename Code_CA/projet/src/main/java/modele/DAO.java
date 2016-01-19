package modele;

import java.io.ByteArrayInputStream;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DAO{ //VBO Le DAO n'a pas à se trouver dans le package modele

	Database db;

	public DAO(Database db){
		this.db = db;
	}

	public Contact TrouverContact(int id) throws SQLException{
		Contact contact = new Contact();
		List<Adresse> adresses = new LinkedList<Adresse>();

		PreparedStatement psSimpleContact = db.connexion.prepareStatement("SELECT * FROM CONTACT WHERE idcontact = ? ");
		psSimpleContact.setInt(1, id);

		PreparedStatement psAdressesContact = db.connexion.prepareStatement("SELECT * FROM ADRESSE WHERE idcontact = ? ");
		psAdressesContact.setInt(1, id);

		ResultSet rsContactSimple = psSimpleContact.executeQuery();
		ResultSet rsAdressesContact = psAdressesContact.executeQuery();

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
					adresses.add(new Adresse(rsAdressesContact.getInt(1),rsAdressesContact.getString("adresse")));
				}
				contact.setAdresses(adresses);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block //VBO ToRemove
			e.printStackTrace(); //VBO C'est très moche.
		}
		return contact;
	}

	// SQL ne renvoie pas d'erreur quand je crée un contact pour un groupe qui n'existe pas ?
	public boolean CreerContact(Contact contact) throws Exception {
		try {
			int isContactFavoris = 0; // en SQLite boolean false|true devient int 0|1
			if(contact.getFavoris())
			{
				isContactFavoris = 1;
			}

			PreparedStatement ps = db.connexion.prepareStatement(" insert into contact(idcontact,idgroupe, nom,favoris, prenom, ddn, photo, fax) "
					+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setInt(1, contact.getIdContact());
			ps.setInt(2, contact.getIdGroupe());
			ps.setString(3, contact.getNom());
			ps.setInt(4, isContactFavoris);
			ps.setString(5, contact.getPrenom());
			ps.setDate(6, (Date) contact.getDdn());
			ps.setBinaryStream(7, contact.getPhoto(), 1000000); // ne pas mettre en dur le taille maximale (1000000)
			ps.setString(8, contact.getFax());
			// on crée le contact
			ps.execute();

			if(TrouverContact(contact.getIdContact()) != null){
				// pour les adresses d'un contact
				if(contact.getAdresses() != null)
				{
					int idAdresseTempo = 1; // à modifier .. il faut que mettre en auto_increment idadresse
					for (Adresse adr : contact.getAdresses()) {
						ps = db.connexion.prepareStatement("insert into adresse(idadresse, idcontact, adresse) values (?, ?, ?)");
						ps.setInt(1, idAdresseTempo);
						ps.setInt(2, contact.getIdContact());
						ps.setString(3, adr.getAdresse());
						// on crée les adresses pour le contact
						ps.execute();
					}
				}
				// pour les mails d'un contact
				// A TERMINER ICI
				if(contact.getMails() != null)
				{
					int idMailTempo = 1; // à modifier .. il faut que mettre en auto_increment idadresse
					for (Adresse adr : contact.getAdresses()) {
						ps = db.connexion.prepareStatement("insert into mail(idmail, idcontact, mail) values (?, ?, ?)");
						ps.setInt(1, idMailTempo);
						ps.setInt(2, contact.getIdContact());
						ps.setString(3, adr.getAdresse());
						// on crée les adresses pour le contact
						ps.execute();
					}
				}

			}
			return true;
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


	public boolean ModifierContact(Contact contact) {
		// TODO Auto-generated method stub
		return false;
	}

	// TODO
	public Groupe TrouverGroupe(String nom) throws SQLException{
		Groupe groupe = new Groupe();
		PreparedStatement ps = db.connexion.prepareStatement("SELECT * FROM GROUPE WHERE nom = ?");
		ps.setString(1, nom);
		ResultSet rs = ps.executeQuery();
		try {
			if(!rs.next()){
				return null;
			}
			else {
				groupe.setIdGroupe(Integer.parseInt(rs.getString("idGroupe")));
				groupe.setNom(nom);
			}
		} catch (SQLException e) {
			throw new SQLException(e.toString());
		}
		return groupe;
	}

	public boolean ModifierGroupe(Groupe groupe) {
		// TODO
		return false;
	}

	public boolean SupprimerGroupe(Groupe groupe) throws Exception {
		if(TrouverGroupe(groupe.getNom()) == null){
			throw new Exception("Aucun groupe de ce nom n'existe !");
		}
		try {
			PreparedStatement ps = db.connexion.prepareStatement("DELETE FROM GROUPE WHERE idgroupe = ?");
			ps.setInt(1, groupe.getIdGroupe());
			ps.execute();
			return TrouverGroupe(groupe.getNom()) == null;
		}
		catch(Exception e)
		{
			throw new Exception(e.toString());
		}
	}

	public boolean CreerGroupe(Groupe groupe) throws Exception {
		if(TrouverGroupe(groupe.getNom()) != null)
		{
			throw new Exception("Un groupe de ce nom existe déjà !");
		}
		try {
			PreparedStatement ps = db.connexion.prepareStatement("INSERT INTO GROUPE(IDGROUPE, NOM) VALUES(?, ?)");
			ps.setInt(1, groupe.getIdGroupe());
			ps.setString(2, groupe.getNom());
			ps.execute();
			return true;
		}
		catch (Exception e)
		{
			throw new Exception(e.toString());
		}
	}
}
