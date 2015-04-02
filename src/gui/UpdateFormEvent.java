package gui;

import java.util.EventObject;

public class UpdateFormEvent extends EventObject{
	
	private int id;
	private String forNavn;
	private String etterNavn;
	private String eMail;
	private String tlf;
	private String kontCat;
	private String adresse;
	private String postNr;
	
	public UpdateFormEvent(Object source) {
		super(source);
	}
	
	UpdateFormEvent(Object source,int id,String forNavn,String etterNavn, String eMail,String tlf,String adresse,String postNr,String kontCat) {
		super(source);
		
		this.id = id;
		this.forNavn = forNavn;
		this.etterNavn = etterNavn;
		this.eMail = eMail;
		this.tlf = tlf;
		this.adresse = adresse;
		this.postNr = postNr;
		this.kontCat = kontCat;
		
	}

	public int getId() {
		return id;
	}

	public String getForNavn() {
		return forNavn;
	}

	public String getEtterNavn() {
		return etterNavn;
	}

	public String geteMail() {
		return eMail;
	}

	public String getTlf() {
		return tlf;
	}

	public String getKontCat() {
		return kontCat;
	}

	public String getAdresse() {
		return adresse;
	}

	public String getPostNr() {
		return postNr;
	}
	
}
