package ar.edu.iaa.sudoku.runnable;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import ar.edu.iaa.sudoku.jgap.ConfigurationFactory;

import org.jgap.Configuration;
import org.jgap.Genotype;
import org.jgap.IChromosome;

import ar.edu.iaa.sudoku.commons.Log;
import ar.edu.iaa.sudoku.domain.PureEvaluate;
import ar.edu.iaa.sudoku.domain.Sudoku;

public class Main {

	public static void main(String[] args) throws Exception {
		int maximasEvoluciones;
		String archivoLog = "";
		Sudoku sudokuAResolver = null;

		System.out.println("Sudoku V1.0");
		if (args.length == 1) { // si hay 1 parámetro
			sudokuAResolver = new Sudoku(args[0]);
		} else {
			System.out
					.println("Error en los parámetros. Debe escribir por ejemplo: sudoku string");
			return;
		}
		sudokuAResolver.setEvaluationMethod(new PureEvaluate());
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("config.properties");
			prop.load(input);
		} catch (Exception e) {
			System.out
					.println("Error en la lectura del archivo de configuración config.properties.");
			return;
		}
		maximasEvoluciones = Integer.parseInt(prop
				.getProperty("maximasEvoluciones"));
		archivoLog = prop.getProperty("archivoDeLog");

		ConfigurationFactory configurationFactory = new ConfigurationFactory(
				prop, sudokuAResolver);
		Configuration conf = configurationFactory.getConfiguration(0);

		Genotype genotipe = Genotype.randomInitialGenotype(conf);
		IChromosome bestSolutionSoFar = null;

		int i;
		Log logueador = new Log(archivoLog);
		System.out.println("\n");
		for (i = 0; i < maximasEvoluciones; i++) {

//			for(int j=0;j<15;j++)
//				System.out.print("\b");
			Configuration.reset();

			genotipe = new Genotype(configurationFactory.getConfiguration(i
					/ (double) maximasEvoluciones), genotipe.getPopulation());

			genotipe.evolve();
			bestSolutionSoFar = genotipe.getFittestChromosome();
			Sudoku mejor = sudokuAResolver.setGene(bestSolutionSoFar.getGenes());

			//logueador.escribir(mejor, bestSolutionSoFar.getFitnessValue(), i);

			if (mejor.evaluate()==81) {
				System.out.println("\nGané en la evolución " + i
						+ "!!!!!! ");
				//mejor.print();
				break;
			}
			String value = "\nFittness de la mejor solución: " + bestSolutionSoFar.getFitnessValue();
			value = value + "\nIteración número: " + i + "\n";
			value = value + mejor.print();
			System.out.print(value);
			

		}
		if (i == maximasEvoluciones)
			System.out.println("perdí :(");
		logueador.close();
	}

}
