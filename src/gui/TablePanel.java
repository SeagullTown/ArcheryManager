package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import model.Medlem;

public class TablePanel extends JPanel{
	
	/*
	 * maybe this, it will make the header of the table stick to the top of the view.
	 * container.add(table.getTableHeader(), BorderLayout.PAGE_START);
	 */
	
	private JTable table;
	private MedlemTableModel tableModel;
	private JPopupMenu popup;
	private MedlemTableListener medlemTableListener;
	private int column= 0;//column position, gets set on cell selection. it's a buffer because jtable is stupid.
	
	public TablePanel() {
		
		
		
		tableModel = new MedlemTableModel();
		table = new JTable();
		table.setModel(tableModel);
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//table.setColumnSelectionAllowed(true);
		//table.setRowSelectionAllowed(true);
		table.setFillsViewportHeight(true);
		
		popup = new JPopupMenu();
		JMenuItem removeRecord = new JMenuItem("Slett rad");
		JMenuItem editRecord = new JMenuItem("Rediger rad");
		JMenuItem copyRow = new JMenuItem("Kopier rad");
		JMenuItem copyField = new JMenuItem("Kopier felt");
		popup.add(editRecord);
		popup.add(removeRecord);
		popup.addSeparator();
		popup.add(copyRow);
		popup.add(copyField);
		
		
		
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				
				column = table.columnAtPoint(e.getPoint());
				
				table.getSelectionModel().setSelectionInterval(row,row);
				
				
				
				if(e.getButton() == MouseEvent.BUTTON3) {
					
					popup.show(table, e.getX(), e.getY());
				}
			}
		});
		
		removeRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				String firstName = (String)table.getModel().getValueAt(row,0);
				String lastName = (String)table.getModel().getValueAt(row, 1);
				
				
				if(medlemTableListener != null) {
					medlemTableListener.rowDeleted(firstName,lastName);
					tableModel.fireTableRowsDeleted(row, row);
				}
				
				
			}
		});
		
		editRecord.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				
				if(medlemTableListener != null) {
					medlemTableListener.rowEdit(row);
					tableModel.fireTableRowsUpdated(row, row);
				}
				
			}
			
		});
		
		copyRow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				medlemTableListener.copyRow(row);
				
			}
		});
		
		copyField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				int row = table.getSelectedRow();
				
				
				String square = (String)table.getModel().getValueAt(row, column);
				medlemTableListener.copySquare(square);
				
			}
		});
		setLayout(new BorderLayout());
		add(new JScrollPane(table),BorderLayout.CENTER);
		
		
	}
	
	public void setData(List<Medlem> db) {
		tableModel.setData(db);
	}
	
	public void refresh() {
		tableModel.fireTableDataChanged();
	}
	
	public void setMedlemTableListener(MedlemTableListener listener) {
		this.medlemTableListener = listener;
	}
	public void setRowSort() {
		table.setAutoCreateRowSorter(true);
	}
	public String getText(int row) {
		String text = "";													//columns
		
		LocalDate date = (LocalDate) table.getModel().getValueAt(row, 2);	//birthDate
		
		text = text + (String)table.getModel().getValueAt(row,0 );			//first name
		text = text + " " + (String)table.getModel().getValueAt(row, 1);	//last name
		text = text + " " + (String)date.toString();
		text = text + " " + (String)table.getModel().getValueAt(row, 3);	//phone
		text = text + " " + (String)table.getModel().getValueAt(row, 4);	//email
		text = text + " " + (String)table.getModel().getValueAt(row, 5);	//adress
		text = text + " " + (String)table.getModel().getValueAt(row, 6);	//zipcode
		text = text + " " + (String)table.getModel().getValueAt(row, 7).toString();//gender
		text = text + " " + (String)table.getModel().getValueAt(row, 8).toString();//payment class
		//text = text + " " + (String)table.getModel().getValueAt(row, 9);	//payment true or false  not currently on the table
		
		return text;
	}

}
