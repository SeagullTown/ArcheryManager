package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HistoryForm extends JPanel{
	
	/*
	 * Historyform should allow for entering several types of statistics.
	 */
	
	JButton submitButton;
	JTextField addYearField;
	JTextField editYear;
	
	JLabel yearLabel;
	JLabel editYearLabel;
	
	
	public HistoryForm() {
		setBackground(Color.LIGHT_GRAY);
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		submitButton = new JButton("Registrer");
		submitButton.setPreferredSize(new Dimension(114,20));
		submitButton.setBackground(Color.LIGHT_GRAY);
		submitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				//actionlistener
			}
			
		});
		
		/*
		 * Components
		 */
		gc.insets = new Insets(2,1,0,5);
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.BASELINE_TRAILING;
		
		gc.gridx = 0;
		gc.gridy = 0;
		
		add(yearLabel,gc);
		
		
		
		
	}
}
