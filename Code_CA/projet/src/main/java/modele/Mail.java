package modele;

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

	public int getIdMail() {
		return idMail;
	}
	public void setIdMail(int idMail) {
		this.idMail = idMail;
	}
	public String getMail() {
		return Mail;
	}
	public void setMail(String mail) {
		Mail = mail;
	}

}
