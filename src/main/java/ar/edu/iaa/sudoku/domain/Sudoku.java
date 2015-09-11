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
			if (new Integer(arr[i])!= 0)
				this.values.add(new Cell(i, new Integer(arr[i]),true));
			else
				this.values.add(new Cell(i, new Integer(arr[i]),false));
		}
	}

//	public Sudoku(Gene[] genes, PureEvaluate evaluationMethod) {
//		this(genes);
//		this.evaluationMethod = evaluationMethod;
//	}

	public boolean findValueInFile(int valueTofind, int file) {
		return values.stream().filter(x -> x.equalsFile(file) && x.getValue() == valueTofind).count() != 1;
	}

	public boolean findValueInColumn(int valueTofind, int column) {
		return values.stream().filter(x -> x.equalsColumn(column) && x.getValue() == valueTofind).count() != 1;
	}

	public boolean findValueInQuadrant(int valueTofind, Quadrant aQuadrant) {
		int apariciones = 0 ;
		for (int i = aQuadrant.getOffset_file(); i < (3 + aQuadrant.getOffset_file()); i++)
			for (int j = aQuadrant.getOffset_column(); j < (3 + aQuadrant.getOffset_column()); j++){
				if (this.getValue(i, j)==valueTofind)
					apariciones = apariciones + 1; // || evaluateKey(new Cell(i, j), valueTofind);
			}
		return apariciones > 1;
	}

	public String print() {
		String valueToReturn = "+-------+-------+-------+";
		valueToReturn = valueToReturn + this.processFile(1);
		valueToReturn = valueToReturn + this.processFile(2);
		valueToReturn = valueToReturn + this.processFile(3);
		valueToReturn = valueToReturn +"\n+-------+-------+-------+";
		valueToReturn = valueToReturn + this.processFile(4);
		valueToReturn = valueToReturn + this.processFile(5);
		valueToReturn = valueToReturn + this.processFile(6);
		valueToReturn = valueToReturn +"\n+-------+-------+-------+";
		valueToReturn = valueToReturn + this.processFile(7);
		valueToReturn = valueToReturn + this.processFile(8);
		valueToReturn = valueToReturn + this.processFile(9);
		valueToReturn = valueToReturn +"\n+-------+-------+-------+";		
		return valueToReturn;

	}

	private String processFile(int numberFile) {
		String value = "";
		switch (numberFile) {
		case 1:
			value = value + this.getPrintFileValue(0);
			break;
		case 2:
			value = value + this.getPrintFileValue(9);
			break;
		case 3:
			value = value + this.getPrintFileValue(18);
			break;			
		case 4:
			value = value + this.getPrintFileValue(27);
			break;
		case 5:
			value = value + this.getPrintFileValue(36);
			break;
		case 6:
			value = value + this.getPrintFileValue(45);
			break;
		case 7:
			value = value + this.getPrintFileValue(54);
			break;
		case 8:
			value = value + this.getPrintFileValue(63);
			break;
		case 9:
			value = value + this.getPrintFileValue(72);
			break;			
		}
		return value;
	}
	private String getPrintFileValue(int begin){
		return "\n| " + this.getFileValue(begin, begin + 3) + "| "
				+ this.getFileValue(begin + 3, begin + 6) + "| " + this.getFileValue(begin + 6, begin +9)
				+ "|";
	}
	private String getFileValue(int offsetBegin, int offsetEnd) {
		String value = "";
		for (int i = offsetBegin; i < offsetEnd; i++) {
			value = value + Integer.toString(this.getValue(i)) + " ";
		}
		return value;
	}

	public int getValue(int file, int column) {
		Optional<Cell> celda = this.values.stream().filter(x -> x.getColumn() == column && x.getFile() == file).findFirst();
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
