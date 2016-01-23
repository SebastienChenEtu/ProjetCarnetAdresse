package modele;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Contact implements Comparable<Contact>{
	// il faut le gÃ©nÃ©rer automatiquement
	private int idContact;
	private String nom;
	private String prenom;
	private Date ddn;
	private List<Adresse> adresses;
	private List<Telephone> telephones;
	private String fax;
	private List<Mail> mails;
	private int idGroupe;
	private InputStream photo;
	private Boolean favoris;

	public Contact()
	{

	}

	public Contact(int idContact, String nom, String prenom, Date ddn, String fax, int idGroupe, InputStream photo,
			Boolean favoris) {
		super();
		this.idContact = idContact;
		this.nom = nom;
		this.prenom = prenom;
		this.ddn = ddn;
		this.fax = fax;
		this.idGroupe = idGroupe;
		this.photo = photo;
		this.favoris = favoris;
	}

	@Override
	public int compareTo(Contact o) {
		Contact c = (Contact)o;
		if(nom.toUpperCase().equals(c.nom.toUpperCase()))
	    {
	      return prenom.toUpperCase().compareTo(c.prenom.toUpperCase());
	    }
	   return nom.toUpperCase().compareTo(c.nom.toUpperCase());
	}
}


