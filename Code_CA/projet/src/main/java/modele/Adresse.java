package modele;

import lombok.Data;

@Data
public class Adresse {

	private int idAdresse;
	private String adresse;
	private int idType;

	public Adresse(int idAdresse, String adresse, int idType) {
		super();
		this.idAdresse = idAdresse;
		this.adresse = adresse;
		this.idType = idType;
	}

	public Adresse(String adresse, int idType) {
		this.adresse = adresse;
		this.idType = idType;
	}

}
