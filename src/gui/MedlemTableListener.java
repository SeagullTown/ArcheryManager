package gui;

public interface MedlemTableListener {
	public void rowDeleted(String firstName,String lastName);
	public void rowEdit(int row);
	public void copyRow(int row);
	public void copySquare(String square);

}
