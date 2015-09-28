package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Medlem;

public class MedlemTableModel extends AbstractTableModel {

	private List<Medlem> db;

	private String[] colNames = { "Fornavn", "EtterNavn", "F.dato", 
			"Telefon","E-Mail", "Adresse", "PostNr","kjønn", "Kont Betalt" };

	public MedlemTableModel() {

	}

	@Override
	public String getColumnName(int column) {

		return colNames[column];
	}

	public void setData(List<Medlem> db) {
		this.db = db;
	}

	@Override
	public int getColumnCount() {
		return colNames.length;
	}

	@Override
	public int getRowCount() {
		return db.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Medlem medlem = db.get(row);

		switch (col) {
		case 0:
			return medlem.getForNavn();
		case 1:
			return medlem.getEtterNavn();
		case 2:
			return medlem.getDateOfBirth();
		case 3:
			return medlem.getTlf();
		case 4:
			return medlem.geteMail();
		case 5:
			return medlem.getAdresse();
		case 6:
			return medlem.getPostNr();
		case 7:
			return medlem.getSex();
		case 8:
			return medlem.getKontCat();

		}
		return null;
	}

}
