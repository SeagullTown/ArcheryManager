package model;

import java.io.Serializable;

/*
 * Class for storing payment information for 1 Medlem
 */

public class Kontingent implements Serializable{
	
	private Medlem eierKont;
	private KontingentCategory kontCat;
	private boolean betalt;

	/**
	 * 
	 */
	private static final long serialVersionUID = 2097426409958886947L;

	public Kontingent(KontingentCategory kontCat,
			boolean betalt) {
		
		this.kontCat = kontCat;
		this.betalt = betalt;
	}
	
	
	/*
	 * Getters and setters
	 */
	public Medlem getEierKont() {
		return eierKont;
	}

	public void setEierKont(Medlem eierKont) {
		this.eierKont = eierKont;
	}

	public KontingentCategory getKontCat() {
		return kontCat;
	}

	public void setKontCat(KontingentCategory kontCat) {
		this.kontCat = kontCat;
	}

	public boolean isBetalt() {
		return betalt;
	}

	public void setBetalt(boolean betalt) {
		this.betalt = betalt;
	}
	
	

}
