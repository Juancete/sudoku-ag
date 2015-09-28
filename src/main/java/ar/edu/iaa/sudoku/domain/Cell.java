package ar.edu.iaa.sudoku.domain;

import ar.edu.iaa.sudoku.exeptions.BussinessException;

public class Cell {
	
	private int GenPosition;
    private int row;
    private int column;
    private boolean blocked;
    private int value;

    public Cell (int linearPosition, int value,boolean blocked){
    	this.setValue(value);
    	this.blocked = blocked;
        this.row = linearPosition/9;
        this.column = linearPosition -this.row * 9;        
    }
    public Cell(int aFile, int aColumn, int value, boolean blocked) {
    	this.setValue(value);
    	this.blocked = blocked;
        this.row = aFile;
        this.column = aColumn;
    }
    public boolean compareValues(int valueTocompare){
    	return this.value == valueTocompare;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */    
    public boolean equalsRow(int file){
    	return this.getRow() == file;
    }
    
    public boolean equalsColumn(int column){
    	return this.getColumn() == column;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;
        Cell key = (Cell) o;
        return getRow() == key.getRow() && getColumn() == key.getColumn();
    }
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = getRow();
        result = 31 * result + getColumn();
        return result;
    }

    /*
     * Getters & Setters
     */
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		if (this.blocked)
			throw new BussinessException("No se puede asignar un valor a una celda bloqueada!");
		this.value = value;
	}
	public int getLinearPosition() {
		return row * 9 + column;
	}
	public int getGenPosition() {
		return GenPosition;
	}
	public void setGenPosition(int genPosition) {
		GenPosition = genPosition;
	}

}
