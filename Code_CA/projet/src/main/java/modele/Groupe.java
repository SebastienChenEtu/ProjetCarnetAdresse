package modele;
import java.util.List;

public class Groupe {
	public int getIdGroupe() {
		return idGroupe;
	}
	public void setIdGroupe(int idGroupe) {
		this.idGroupe = idGroupe;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Groupe(int idGroupe, String nom) {
		super();
		this.idGroupe = idGroupe;
		this.nom = nom;
	}
	public Groupe() {

	}
	// il faut le gÃ©nÃ©rer automatiquement
	private int idGroupe;
	private String nom;
}
