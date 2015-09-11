package ar.edu.iaa.sudoku.domain;

import ar.edu.iaa.sudoku.exeptions.BussinessException;

public class Cell {

    private int file;
    private int column;
    private boolean blocked;
    private int value;

    public Cell (int linearPosition, int value,boolean blocked){
    	this.setValue(value);
    	this.blocked = blocked;
        this.file = linearPosition/9;
        this.column = linearPosition -this.file * 9;        
    }
    public Cell(int aFile, int aColumn, int value, boolean blocked) {
    	this.setValue(value);
    	this.blocked = blocked;
        this.file = aFile;
        this.column = aColumn;
    }
    public boolean compareValues(int valueTocompare){
    	return this.value == valueTocompare;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */    
    public boolean equalsFile(int file){
    	return this.getFile() == file;
    }
    
    public boolean equalsColumn(int column){
    	return this.getColumn() == column;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;
        Cell key = (Cell) o;
        return getFile() == key.getFile() && getColumn() == key.getColumn();
    }
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = getFile();
        result = 31 * result + getColumn();
        return result;
    }

    /*
     * Getters & Setters
     */
	public int getFile() {
		return file;
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
		return file * 9 + column;
	}

}
