package modele;

import java.util.LinkedList;
import java.util.List;

import lombok.Data;

@Data
public class Groupe {


	// il faut le gÃ©nÃ©rer automatiquement
	private String nom;
	private int idGroupe;
	private List<Contact> listeContacts;

	public Groupe(){

	}



	public Groupe(int idGroupe, String nom, List<Contact> listeContacts) {
		super();
		this.nom = nom;
		this.idGroupe = idGroupe;
		this.listeContacts = listeContacts;
	}
}
