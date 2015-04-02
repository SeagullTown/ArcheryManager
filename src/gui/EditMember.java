package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Medlem;

/*
 * this is a form for editing the info of existing members in the database.
 * 
 * info not to be updated are ID and birth date.
 * 
 * the form will contain almost the same checkboxes as the registrationform.
 * if a field is blank, it will not be updated. if there is anything in it 
 * and it is valid, it will be updated to the database immediatly.
 */
public class EditMember extends JPanel {

	Medlem member;
	int memberID;
	LocalDate date;
	private EditMedlemListener editMedlemListener;
	
	JButton editButton;

	private JTextField forNavnField;
	private JTextField etterNavnField;
	private JTextField tlfField;
	private JTextField eMailField;
	private JTextField adresseField;
	private JTextField postNrField;

	

	private JComboBox kontCatCombo;
	private JComboBox sexCombo;

	private JLabel fNavnLabel;
	private JLabel eNavnLabel;
	private JLabel telefonLabel;
	private JLabel mailLabel;
	private JLabel adresseLabel;
	private JLabel postNrLabel;

	private JLabel kontCatLabel;
	private JLabel sexLabel;
	
	

	EditMember(Medlem member) {

		this.member = member;
		memberID = member.getId();
		
		date = member.getDateOfBirth();

		setBackground(Color.LIGHT_GRAY);

		editButton = new JButton("Edit");
		editButton.setBackground(getBackground());
		editButton.setPreferredSize(new Dimension(114, 20));
		

		forNavnField = new JTextField(13);
		forNavnField.setText(member.getForNavn());

		etterNavnField = new JTextField(13);
		etterNavnField.setText(member.getEtterNavn());

		tlfField = new JTextField(13);
		tlfField.setText(member.getTlf());

		eMailField = new JTextField(13);
		eMailField.setText(member.geteMail());

		adresseField = new JTextField(13);
		adresseField.setText(member.getAdresse());

		postNrField = new JTextField(13);
		postNrField.setText(member.getPostNr());

		kontCatCombo = new JComboBox();
		sexCombo = new JComboBox();


		fNavnLabel = new JLabel("Fornavn");
		eNavnLabel = new JLabel("Etternavn");
		telefonLabel = new JLabel("Telefon");
		mailLabel = new JLabel("eMail");
		adresseLabel = new JLabel("Adresse");
		postNrLabel = new JLabel("Postnummer");
		kontCatLabel = new JLabel("Kontigentklasse");
		sexLabel = new JLabel("Kjønn");

		DefaultComboBoxModel kontModel = new DefaultComboBoxModel();
		kontModel.addElement("Medlem");
		kontModel.addElement("Støttemedlem");
		kontModel.addElement("Ingen betaling");
		kontCatCombo.setModel(kontModel);
		
		DefaultComboBoxModel sexModel = new DefaultComboBoxModel();
		sexModel.addElement("Ukjent");
		sexModel.addElement("Kvinne");
		sexModel.addElement("Mann");
		sexCombo.setModel(sexModel);
		sexCombo.setSelectedIndex(0);

		

		if (member.getKontCat().toString().equals("member")) {
			kontCatCombo.setSelectedIndex(0);
		} else if (member.getKontCat().toString().equals("supportingMember")) {
			kontCatCombo.setSelectedIndex(1);
		} else if (member.getKontCat().toString().equals("noPayment")) {
			kontCatCombo.setSelectedIndex(2);
		}

		editButton.addActionListener(new ActionListener() {
			
			

			public void actionPerformed(ActionEvent e) {
				String kontCatString = (String) kontCatCombo.getSelectedItem();
				int tempMemberID = memberID;
				
				
				UpdateFormEvent ufe = new UpdateFormEvent(this, tempMemberID,
						forNavnField.getText(), etterNavnField.getText(),
						eMailField.getText(), tlfField.getText(), adresseField
								.getText(), postNrField.getText(),
						kontCatString);
				
				if(editMedlemListener != null) {
					editMedlemListener.editEventOccured(ufe);
				}
			}
		});

		layoutComponents();
	}

	private void layoutComponents() {
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

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
		gc.gridx = 1;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;

		add(forNavnField, gc);
		gc.gridy++;

		add(etterNavnField, gc);
		gc.gridy++;

		add(tlfField, gc);
		gc.gridy++;

		add(eMailField, gc);
		gc.gridy++;

		add(adresseField, gc);
		gc.gridy++;

		add(postNrField, gc);
		gc.gridy++;

		// add(kontBox,gc);
		// gc.gridy++;

		add(kontCatCombo, gc);
		gc.gridy++;

		add(sexCombo, gc);
		gc.gridy++;
		
		add(editButton, gc);
		gc.gridy++;
		
		
		
	}

	public void setEditMedlemListener(EditMedlemListener editMedlemListener) {
		
		this.editMedlemListener = editMedlemListener;

	}

}
