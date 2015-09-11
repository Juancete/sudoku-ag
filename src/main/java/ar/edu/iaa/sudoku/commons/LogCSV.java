package ar.edu.iaa.sudoku.commons;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import ar.edu.iaa.sudoku.domain.Sudoku;
import ar.edu.iaa.sudoku.exeptions.BussinessException;

public class LogCSV implements Logueable{

	CSVPrinter impresora;
	
	@Override
	public void escribir(Sudoku unaCombinacion, double valorFitness,
			int numeroDeCorrida) {
		this.escribirEnArchivo(new String[] { Integer.toString(numeroDeCorrida),
//				unaCombinacion.getColores().get(0).toString(),
//				unaCombinacion.getColores().get(1).toString(),
//				unaCombinacion.getColores().get(2).toString(),
//				unaCombinacion.getColores().get(3).toString(),
				Double.toString(valorFitness) });
	}

	@Override
	public void close() {
		try {
			this.impresora.close();
		} catch (IOException e) {
			throw new BussinessException("Imposible cerrar el archivo de Log");
		}
	}

	private void escribirEnArchivo(String[] valor) {
		try {
			this.impresora.printRecord(valor);
		} catch (IOException e) {
			throw new BussinessException("Imposible escribir el archivo de Log");
		}
	}

	@Override
	public void setearArchivo(String nombreArchivo) {
		CSVFormat formato = CSVFormat.RFC4180;
		try {
			this.impresora = new CSVPrinter(new PrintWriter(nombreArchivo
					+ ".csv"), formato);
			// impresión de header

			this.escribirEnArchivo(new String[] { "NroCorrida", "ColorPos1", "ColorPos2",
					"ColorPos3", "ColorPos4", "Aptitud" });
		} catch (Exception e) {
			throw new BussinessException("Imposible crear el archivo de Log");
		}
		
	}
}
