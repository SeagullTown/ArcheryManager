package controller;

import gui.FormEvent;
import gui.UpdateFormEvent;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import model.Database;
import model.Kontingent;
import model.KontingentCategory;
import model.Medlem;
import model.Sex;

public class Controller {

	private Database db = new Database();

	public List<Medlem> getMedlemmer() {
		return db.getMedlemmer();
	}

	public void addMember(FormEvent ev) throws SQLException {
		
		LocalDate date;
		
		String forNavn = ev.getForNavn();
		String etterNavn = ev.getEtterNavn();
		String eMail = ev.geteMail();
		String tlf = ev.getTlf();
		String adresse = ev.getAdresse();
		String postNr = ev.getPostNr();
		
		date = ev.getfDate();
		
		Sex sex = null;

		KontingentCategory kontCat = null;
		Kontingent kont = null;

		/*
		 * checking wich kontingentCategory the member has registered.
		 */
		if (ev.getKontCat().equals("Medlem")) {
			kontCat = KontingentCategory.member;
			kont = new Kontingent(kontCat, true);

		} else if (ev.getKontCat().equals("Støttemedlem")) {
			kontCat = KontingentCategory.supportingMember;
			kont = new Kontingent(kontCat, true);

		} else if (ev.getKontCat().equals("Ingen betaling")) {
			kontCat = KontingentCategory.noPayment;
			kont = new Kontingent(kontCat, false);

		}
		
		if ( ev.getSex().equals("Kvinne")) {
			sex = Sex.female;
		} else if(ev.getSex().equals("Mann")) {
			sex = Sex.male;
		} else {
			sex = Sex.unknown;
		}

		/*
		 * passing the info gathered to the Medlem constructor.
		 */
		Medlem medlem = new Medlem(forNavn, etterNavn, date, tlf, eMail,
				adresse, postNr, kont, sex);

		if (kont != null) {
			kont.setEierKont(medlem);
		}

		db.addMember(medlem);
		db.addKont(kont);

	}

	public void removeRecord(String firstName, String lastName) {
		try {
			db.removeMedlem(firstName,lastName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void skrivUtMedlemmer() {
		db.skrivUt();
	}

	public void saveToFile(File file) throws IOException {
		db.saveToFile(file);
	}

	public void loadFromFile(File file) throws IOException {
		db.loadFromFile(file);
	}

	public void save() throws SQLException {
		db.save();
	}
	
	public void load() throws SQLException {
		db.load();
	}
	
	public void connect() throws Exception {
		db.connect();
	}
	
	/*
	public void disconnect() {
		db.disconnect();
	}
	*/
	
	
	/*
	 * 
	 * Method that adds a few members to the database.
	 * 
	 */
/*
	public void debugAddMembers() {
		Kontingent debugKontingent = new Kontingent(KontingentCategory.member,
				true);

		Medlem debugMedlem = new Medlem("Arne", "Pedersen", LocalDate.of(1989,12,27),
				"91754398","test_mail@gmail.com" , "sletterudveien 23b",
				"1435", debugKontingent,Sex.male);
		debugKontingent.setEierKont(debugMedlem);
		db.addMember(debugMedlem);
		db.addKont(debugKontingent);

		debugKontingent = new Kontingent(KontingentCategory.noPayment, false);
		debugMedlem = new Medlem("Hanne", "Stordalen", LocalDate.of(1967,9,18),
				 "26834867","test_mail2@gmail.com", "sletterudveien 24b",
				"1435", debugKontingent,Sex.female);
		debugKontingent.setEierKont(debugMedlem);
		db.addMember(debugMedlem);
		db.addKont(debugKontingent);

		debugMedlem = new Medlem("Jens", "Stoltenberg", LocalDate.of(2001,4,21),
				"15376978", "test_mail3@gmail.com", "sletterudveien 23a",
				"1435", debugKontingent,Sex.male);
		debugKontingent.setEierKont(debugMedlem);
		db.addMember(debugMedlem);
		db.addKont(debugKontingent);

		debugMedlem = new Medlem("Frida", "Fjomp", LocalDate.of(1992,2,22),
				"12475812", "test_mail4@gmail.com", "sletterudveien 26c",
				"1435", debugKontingent,Sex.female);
		debugKontingent.setEierKont(debugMedlem);
		db.addMember(debugMedlem);
		db.addKont(debugKontingent);
	}
	
	*/

	public void report() {
		db.report();
		
	}
	
	public void updateMedlem(UpdateFormEvent e) {
		db.updateMedlem(e);
		
	}
	
	public double[][] getMedlemTall() {
		return db.getMedlemTall();
	}
	
	public double[][] getMedlemStotte() {
		return db.getMedlemStotte();
	}
	
	public int getMedlemAntall() {
		return db.getMedlemAntall();
	}
	
	public int getSrKvinner() {
		return db.getSrKvinner();
	}
	
	public int getSrMenn() {
		return db.getSrMenn();
	}
	
	public int getJrKvinner() {
		return db.getJrKvinner();
	}
	
	public int getJrMenn() {
		return db.getJrMenn();
	}

	public List<Medlem> getStatisticsMembers() {
		return db.getStatisticsMembers();
	}

}
