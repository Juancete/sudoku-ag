package ar.edu.iaa.sudoku.domain;


public class PureEvaluate implements EvaluationMethod {

	@Override
	public double evaluate(Sudoku aSudoku) {
		double percent = 0 ;
		int value;
		int file;
		int column;
		for (int i = 0; i<81;i++){
			value = aSudoku.getValue(i); 			
	        file = i/9;
	        column = i - file * 9;	
	        if (!(aSudoku.findValueInColumn(value, column) || aSudoku.findValueInFile(value, file) || aSudoku.findValueInQuadrant(value, HomeQuadrants.getInstance().findQuadrant(column, file))))
	        	percent = percent + 1;
		}
		return percent;
	}

}
