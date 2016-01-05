package modele;

public class Adresse {
	
	private int idAdresse;
	
	private String adresse;

	public Adresse(int idAdresse, String adresse) {
		super();
		this.idAdresse = idAdresse;
		this.adresse = adresse;
	}

	public int getIdAdresse() {
		return idAdresse;
	}

	public void setIdAdresse(int idAdresse) {
		this.idAdresse = idAdresse;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

}
