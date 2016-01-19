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
}


