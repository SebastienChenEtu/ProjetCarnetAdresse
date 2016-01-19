package modele;

import lombok.Data;

@Data
public class Groupe {


	// il faut le gÃ©nÃ©rer automatiquement
	private String nom;
	private int idGroupe;

	public Groupe(int idGroupe, String nom) {
		super();
		this.idGroupe = idGroupe;
		this.nom = nom;
	}

	public Groupe(){

	}
}
