package modele;

import lombok.Data;

@Data
public class Groupe {
	public Groupe(int idGroupe, String nom) {
		super();
		this.idGroupe = idGroupe;
		this.nom = nom;
	}
	
	public Groupe(){
		
	}
	// il faut le gÃ©nÃ©rer automatiquement
	private int idGroupe;
	private String nom;
}
