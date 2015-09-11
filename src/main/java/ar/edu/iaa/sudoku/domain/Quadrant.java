package ar.edu.iaa.sudoku.domain;

public class Quadrant {
	private int number;
	private int offset_file;
	private int offset_column;
	
	
	public Quadrant(int number, int offset_file, int offset_column) {
		super();
		this.setNumber(number);
		this.setOffset_column(offset_column);
		this.setOffset_file(offset_file);
	}
	public int getOffset_file() {
		return offset_file;
	}
	public void setOffset_file(int offset_file) {
		this.offset_file = offset_file;
	}
	public int getOffset_column() {
		return offset_column;
	}
	public void setOffset_column(int offset_column) {
		this.offset_column = offset_column;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int nomber) {
		this.number = nomber;
	}
}
