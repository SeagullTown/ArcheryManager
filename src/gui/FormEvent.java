package gui;

import java.util.EventObject;
import java.time.LocalDate;

public class FormEvent extends EventObject {
	
	private String forNavn;
	private String etterNavn;
	private LocalDate fDate;
	private String eMail;
	private String tlf;
	private String kontCat;
	private String adresse;
	private String postNr;
	private String sex;
	
	public FormEvent(Object source) {
		super(source);
		
	}
	
	FormEvent(Object source,String forNavn,String etterNavn, LocalDate fDate, String eMail,String tlf,String adresse,String postNr,String kontCat,String sex) {
		super(source);
		
		this.forNavn = forNavn;
		this.etterNavn = etterNavn;
		this.fDate = fDate;
		this.eMail = eMail;
		this.tlf = tlf;
		this.adresse = adresse;
		this.postNr = postNr;
		this.kontCat = kontCat;
		this.sex = sex;
		
		
	}

	public String getForNavn() {
		return forNavn;
	}

	public void setForNavn(String forNavn) {
		this.forNavn = forNavn;
	}

	public String getEtterNavn() {
		return etterNavn;
	}

	public void setEtterNavn(String etterNavn) {
		this.etterNavn = etterNavn;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getTlf() {
		return tlf;
	}

	public void setTlf(String tlf) {
		this.tlf = tlf;
	}

	public String getKontCat() {
		return kontCat;
	}

	public void setKontCat(String kontCat) {
		this.kontCat = kontCat;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getPostNr() {
		return postNr;
	}

	public void setPostNr(String postNr) {
		this.postNr = postNr;
	}

	public String getSex() {
		return sex;
	}

	public LocalDate getfDate() {
		return fDate;
	}

	public void setfDate(LocalDate fDate) {
		this.fDate = fDate;
	}
	
	
}
	