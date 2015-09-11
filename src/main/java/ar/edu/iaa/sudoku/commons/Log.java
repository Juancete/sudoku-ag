package ar.edu.iaa.sudoku.commons;

import ar.edu.iaa.sudoku.domain.Sudoku;

public class Log {

	Logueable estrategiaDeLogueo;

	public Log(String nombreDelArchivo) {
		if (nombreDelArchivo == null)
			this.estrategiaDeLogueo = new LogEmpty();
		else
			this.estrategiaDeLogueo = new LogCSV();
		this.estrategiaDeLogueo.setearArchivo(nombreDelArchivo);
	}

	public void close(){
		this.estrategiaDeLogueo.close();
	}
	public void escribir(Sudoku unaCombinacion, double valorFitness,
			int numeroDeCorrida){
		this.estrategiaDeLogueo.escribir(unaCombinacion, valorFitness, numeroDeCorrida);
	}

}
