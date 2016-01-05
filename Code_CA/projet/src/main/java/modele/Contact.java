package modele;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;

import lombok.Data;

@Data
public class Contact {
	// il faut le gÃ©nÃ©rer automatiquement
	private int idContact;
	private String nom;
	private String prenom;
	private Date ddn;
	private String adresse;
	private String telephone;
	private String fax;
	private String mail;
	private int idGroupe;
	private Blob photo;
	private Boolean favoris;

	public Contact(int idContact, String nom, String prenom, Date ddn, String adresse, String telephone, String fax,
			String mail, int idGroupe, Blob photo, Boolean favoris) {
		super();
		this.idContact = idContact;
		this.nom = nom;
		this.prenom = prenom;
		this.ddn = ddn;
		this.adresse = adresse;
		this.telephone = telephone;
		this.fax = fax;
		this.mail = mail;
		this.idGroupe = idGroupe;
		this.photo = photo;
		this.favoris = favoris;
	}
	public Contact()
	{

	}
}


