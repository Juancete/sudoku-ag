package ar.edu.iaa.sudoku.jgap;

import ar.edu.iaa.sudoku.domain.Sudoku;
import ar.edu.iaa.sudoku.exeptions.BussinessException;

import org.jgap.Configuration;

import java.util.Properties;

public class ConfigurationFactory {
	private ConfigurationBuilder builder;
	private final Properties properties;
	private final Sudoku target;

	public ConfigurationFactory(Properties properties, Sudoku target) {
		this.properties = properties;
		this.target = target;
	}

	public Configuration getConfiguration(double runRate){
		this.builder = new ConfigurationBuilder();
		this.builder.setSudokuTarget(this.target);
		int poblacionSize = Integer.parseInt(properties.getProperty("poblacionSize"));

		this.setSelector();
		this.builder.SetCrossoverOperator();
		this.setMutation(properties, runRate);
		return this.builder.SetFitnessFunction(new SolucionFitness(target))
			.SetSampleChromosome(target)
			.SetPopulationSize(poblacionSize)
			.Build();
	}

	private void setMutation(Properties properties, double runRate) {
		String mutation = properties.getProperty("mutacion");
		String mutationRate = properties.getProperty("indiceDeMutacion");
		if(mutation == null) return;

		if(mutation.equals("simple")){
			if(!mutationRate.isEmpty()) this.builder.SetMutationOperator(Integer.parseInt(mutationRate));
			else this.builder.SetMutationOperator();
		}
		if(mutation.equals("asc")) this.builder.SetCrescentMutationOperator(Integer.parseInt(mutationRate), runRate);
		if(mutation.equals("desc")) this.builder.SetDecrescentMutationOperator(Integer.parseInt(mutationRate), runRate);
	}

	private void setSelector() {
		String selector = properties.getProperty("selector");
		if(selector.equals("torneo")) builder.SetTournamentSelector();
		if(selector.equals("ranking")) builder.SetRankingSelector();

		if(!(selector.equals("ranking") || selector.equals("torneo")))
			throw new BussinessException("unknown selector: " + selector);
	}

}

