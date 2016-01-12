package modele;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO{

	Database db;

	public DAO(Database db){
		this.db = db;
	}

	public Contact TrouverContact(int id){
		Contact contact = new Contact();
		ResultSet rs = db.getResultatRequete("SELECT * FROM CONTACT WHERE idcontact = " + id);
		try {
			if(!rs.next()){
				return null;
			}
			else {
				contact.setIdContact(id);
				contact.setNom(rs.getString("nom"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contact;
	}

	// SQL ne renvoie pas d'erreur quand je crée un contact pour un groupe qui n'existe pas ?
	// Gérer le non null pour le blob (photo)
	public boolean CreerContact(Contact contact) throws Exception {
		try {
			int isContactFavoris = 0;
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
			ps.setBinaryStream(7, contact.getPhoto(), 1000000);
			// ps.setBlob(7, contact.getPhoto());
			ps.setString(8, contact.getFax());

			ps.execute();
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
		db.setValeur("DELETE FROM CONTACT WHERE idcontact = " + contact.getIdContact());
		return TrouverContact(contact.getIdContact()) == null;
	}

	public boolean ModifierContact(Contact contact) {
		// TODO Auto-generated method stub
		return false;
	}

	// TODO
	public Groupe TrouverGroupe(String nom){
		Groupe groupe = new Groupe();
		ResultSet rs = db.getResultatRequete("SELECT * FROM GROUPE WHERE nom = '" + nom + "'");
		try {
			if(!rs.next()){
				return null;
			}
			else {
				groupe.setIdGroupe(Integer.parseInt(rs.getString("idGroupe")));
				groupe.setNom(nom);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		db.setValeur("DELETE FROM GROUPE WHERE idgroupe = " + groupe.getIdGroupe());
		return TrouverGroupe(groupe.getNom()) == null;
	}

	public boolean CreerGroupe(Groupe groupe) throws Exception {
		if(TrouverGroupe(groupe.getNom()) != null)
		{
			throw new Exception("Un groupe de ce nom existe déjà !");
		}
		try {
			String s = "INSERT INTO GROUPE(IDGROUPE, NOM) VALUES(" + groupe.getIdGroupe() + ", '" + groupe.getNom() + "')";
			db.setValeur(s);
			return true;
		}
		catch (Exception e)
		{
			throw new Exception(e.toString());
		}
	}
}
