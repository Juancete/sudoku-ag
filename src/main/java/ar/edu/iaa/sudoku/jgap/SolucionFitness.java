package ar.edu.iaa.sudoku.jgap;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

import ar.edu.iaa.sudoku.domain.Sudoku;

public class SolucionFitness extends FitnessFunction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Sudoku original;

	public SolucionFitness(Sudoku target) {
		this.original = target;
	}


	@Override
	protected double evaluate(IChromosome unaSolucion) {
		Sudoku solution = original.setGene(unaSolucion.getGenes());
		return solution.evaluate(); 
	}


}
