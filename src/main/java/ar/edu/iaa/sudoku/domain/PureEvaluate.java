package ar.edu.iaa.sudoku.domain;


public class PureEvaluate implements EvaluationMethod {

	@Override
	public double evaluate(Sudoku aSudoku) {
		double percent = 0 ;
		int value;
		int row;
		int column;
		for (int i = 0; i<81;i++){
			value = aSudoku.getValue(i); 			
	        row = i/9;
	        column = i - row * 9;	
	        if (!(aSudoku.isValueInColumn(value, column) || aSudoku.isValueInRow(value, row) || aSudoku.isValueInQuadrant(value, HomeQuadrants.getInstance().findQuadrant(column, row))))
	        	percent = percent + 1;
		}
		return percent;
	}

}
