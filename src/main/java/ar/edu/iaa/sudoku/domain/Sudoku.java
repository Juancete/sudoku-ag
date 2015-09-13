package ar.edu.iaa.sudoku.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.jgap.Gene;

public class Sudoku {
	private List<Cell> values;
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

//	public Sudoku(Gene[] genes, PureEvaluate evaluationMethod) {
//		this(genes);
//		this.evaluationMethod = evaluationMethod;
//	}

	public double getRepeatedForRow(int valueTofind, int row) {
		return (double)values.stream().filter(x -> x.equalsRow(row) && x.getValue() == valueTofind).count()==0?0:(double)values.stream().filter(x -> x.equalsRow(row) && x.getValue() == valueTofind).count()-1;
	}

	public double getRepeatedForColumn(int valueTofind, int column) {
		return (double)values.stream().filter(x -> x.equalsColumn(column) && x.getValue() == valueTofind).count()==0?0:(double)values.stream().filter(x -> x.equalsColumn(column) && x.getValue() == valueTofind).count()-1;
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
	
	public double getSeenQuantity(int valueTofind) {
		int apariciones = 0 ;
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++){
				if (this.getValue(i, j)==valueTofind)
					apariciones = apariciones + 1; // || evaluateKey(new Cell(i, j), valueTofind);
			}
		return apariciones;
	}

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

	public int getValue(int file, int column) {
		Optional<Cell> celda = this.values.stream().filter(x -> x.getColumn() == column && x.getRow() == file).findFirst();
		return celda.get().getValue();
	}

	public int getValue(int i) {
		Optional<Cell> celda = this.values.stream().filter(x -> x.getLinearPosition() == i).findFirst();
		return celda.get().getValue();
	}

	public double evaluate() {
		return this.getEvaluationMethod().evaluate(this);
	}

	public EvaluationMethod getEvaluationMethod() {
		return evaluationMethod;
	}

	public void setEvaluationMethod(EvaluationMethod evaluationMethod) {
		this.evaluationMethod = evaluationMethod;
	}

	public Sudoku setGene(Gene[] genes) {
		int insertionPosition = 0;
		for (int i = 0; i < genes.length; i++){
			while(this.values.get(insertionPosition).isBlocked()){
				insertionPosition ++;	
			}
//			if (!this.values.get(insertionPosition).isBlocked())
			this.values.get(insertionPosition).setValue((int) genes[i].getAllele());
			insertionPosition ++;
		}
		return this;
	}

	public int getFreeSpaces() {
		return 81 - (int) this.values.stream().filter(x -> x.isBlocked()).count();
	}
}
