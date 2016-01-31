package modele;

import lombok.Data;

@Data
public class Telephone {

	private int idTelephone;
	private String telephone;
	private int idType;


	public Telephone()
	{

	}


	public Telephone(int idTelephone, String telephone, int idType) {
		super();
		this.idTelephone = idTelephone;
		this.telephone = telephone;
		this.idType = idType;
	}



	public Telephone(String telephone, int idType) {
		this.telephone = telephone;
		this.idType = idType;
	}


}
