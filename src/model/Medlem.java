package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Medlem implements Serializable{
	
	/*
	 * 
	 */
	private static final long serialVersionUID = -4600197054009278763L;
	
	private int ID;
	private String forNavn;
	private String etterNavn;
	private String eMail;
	private String tlf;
	private String adresse;
	private String postNr;
	private Kontingent kont;
	private Sex sex;
	private LocalDate dateOfBirth;
	
	public Medlem(String forNavn,String etterNavn,LocalDate date,String tlf,String eMail,String adresse,String postNr,Kontingent kont,Sex sex) {
		
		this.forNavn = forNavn;
		this.etterNavn = etterNavn;
		this.dateOfBirth = date;
		this.eMail = eMail;
		this.tlf = tlf;
		this.adresse = adresse;
		this.postNr = postNr;
		this.kont = kont;
		this.sex = sex;
	
		
		
	}
	
	public Medlem(int ID,String forNavn,String etterNavn,LocalDate date,String tlf,String eMail,String adresse,String postNr,Kontingent kont,Sex sex) {
		
		
		this.ID = ID;
		this.forNavn = forNavn;
		this.etterNavn = etterNavn;
		this.dateOfBirth = date;
		this.tlf = tlf;
		this.eMail = eMail;
		this.adresse = adresse;
		this.postNr = postNr;
		this.kont = kont;
		this.sex = sex;
	
		
		
	}



	/*
	 * Getters and setters for Medlem.
	 * with the exception of setting ID as that is chosen by the MySQL database.
	 *  
	 */
	
	
	public int getID() {
		return ID;
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

	public Kontingent getKont() {
		return kont;
	}
	
	public void setKont(Kontingent kont) {
		this.kont = kont;
	}
	public KontingentCategory getKontCat() {
		return kont.getKontCat();
	}

	public int getId() {	
		return ID;
	}

	public boolean getBetalt() {
		
		return kont.isBetalt();
	}
	
	public Sex getSex() {
		return sex;
	}
	
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	
	
	
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void report() {
		System.out.println("ID: " + ID);
		System.out.println("forNavn: " + forNavn );
		System.out.println("etterNavn: " + etterNavn);
		System.out.println("fdato: " + dateOfBirth);
		System.out.println("tlf: " + tlf);
		System.out.println("mail: " + eMail);
		System.out.println("adr: " + adresse);
		System.out.println("post: " + postNr);
		System.out.println("kjønn: " + sex + "\n");
	}
}
