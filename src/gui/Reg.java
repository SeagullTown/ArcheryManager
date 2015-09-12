package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.omg.CORBA.CODESET_INCOMPATIBLE;

public class Reg extends JPanel {

	private JButton regButton;

	private FormListener formListener;

	private JTextField forNavnField;
	private JTextField etterNavnField;
	// private JTextField fYearField;
	// private JTextField fMonthField;
	// private JTextField fDayField;
	private JTextField tlfField;
	private JTextField eMailField;
	private JTextField adresseField;
	private JTextField postNrField;
	private JFormattedTextField fDateField;

	private DateFormat dateFormat;
	private NumberFormat numberFormat;
	
	private Color errorColor;

	private JCheckBox kontBox;

	private JComboBox kontCatCombo;
	private JComboBox sexCombo;

	private JLabel fNavnLabel;
	private JLabel eNavnLabel;
	private JLabel datoLabel;
	private JLabel telefonLabel;
	private JLabel mailLabel;
	private JLabel adresseLabel;
	private JLabel postNrLabel;
	private JLabel kontCatLabel;
	private JLabel sexLabel;

	Reg() {

		setBackground(Color.LIGHT_GRAY);

		forNavnField = new JTextField(10);
		etterNavnField = new JTextField(10);
		
		//this color is painted on fields with errors in them.
		errorColor = new Color(255,75,75);

		/*
		 * commented out as i'm experimenting with a new version of the
		 * dateinput for the registration form.
		 */

		// fYearField = new JTextField("y",4);
		// fMonthField = new JTextField("m",3);
		// fDayField = new JTextField("d",3);

		tlfField = new JTextField(10);
		eMailField = new JTextField(10);
		adresseField = new JTextField(10);
		postNrField = new JTextField(10);

		dateFormat = new SimpleDateFormat("ddMMyy");
		numberFormat = NumberFormat.getNumberInstance();
		

		fDateField = new JFormattedTextField(dateFormat);

		kontCatCombo = new JComboBox();
		sexCombo = new JComboBox();

		kontBox = new JCheckBox();

		fNavnLabel = new JLabel("Fornavn");
		eNavnLabel = new JLabel("Etternavn");
		datoLabel = new JLabel("Dato");
		telefonLabel = new JLabel("Telefon");
		mailLabel = new JLabel("eMail");
		adresseLabel = new JLabel("Adresse");
		postNrLabel = new JLabel("Postnummer");
		kontCatLabel = new JLabel("Kontigentklasse");
		sexLabel = new JLabel("Kjønn");

		regButton = new JButton("Registrer");
		regButton.setPreferredSize(new Dimension(114, 20));
		regButton.setBackground(Color.LIGHT_GRAY);

		/*
		 * Setting accessibility
		 */

		fNavnLabel.setLabelFor(forNavnField);
		eNavnLabel.setLabelFor(etterNavnField);
		datoLabel.setLabelFor(fDateField);
		telefonLabel.setLabelFor(tlfField);
		mailLabel.setLabelFor(eMailField);
		adresseLabel.setLabelFor(adresseField);
		postNrLabel.setLabelFor(postNrField);
		kontCatLabel.setLabelFor(kontCatCombo);
		sexLabel.setLabelFor(sexCombo);

		/*
		 * adding content to combobox
		 */
		DefaultComboBoxModel kontModel = new DefaultComboBoxModel();
		kontModel.addElement("Medlem");
		kontModel.addElement("Støttemedlem");
		kontModel.addElement("Ingen betaling");
		kontCatCombo.setModel(kontModel);
		kontCatCombo.setSelectedIndex(0);

		DefaultComboBoxModel sexModel = new DefaultComboBoxModel();
		sexModel.addElement("Mann");
		sexModel.addElement("Kvinne");
		sexModel.addElement("Ukjent");

		sexCombo.setModel(sexModel);
		sexCombo.setSelectedIndex(0);

		setActionListeners();
		layoutComponents();
	}

	/*
	 * lays out the components using gridbag layout.
	 */
	private void layoutComponents() {
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		int inset1 = 2;

		gc.weightx = 1;
		// gc.weighty = 10;

		/*
		 * MAL
		 * 
		 * gc.gridx = 0; gc.gridy++; gc.fill = GridBagConstraints.NONE;
		 * gc.anchor = GridBagConstraints.FIRST_LINE_END; gc.insets = new
		 * Insets(inset1,1,0,5); add(Label,gc);
		 * 
		 * gc.gridx = 1; gc.fill = GridBagConstraints.NONE; gc.anchor =
		 * GridBagConstraints.FIRST_LINE_START; gc.insets = new
		 * Insets(inset1,1,0,5); add(Field,gc);
		 * 
		 */

		int in1 = 2;
		int in2 = 1;
		int in3 = 0;
		int in4 = 5;

		gc.weightx = 1;

		/*
		 * kolonne 0
		 */
		gc.gridx = 0;
		gc.gridy = 0;

		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.BASELINE_TRAILING;
		gc.insets = new Insets(in1, in2, in3, in4);

		add(fNavnLabel, gc);
		gc.gridy++;

		add(eNavnLabel, gc);
		gc.gridy++;

		add(datoLabel, gc);
		gc.gridy++;

		add(telefonLabel, gc);
		gc.gridy++;

		add(mailLabel, gc);
		gc.gridy++;

		add(adresseLabel, gc);
		gc.gridy++;

		add(postNrLabel, gc);
		gc.gridy++;

		// add(kontBoxLabel,gc);
		// gc.gridy++;

		add(kontCatLabel, gc);
		gc.gridy++;

		add(sexLabel, gc);
		gc.gridy++;

		/*
		 * kolonne 1
		 */
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 1;
		gc.gridy = 0;
		gc.gridwidth = 3;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;

		add(forNavnField, gc);
		gc.gridy++;

		add(etterNavnField, gc);
		gc.gridy++;

		gc.gridwidth = 1;
		add(fDateField, gc);

		gc.gridy++;
		gc.gridx = 1;
		gc.gridwidth = 3;

		add(tlfField, gc);
		gc.gridy++;

		add(eMailField, gc);
		gc.gridy++;

		add(adresseField, gc);
		gc.gridy++;

		add(postNrField, gc);
		gc.gridy++;

		add(kontCatCombo, gc);
		gc.gridy++;

		add(sexCombo, gc);
		gc.gridy++;
		add(regButton, gc);
		gc.gridy++;

	}

	public void setFormListener(FormListener formListener) {
		this.formListener = formListener;
	}

	public boolean isNumber(String text) {
		try {
			Integer.parseInt(text);
			return false;
		} catch (NumberFormatException nfe) {
			// Not an integer
		}
		return true;
	}

	public void setActionListeners() {

		/*
		 * actionListener for when the submit form button is pressed
		 * 
		 * this aims to make sure the user don't fuck up the input of
		 * information that is going into the database.
		 */
		regButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// System.out.println("actionperformed checkpoint 1"); //debug

				try {
					if (forNavnField.getText().equals("") || etterNavnField.getText().equals("") ||
							// fDayField.getText().equals("") ||
							// fMonthField.getText().equals("") ||
							// fYearField.getText().equals("") ||
					fDateField.getText().equals("") || eMailField.getText().equals("") || tlfField.getText().equals("")
							|| adresseField.getText().equals("") || postNrField.getText().equals("")) {
						JOptionPane.showMessageDialog(getParent(), "Alle felter er ikke fylt");

					} else if (isNumber(tlfField.getText()) || isNumber(postNrField.getText())) {

						JOptionPane.showMessageDialog(getParent(), "Nummerfelt(er) er fylt med bokstaver");

					} else {
						String forNavn = forNavnField.getText();
						String etterNavn = etterNavnField.getText();
						// int fDay = Integer.parseInt(fDayField.getText());
						// int fMonth = Integer.parseInt(fMonthField.getText());
						// int fYear = Integer.parseInt(fYearField.getText());
						Date date = (Date) fDateField.getValue();
						LocalDate fDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						String eMail = eMailField.getText();
						String tlf = tlfField.getText();
						String adresse = adresseField.getText();
						String postNr = postNrField.getText();
						String kontCat = (String) kontCatCombo.getSelectedItem();
						String sex = (String) sexCombo.getSelectedItem();

						FormEvent ev = new FormEvent(this, forNavn, etterNavn, fDate, eMail, tlf, adresse, postNr, kontCat, sex);

						if (formListener != null) {
							formListener.formEventOccured(ev);
						}
						forNavnField.setText("");
						etterNavnField.setText("");
						fDateField.setText("ddMMyy");
						fDateField.setValue(null);
						eMailField.setText("");
						tlfField.setText("");
						adresseField.setText("");
						postNrField.setText("");
						kontBox.setSelected(false);
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(Reg.this, "Alle felter er ikke fylt");

				} catch (DateTimeException e2) {
					JOptionPane.showMessageDialog(Reg.this, "Error ved registrering av dato");
				}
			}

		});

		/*
		 * focusListener for the dateinput field
		 * 
		 * This aims to handle the format of the datefield.
		 * 
		 * what i want the field to do is make date input be quick.
		 */
		fDateField.addFocusListener(new FocusListener() {

			public void focusLost(FocusEvent arg0) {

				/*
				 * if (fDateField.getText().equals("")) {
				 * fDateField.setText("ddMMyy"); } else { try { Date date =
				 * (Date) fDateField.getValue(); LocalDate localDate =
				 * date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
				 * ; // fDateField.setText(localDate.toString()); } catch
				 * (Exception e) { fDateField.setBackground(Color.red);
				 * fDateField.setText("ddMMyy"); e.printStackTrace(); } }
				 */

			}

			public void focusGained(FocusEvent arg0) {
				fDateField.setValue(null);
				;

			}
		});

		fDateField.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				if (!fDateField.getText().equals("")) {
					
					if (fDateField.getText().length() == 6 || fDateField.getText().length() == 10) {
						fDateField.setBackground(Color.white);
						if (fDateField.getText().length() == 6) {
							Date date = (Date) fDateField.getValue();
							LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
							
							fDateField.setToolTipText(localDate.toString());

						}

					} else {
						fDateField.setBackground(errorColor);
					}

				}

			}
		});
		
		postNrField.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(!postNrField.getText().equals("")) {
					if(postNrField.getText().length() == 4) {
						postNrField.setBackground(Color.white);
					} else {
						postNrField.setBackground(errorColor);

					}
				}
				
			}
		});

	}

}
