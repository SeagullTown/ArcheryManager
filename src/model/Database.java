package model;

import java.awt.GridLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.commons.dbcp2.BasicDataSource;

import gui.UpdateFormEvent;

/*
 * All storing of data should be in this class.
 */


/*
 * TODO: implement connection pooling, this breaks too easily.
 */
public class Database {
	
	private static final BasicDataSource dataSource = new BasicDataSource();
	private Preferences databasePrefs;

	private List<Medlem> medlemsListe;
	private List<Kontingent> kontingentListe;
	private String dbPass;
	private boolean firstStart;
	
	static {
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		
		
	}
	
	
	/*
	 * debug arrays for testing the statistics function of the program.
	 */
	private double[][] medlemsAntall;
	private double[][] stotteMedlemAntall;
	
	
	//the connection
	
	//private Connection con;

	public Database() {
		
		databasePrefs = Preferences.userRoot().node("club");
		medlemsListe = new LinkedList<Medlem>();
		kontingentListe = new LinkedList<Kontingent>();
		/*
		 * TODO: debug arrays. not actual data.
		 */
		medlemsAntall = new double[][]{{2009,2010,2011,2012,2013,2014,2015},{50,55,50,60,61,70,72}};
		stotteMedlemAntall = new double[][]{{2009,2010,2011,2012,2013,2014,2015},{6,8,9,8,8,7,8}};
		
		if(databasePrefs.get("databaseURL", "No").equals("No") || databasePrefs.get("databaseUser", "no").equals("no")) {
			firstStart = true;
		}
		
	}
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
	
	
	/*
	 * method opens a panel for password input
	 */
	public int retrievePassword() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Enter database password:");
		JPasswordField passField = new JPasswordField(10);
		panel.add(label);
		panel.add(passField);
		String[] options = new String[]{"OK", "Cancel"};
		
		int option = JOptionPane.showOptionDialog(null, panel, "Skriv inn passord",
		                         JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
		                         null, options, options[1]);
		if(option == 0) {// pressing OK button
		    char[] password = passField.getPassword();
		    System.out.println("ok pressed");
		    dbPass = new String(password);
		    return 0;
		} else { // pressing cancel or quitbutton
			return 1;
		}
	}
	
	

	public void connect() throws Exception {
		if (firstStart) {
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(3, 2));
			JLabel urlLabel = new JLabel("Database URL:");
			JLabel UserLabel = new JLabel("Database User:");
			JLabel passLabel = new JLabel("Database Password:");
			
			JTextField urlField = new JTextField(15);
			JTextField  userField = new JTextField(15);
			JPasswordField passField = new JPasswordField(15);
			
			panel.add(urlLabel);
			panel.add(urlField);
			panel.add(UserLabel);
			panel.add(userField);
			panel.add(passLabel);
			panel.add(passField);
			panel.repaint();
			
			String[] options = new String[]{"OK,","Cancel"};
			
			int option = JOptionPane.showOptionDialog(null, panel, "Fyll inn database info",
                    JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[1]);
			if(option == 0) {// pressing OK button
				dataSource.setUrl("jdbc:mysql://" + urlField.getText());
				dataSource.setUsername(userField.getText());
				dataSource.setPassword(new String(passField.getPassword()));
			} else if(option == 1) {
				System.out.println("connection data cancelled");
			}
			
			databasePrefs.put("databaseURL", urlField.getText());
			databasePrefs.put("databaseUser", userField.getText());
			
			
			
		} else {
			
			dataSource.setUrl("jdbc:mysql://" + databasePrefs.get("databaseURL","no"));
			dataSource.setUsername(databasePrefs.get("databaseUser","no" ));
			
			
			
			if(retrievePassword() == 1) {
			System.out.println("connection cancelled");
			return;
			}
			dataSource.setPassword(dbPass);
		}

		

		

	}
	
	
	
	
	// TODO: all sql statements have to be reviewed and changed due to changes in how members are stored and how the databases are handled.
	public void save() throws SQLException {
		
		Connection con = getConnection();
		
		try {
			String checkSql = "select count(*) as count from medlemsListe where ID=?";
			PreparedStatement checkStmt = con.prepareStatement(checkSql);

			String insertSql = "insert into medlemsListe (ID, for_navn, etter_navn, f_dato, telefon, e_mail, adresse, post_nr, sex, Kont_klasse, betalt) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement insertStatement = con.prepareStatement(insertSql);
			
			String updateSql = "update medlemsListe set for_navn=?, etter_navn=?,f_dato=?, telefon=?, e_mail=?, adresse=?, post_nr=?, sex=?, Kont_klasse=?, betalt=? where ID=?";
			PreparedStatement updateStatement = con.prepareStatement(updateSql);
			//System.out.println("før forløkke");
			for (Medlem medlem: medlemsListe) {
				
				int ID = medlem.getId();
				String forNavn = medlem.getForNavn();
				String etterNavn = medlem.getEtterNavn();
				LocalDate date = medlem.getDateOfBirth();
				String tlf = medlem.getTlf();
				String eMail = medlem.geteMail();
				String adresse = medlem.getAdresse();
				String postNr = medlem.getPostNr();
				Sex sex = medlem.getSex();
				KontingentCategory kontCat = medlem.getKontCat();
				boolean betalt = medlem.getBetalt();
				
				
				//checking if this medlem have been assigned an ID yet to 
				//determine if the record should be updated or inserted
				checkStmt.setInt(1, ID);

				ResultSet checkResult = checkStmt.executeQuery();
				checkResult.next();

				int count = checkResult.getInt(1);
				
				//System.out.println("attempting to insert people");
				if (count == 0) {
					//System.out.println("Inserting person with ID " + ID);
					
					int col = 1;
					insertStatement.setInt(col++, ID);
					insertStatement.setString(col++, forNavn);
					insertStatement.setString(col++, etterNavn);
					insertStatement.setDate(col++, Date.valueOf(date));
					
					insertStatement.setString(col++, tlf);
					insertStatement.setString(col++, eMail);
					insertStatement.setString(col++, adresse);
					insertStatement.setString(col++, postNr);
					insertStatement.setString(col++, sex.name());
					insertStatement.setString(col++, kontCat.name());
					insertStatement.setBoolean(col++, betalt);
					
					
					insertStatement.executeUpdate();
					
					
				} else {
					//System.out.println("Updating person with ID " + ID);
					
					int col = 1;
					
					updateStatement.setString(col++, forNavn);
					updateStatement.setString(col++, etterNavn);
					updateStatement.setDate(col++, Date.valueOf(date));
					updateStatement.setString(col++, tlf);
					updateStatement.setString(col++, eMail);		
					updateStatement.setString(col++, adresse);
					updateStatement.setString(col++, postNr);
					updateStatement.setString(col++, sex.name());
					updateStatement.setString(col++, kontCat.name());
					updateStatement.setBoolean(col++, betalt);
					updateStatement.setInt(col++, ID);
					
					updateStatement.executeUpdate();
				}
			}
			
			updateStatement.close();
			insertStatement.close();
			checkStmt.close();
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		} catch (NullPointerException e) {
			System.out.println("nullpointer after trying to use database, no connection to the database");
		}
	}
	
	public void load() throws SQLException {
		Connection con = getConnection();
		medlemsListe.clear();
		
		String sql = "select ID, for_navn, etter_navn, f_dato, telefon, e_mail, adresse, post_nr, sex, kont_klasse, betalt from medlemsListe order by for_navn";
		try {
			
		Statement selectStatement = con.createStatement();
		
		ResultSet results;
		
		/*
		 * surrounded the whole thing with a try catch, if something breaks...it's probably because of that.
		 */
		
		results = selectStatement.executeQuery(sql);
		
		while(results.next()) {
			
			int ID = results.getInt("ID");
			String forNavn = results.getString("for_navn");
			String etterNavn = results.getString("etter_navn");
			
			Date date = results.getDate("f_dato");
			LocalDate fDato = date.toLocalDate();
			
			String tlf = results.getString("telefon");
			String eMail = results.getString("e_mail");
			String adresse = results.getString("adresse");
			String postNr = results.getString("post_nr");
			
			
			Sex sex = Sex.valueOf(results.getString("sex"));
			
			
			
			KontingentCategory kontCat = KontingentCategory.valueOf(results.getString("Kont_klasse"));
			boolean betalt = results.getBoolean("betalt");
			
			Kontingent kont = new Kontingent(kontCat, betalt);
			Medlem medlem = new Medlem(ID, forNavn, etterNavn,fDato, tlf, eMail, adresse, postNr, kont,sex);
			
			medlemsListe.add(medlem);
			
		}
		
		results.close();
		selectStatement.close();
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
		} catch (SQLException e) {
			System.out.println("failed to connect");
		} catch (NullPointerException e1) {
			System.out.println("nullpointer exception after failing to load database.");
		}
		
		
	}

	public void addMember(Medlem medlem) throws SQLException {
		medlemsListe.add(medlem);
		save();
		
		
	}

	public void addKont(Kontingent kont) {
		kontingentListe.add(kont);
	}

	public void removeMedlem(Medlem medlem) throws SQLException {
		
	}

	public void removeMedlem(String firstName,String lastName) throws SQLException {
		Connection con = getConnection();
		
		for(Medlem medlem: medlemsListe) {
			if(medlem.getForNavn().equals(firstName) && medlem.getEtterNavn().equals(lastName)) {
				String removeSql = "delete from medlemsListe where ID=?";
				PreparedStatement deleteStatement = con.prepareStatement(removeSql);
		
				deleteStatement.setInt(1, medlem.getID());
		
				deleteStatement.executeUpdate();
		
				deleteStatement.close();
		
				//delete from Database class.
		
				medlemsListe.remove(medlem);
				break;
			}
		}
		
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		//deletes from sql database
		
		
		
		
	}
	
	public void updateMedlem(UpdateFormEvent e) {
		Medlem medlem = null;
		
		for(Medlem med:medlemsListe) {
			if(med.getID() == e.getId()) {
				medlem = med;
			}
		}
		
		medlem.setForNavn(e.getForNavn());
		medlem.setEtterNavn(e.getEtterNavn());
		medlem.setTlf(e.getTlf());
		
		medlem.seteMail(e.geteMail());
		
		medlem.setAdresse(e.getAdresse());
		medlem.setPostNr(e.getPostNr());
		KontingentCategory kontCat = null;
		boolean payment = false;
		
		
		if (e.getKontCat().equals("Medlem")) {
			kontCat = KontingentCategory.member;
			payment =true;
			
		} else if (e.getKontCat().equals("Støttemedlem")) {
			kontCat = KontingentCategory.supportingMember;
			payment = true;
			
		} else if (e.getKontCat().equals("Ingen betaling")) {
			kontCat = KontingentCategory.noPayment;
			payment = false;
			
		}
		
		Kontingent kont = new Kontingent(kontCat,payment);
		kont.setEierKont(medlem);
		try {
			save();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
	
	public List<Medlem> getMedlemmer() {
		return Collections.unmodifiableList(medlemsListe);
	}

	public void skrivUt() {
		for (Medlem medlem : medlemsListe) {
			System.out.println(medlem.getForNavn());
		}
	}

	public void saveToFile(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		Medlem[] medlemmer = medlemsListe.toArray(new Medlem[medlemsListe
				.size()]);

		oos.writeObject(medlemmer);

		oos.close();

	}

	public void loadFromFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);

		try {
			Medlem[] medlemmer = (Medlem[]) ois.readObject();

			medlemsListe.clear();
			medlemsListe.addAll(Arrays.asList(medlemmer));

		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
	}

	public void report() {
		for(Medlem medlem: medlemsListe) {
			medlem.report();
		}
		
	}
/*
 * methods for retrieving statistics for the demography panel.	
 */
	public double[][] getMedlemTall() {
		return medlemsAntall;
	}
	
	public double[][] getMedlemStotte() {
		return stotteMedlemAntall;
	}
	
	public int getMedlemAntall() {
		return medlemsListe.size();
	}
	
	public int getSrKvinner() {
		int i = 0;
		
		for(Medlem medlem: medlemsListe) {
			if(!isJunior(medlem)) {
				if(medlem.getSex().equals(Sex.female)) {
					i++;
				}
			}
		}
		
		return i;
	}
	
	public int getSrMenn() {
		int i = 0;
		for(Medlem medlem: medlemsListe) {
			if(!isJunior(medlem)) {
				if(medlem.getSex().equals(Sex.male)) {
					i++;
				}
			}
		}
		return i;
	}
	
	public int getJrKvinner() {
		int i = 0;
		for(Medlem medlem: medlemsListe) {
			if(isJunior(medlem)) {
				if(medlem.getSex().equals(Sex.female)) {
					i++;
				}
			}
		}
		return i;
	}
	
	public int getJrMenn() {
		int i = 0;
		for(Medlem medlem: medlemsListe) {
			if(isJunior(medlem)) {
				if(medlem.getSex().equals(Sex.male)) {
					i++;
				}
			}
		}
		return i;
	}
/*
 * end statistics methods.
 */
	
	/*
	 * compares the current year against members birthdate, if it is greater then 20 it returns false.
	 */
	public boolean isJunior(Medlem m) {
		if(LocalDate.now().getYear()-m.getDateOfBirth().getYear() > 20) {
			return false;
		} else {
			return true;
		}
		
	}
}
