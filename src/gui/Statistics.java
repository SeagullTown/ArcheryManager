package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import model.Medlem;

public class Statistics extends JPanel {
	public boolean debug;

	// panels
	private JPanel topPanel;
	private JPanel tablePanel;
	private JPanel registerPanel;

	// table
	private JTable table;
	private StatisticsTableModel tableModel;
	private JPopupMenu popup;
	private int column = 0;// column position, gets set on cell selection. it's
							// a buffer because jtable is stupid.

	Statistics(boolean debug) {
		this.debug = debug;
		setBackground(Color.black);

		// creating the panels
		tablePanel = new JPanel();
		topPanel = new JPanel();
		registerPanel = new JPanel();

		// creates and fills the panels with content
		createTablePanel();
		createRegisterPanel();
		createTopPanel();


		setLayout(new BorderLayout());
		add(topPanel, BorderLayout.NORTH);
		add(tablePanel, BorderLayout.CENTER);

		revalidate();
		repaint();

	}

	// returns the topmost panel.
	private void createTopPanel() {
		setBackground(Color.green);

		JButton newReg = new JButton("Ny Registrering");
		JLabel numberTrackedLabel = new JLabel("Medlemmer som følges: ");
		JTextField numberTrackedField = new JTextField();

		// number of members currently being tracked
		int numberTracked = tableModel.getRowCount();
		numberTrackedField.setText(Integer.toString(numberTracked));

		setLayout(new FlowLayout(FlowLayout.LEADING));
		topPanel.add(newReg);
		topPanel.add(numberTrackedLabel);
		topPanel.add(numberTrackedField);

		numberTrackedLabel.setLabelFor(numberTrackedField);

		revalidate();
		repaint();

	}

	// returns the panel that contains the table.
	private void createTablePanel() {
		tablePanel.setBackground(Color.red);
		// creating the table

		tableModel = new StatisticsTableModel(debug);
		table = new JTable();
		table.setModel(tableModel);
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);
		
		tablePanel.setLayout(new BorderLayout());
		tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

		revalidate();
		repaint();

	}

	// returns the panel that contains the members whom statistics are being
	// tracked.
	private void createRegisterPanel() {

	}

	public void setData(List<Medlem> db) {
		tableModel.setData(db);
	}
}
