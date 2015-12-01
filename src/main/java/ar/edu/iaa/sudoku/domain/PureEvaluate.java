package ar.edu.iaa.sudoku.domain;


public class PureEvaluate implements EvaluationMethod {

	@Override
	public double evaluate(Sudoku aSudoku) {
		double percent = 5000;

		int value;
		int row;
		int column;
		for (int i = 0; i<81;i++){
			value = aSudoku.getValue(i); 			
	        row = i/9;
	        column = i - row * 9;
	        percent-=aSudoku.getValueInQuadrant(value, HomeQuadrants.getInstance().findQuadrant(column, row)) * 20;
		}
		
		//Checkea repetidos de columna por toda la primera fila
		for(int j=0;j<9;j++){
			for(int i=1;i<=9;i++){
				percent=percent-aSudoku.getRepeatedForRow(i, j) * 50;
				//percent-=aSudoku.getRepeatedForColumn(i, j);
			}
    	}

		//Checkea repetidos de fila por toda la primera columna
		for(int j=0;j<81;j=j+9){
			for(int i=1;i<=9;i++)
				percent-=aSudoku.getRepeatedForColumn(i, j/9) * 50;	        	
    	}
//		//Checkea que cada número solo esté repetido 9 veces en todo el sudoku.
//		for(int j=1;j<=9;j++){
//    		percent-=aSudoku.getSeenQuantity(j)==9?0:30;
//    	}
		return percent;
	}

}
