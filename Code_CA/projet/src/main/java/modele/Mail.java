package modele;

import lombok.Data;

@Data
public class Mail {

	public Mail(int idMail, String mail) {
		super();
		this.idMail = idMail;
		Mail = mail;
	}
	private int idMail;
	private String Mail;

	public Mail()
	{

	}

}
