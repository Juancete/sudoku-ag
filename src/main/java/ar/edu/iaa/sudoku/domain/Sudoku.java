package ar.edu.iaa.sudoku.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.jgap.Gene;

public class Sudoku {
	private List<Cell> values;
	private List<Cell> cellsToMutate;
	private EvaluationMethod evaluationMethod;

	/*
	 * Constructors
	 */
	public Sudoku() {
		this.values = new ArrayList<Cell>();
	}

	//Carga los valores del problema a resolver
	public Sudoku(String values) {
		this();
		int[] arr = Arrays
				.stream(values.substring(1, values.length() - 1).split(" "))
				.map(String::trim).mapToInt(Integer::parseInt).toArray();
		for (int i = 0; i < arr.length; i++) {
			Integer value=new Integer(arr[i]);
			if (value!= 0)
				this.values.add(new Cell(i, value,true));
			else
				this.values.add(new Cell(i, value,false));
		}
	}


	public double getRepeatedForRow(int valueTofind, int row) {
		double result = (double)values.stream().filter(x -> x.equalsRow(row) && x.getValue() == valueTofind).count()==0?0:(double)values.stream().filter(x -> x.equalsRow(row) && x.getValue() == valueTofind).count()-1;
		if (result > 0)
			this.cellsToMutate.add(this.getCellInRow( valueTofind,row,false));
		else if (new Random().nextInt(25)==0 && this.getCellInRow(valueTofind,row,false) != null)
			this.cellsToMutate.add(this.getCellInRow( valueTofind,row,false));
		return result; 
	}

	private Cell getCellInRow(int valueTofind, int row, boolean getBlocked) {
		Cell cellRepeated = null;
		for (int i = 0 ; i < 9 ; i ++){
			if (this.getValue(row, i) == valueTofind && (getBlocked || !this.getCell(row * 9 + i).isBlocked()))
				return this.getCell(row * 9 + i);
		}
		return cellRepeated;
	}

	public double getRepeatedForColumn(int valueTofind, int column) {
		double result = (double)values.stream().filter(x -> x.equalsColumn(column) && x.getValue() == valueTofind).count()==0?0:(double)values.stream().filter(x -> x.equalsColumn(column) && x.getValue() == valueTofind).count()-1;
		if (result > 0)
			this.cellsToMutate.add(this.getCellInColumn( valueTofind,column,false));
		else if (new Random().nextInt(25)==0 && this.getCellInColumn(valueTofind,column,false) != null)
			this.cellsToMutate.add(this.getCellInColumn(valueTofind,column,false));
		return result;		
	}
	
	public double getValueInQuadrant(int valueTofind, Quadrant aQuadrant) {
		int apariciones = 0 ;
		for (int i = aQuadrant.getOffset_file(); i < (3 + aQuadrant.getOffset_file()); i++)
			for (int j = aQuadrant.getOffset_column(); j < (3 + aQuadrant.getOffset_column()); j++){
				if (this.getValue(i, j)==valueTofind)
					apariciones = apariciones + 1; // || evaluateKey(new Cell(i, j), valueTofind);
					
			}
		return apariciones-1;
	}
	
	private Cell getCellInColumn(int valueTofind, int column, boolean getBlocked) {
		Cell cellRepeated = null;
		for (int i = 0 ; i < 9 ; i ++){
			if (this.getValue(i, column) == valueTofind && (getBlocked || !this.getCell(i * 9 + column).isBlocked()))
				return this.getCell(i * 9 + column);
		}
		return cellRepeated;
	}

	public double getSeenQuantity(int valueTofind) {
		int apariciones = 0 ;
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++){
				if (this.getValue(i, j)==valueTofind)
					apariciones = apariciones + 1; // || evaluateKey(new Cell(i, j), valueTofind);
			}
		return apariciones;
	}

	public int getValue(int file, int column) {
		Optional<Cell> celda = this.values.stream().filter(x -> x.getColumn() == column && x.getRow() == file).findFirst();
		return celda.get().getValue();
	}

	public int getValue(int i) {
		Optional<Cell> celda = this.values.stream().filter(x -> x.getLinearPosition() == i).findFirst();
		return celda.get().getValue();
	}

	public double evaluate() {
		this.cellsToMutate = new ArrayList<Cell>();
		return this.getEvaluationMethod().evaluate(this);
	}

	public EvaluationMethod getEvaluationMethod() {
		return evaluationMethod;
	}

	public void setEvaluationMethod(EvaluationMethod evaluationMethod) {
		this.evaluationMethod = evaluationMethod;
	}
	public Cell getCell(int position){
		return this.values.get(position);
	}
	public void setGene(int[] genes) {
		int insertionPosition = 0;
		for (int i = 0; i < genes.length; i++){
			while(this.values.get(insertionPosition).isBlocked()){
				insertionPosition ++;	
			}
			this.values.get(insertionPosition).setValue(genes[i]);
			this.values.get(insertionPosition).setGenPosition(i);
			insertionPosition ++;
		}
	}
	public Sudoku setGene(Gene[] genes) {
		int insertionPosition = 0;
		for (int i = 0; i < genes.length; i++){
			while(this.values.get(insertionPosition).isBlocked()){
				insertionPosition ++;	
			}
			this.values.get(insertionPosition).setValue((int) genes[i].getAllele());
			this.values.get(insertionPosition).setGenPosition(i);
			insertionPosition ++;
		}
		return this;
	}

	public int getFreeSpaces() {
		return 81 - (int) this.values.stream().filter(x -> x.isBlocked()).count();
	}
	
	public Cell getFirstRepeatCell(){
		return this.cellsToMutate.get(0);
	}
	public Cell getRepeatCell(int position){
		return this.cellsToMutate.get(position);
	}
	public int getCellValue(Cell aCell) {
		return aCell.getGenPosition();
	}
    private <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }	
	public int getRepeatValueSolution(Cell aCell) {
		int returnValue;
		List<Integer> lista = this.intersection(this.findValuesNotFoundInRow(aCell.getRow()),this.findValuesNotFoundInColum(aCell.getColumn()));

		if (lista.isEmpty())
			returnValue = new Random().nextInt(10);		
		else
			returnValue = lista.get(new Random().nextInt(lista.size()));
		return returnValue;
	}
	public int getCellToMutateSize(){
		return cellsToMutate.size();
	}
	private List<Integer> findValuesNotFoundInColum(int col) {
		List<Integer> returnValue = new ArrayList<>();
		for (int i = 1 ; i< 10; i++){
			if (this.getCellInColumn(i, col, true) == null )
				returnValue.add(i); // = i;
		}
		return returnValue;
	}
	
	private List<Integer> findValuesNotFoundInRow(int row) {
		List<Integer> returnValue = new ArrayList<>();
		for (int i = 1 ; i< 10; i++){
			if (this.getCellInRow(i, row, true) == null )
				returnValue.add(i); // = i;
		}
		return returnValue;
	}
	public Cell selectRandomCellToMutate() {
		int rndValue = new Random().nextInt(cellsToMutate.size());
		Cell retunCell =cellsToMutate.get(rndValue); 
		cellsToMutate.remove(rndValue);
	    return retunCell;
	}
	/*
	 * Impresión por pantalla
	 */
	public String print() {
		String valueToReturn = "+-------+-------+-------+";
		valueToReturn = valueToReturn + this.processRow(1);
		valueToReturn = valueToReturn + this.processRow(2);
		valueToReturn = valueToReturn + this.processRow(3);
		valueToReturn = valueToReturn +"\n+-------+-------+-------+";
		valueToReturn = valueToReturn + this.processRow(4);
		valueToReturn = valueToReturn + this.processRow(5);
		valueToReturn = valueToReturn + this.processRow(6);
		valueToReturn = valueToReturn +"\n+-------+-------+-------+";
		valueToReturn = valueToReturn + this.processRow(7);
		valueToReturn = valueToReturn + this.processRow(8);
		valueToReturn = valueToReturn + this.processRow(9);
		valueToReturn = valueToReturn +"\n+-------+-------+-------+";		
		return valueToReturn;

	}

	private String processRow(int rowNumber) {
		String value = "";
		switch (rowNumber) {
		case 1:
			value = value + this.getPrintRowValue(0);
			break;
		case 2:
			value = value + this.getPrintRowValue(9);
			break;
		case 3:
			value = value + this.getPrintRowValue(18);
			break;			
		case 4:
			value = value + this.getPrintRowValue(27);
			break;
		case 5:
			value = value + this.getPrintRowValue(36);
			break;
		case 6:
			value = value + this.getPrintRowValue(45);
			break;
		case 7:
			value = value + this.getPrintRowValue(54);
			break;
		case 8:
			value = value + this.getPrintRowValue(63);
			break;
		case 9:
			value = value + this.getPrintRowValue(72);
			break;			
		}
		return value;
	}
	private String getPrintRowValue(int begin){
		return "\n| " + this.getRowValue(begin, begin + 3) + "| "
				+ this.getRowValue(begin + 3, begin + 6) + "| " + this.getRowValue(begin + 6, begin +9)
				+ "|";
	}
	private String getRowValue(int offsetBegin, int offsetEnd) {
		String value = "";
		for (int i = offsetBegin; i < offsetEnd; i++) {
			value = value + Integer.toString(this.getValue(i)) + " ";
		}
		return value;
	}
}
