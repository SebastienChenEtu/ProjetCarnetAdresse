package modele;

public class Telephone {

	private int idTelephone;
	private String telephone;


	public Telephone()
	{

	}

//VBO Pensez à remettre en forme votre code.

	public Telephone(int idTelephone, String telephone) {
		super();
		this.idTelephone = idTelephone;
		this.telephone = telephone;
	}



	public int getIdTelephone() {
		return idTelephone;
	}
	public void setIdTelephone(int idTelephone) {
		this.idTelephone = idTelephone;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}



}
