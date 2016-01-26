package modele;

import lombok.Data;

@Data
public class Adresse {

	private int idAdresse;

	private String adresse;

	public Adresse(int idAdresse, String adresse) {
		super();
		this.idAdresse = idAdresse;
		this.adresse = adresse;
	}

}
