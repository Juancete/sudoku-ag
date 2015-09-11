package ar.edu.iaa.sudoku.commons;

import ar.edu.iaa.sudoku.domain.Sudoku;

public interface Logueable {
	
	public void setearArchivo(String nombreArchivo);
	public void escribir(Sudoku unaCombinacion, double valorFitness,int numeroDeCorrida);
	public void close();
}
