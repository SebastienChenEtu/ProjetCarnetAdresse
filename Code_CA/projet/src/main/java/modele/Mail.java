package modele;

import lombok.Data;

@Data
public class Mail {

	public Mail(int idMail, String mail, int idType) {
		super();
		this.idMail = idMail;
		this.Mail = mail;
		this.idType = idType;
	}
	private int idMail;
	private String Mail;
	private int idType;

	public Mail()
	{

	}

	public Mail(String mail, int idType) {
		this.Mail = mail;
		this.idType = idType;
	}
}
