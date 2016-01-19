package modele;

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
			String s = "INSERT INTO CONTACT(IDCONTACT, NOM, PRENOM, DDN, ADRESSE, TELEPHONE, FAX, MAIL, IDGROUPE, PHOTO, FAVORIS) "
					+ "VALUES(" + contact.getIdContact() + ", '"
					+ contact.getNom() + "', '"
					+ contact.getPrenom() + "',"
					+ contact.getDdn() + ", '"
					+ contact.getAdresse() + "', '"
					+ contact.getTelephone() + "', '"
					+ contact.getFax() + "', '"
					+ contact.getMail() + "',"
					+ contact.getIdGroupe() + ","
					+ contact.getPhoto() + ","
					+ isContactFavoris + ")";
			db.setValeur(s);
			return true;
		}
		catch (Exception e)
		{
			throw new Exception(e.toString());
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
