package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;


public class ToolBar extends JPanel{
	
	private WindowChange change;
	private JButton menuBtn;
	private JButton regButton;
	private JButton tableButton;
	private JButton demographyButton;
	private JButton reportButton;
	
	public ToolBar() {
		setBorder(BorderFactory.createEtchedBorder());
		setBackground(Color.GRAY);
		
		menuBtn = new JButton("Meny");
		regButton = new JButton("Registrering");
		tableButton = new JButton("Tabell");
		demographyButton = new JButton("Demografi");
		reportButton = new JButton ("Report");
		
		
		
		/*
		 * using system look and feel se we need to set the background color of the buttons the same as the panel background.
		 */
		menuBtn.setBackground(Color.GRAY);
		regButton.setBackground(Color.GRAY);
		tableButton.setBackground(Color.GRAY);
		demographyButton.setBackground(Color.GRAY);
		regButton.setBackground(Color.GRAY);
		reportButton.setBackground(Color.GRAY);
		
		/*
		 * 
		 * ActionEvents for the buttons on the toolbar.
		 * 
		 * TODO 
		 * the way the windows are changed using different strings are some stoneage firstSemester shit. got to change this if i really really have to.
		 * if it ain't broke, don't fix it.
		 * 
		 */
		
		menuBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				if( change != null) {
					change.setWindow("menu");
				}
			}		
		});
		
		regButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				if( change != null) {
					change.setWindow("reg");
				}
			}		
		});
		tableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				if( change != null) {
					change.setWindow("table");
				}
			}		
		});
		demographyButton.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent e) {
				if(change != null) {
					change.setWindow("statistics");
				}
				
			}
			
		});
		
		
		reportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if( change != null) {
					change.setWindow("report");
				}
			}
		});
		
		
		
		setLayout(new FlowLayout(FlowLayout.LEFT));
		add(menuBtn);
		add(regButton);
		add(tableButton);
		add(demographyButton);
		//add(reportButton);
		
	}
	
	public void setWindowChange(WindowChange change) {
		this.change = change;
	}

	
}
