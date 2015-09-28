package ar.edu.iaa.sudoku.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestMutation {

	private Sudoku target;
	private int[] chromosome;

	@Before
	public void init() {
		this.target = new Sudoku(
				"[0 0 6 4 2 8 0 0 0 4 5 0 1 7 6 0 3 2 0 8 7 3 9 5 0 4 1 0 9 3 5 8 0 7 0 0 0 4 0 2 0 7 0 9 3 7 2 0 9 0 0 5 6 0 5 6 8 0 3 4 2 0 9 0 1 0 8 0 0 3 7 6 0 7 0 6 1 0 4 8 0]");
		target.setEvaluationMethod(new PureEvaluate());
		chromosome = new int[31];
		chromosome[0] = 6;
		chromosome[1] = 3;
		chromosome[2] = 9;
		chromosome[3] = 5;
		chromosome[4] = 7;
		chromosome[5] = 3;
		chromosome[6] = 8;
		chromosome[7] = 2;
		chromosome[8] = 6;
		chromosome[9] = 6;
		chromosome[10] = 1;
		chromosome[11] = 2;
		chromosome[12] = 4;
		chromosome[13] = 8;
		chromosome[14] = 5;
		chromosome[15] = 6;
		chromosome[16] = 1;
		chromosome[17] = 1;
		chromosome[18] = 4;
		chromosome[19] = 3;
		chromosome[20] = 8;
		chromosome[21] = 7;
		chromosome[22] = 1;
		chromosome[23] = 9;
		chromosome[24] = 4;
		chromosome[25] = 5;
		chromosome[26] = 2;
		chromosome[27] = 3;
		chromosome[28] = 2;
		chromosome[29] = 9;
		chromosome[30] = 5;

		target.setGene(chromosome);
		target.evaluate();
	}

	@Test
	public void hay31LugaresLibres() {
		Assert.assertEquals(31, target.getFreeSpaces());
	}
	@Test
	public void paraLaPrimerFilaFaltaEl1() {
		Assert.assertEquals(1, target.getRepeatValueSolution(target.getFirstRepeatCell()));
	}
	
	@Test
	public void elPrimerRepetidoEstaEnLaPosicion0(){
		Assert.assertEquals(0, target.getCellValue(target.getFirstRepeatCell()));
	}
	
	@Test
	public void mutoElPrimeroYElPrimerRepetidoEstaEnLaPosicion5(){
		chromosome[0]=1;
		target.setGene(chromosome);
		target.evaluate();
		Assert.assertEquals(5, target.getCellValue(target.getFirstRepeatCell()));
	}
	@Test
	public void elCompletoDa5000(){
		chromosome[0]=1;
		chromosome[5]=9;
		target.setGene(chromosome);
		Assert.assertEquals(5000.0, target.evaluate());
	}	
	@Test
	public void modificoFila3Col0ParaQueReviente(){
		chromosome[0]=1;
		chromosome[5]=9;
		chromosome[9]=1;
		target.setGene(chromosome);
		target.evaluate();
		Assert.assertEquals(0, target.getFirstRepeatCell().getColumn());
		Assert.assertEquals(3, target.getFirstRepeatCell().getRow());
		Assert.assertEquals(6, target.getRepeatValueSolution(target.getFirstRepeatCell()));
	}	
	@Test
	public void modificoFila3Col7ParaQueReviente(){
		chromosome[0]=1;
		chromosome[5]=9;
		chromosome[11]=8;
		target.setGene(chromosome);
		target.evaluate();
		Assert.assertEquals(7, target.getFirstRepeatCell().getColumn());
		Assert.assertEquals(3, target.getFirstRepeatCell().getRow());
		Assert.assertEquals(2, target.getRepeatValueSolution(target.getFirstRepeatCell()));
	}	
	@Test
	public void creoUnaCeldaPosLineal34(){
		Cell celda = new Cell(34,1,false);
		Assert.assertEquals(7, celda.getColumn());
		Assert.assertEquals(3, celda.getRow());		
	}
	@Test
	public void modificoParaQuePifieLaColumna(){
		chromosome[0]=1;
		chromosome[5]=9;
		chromosome[9]=1;
		chromosome[10]=6;
		target.setGene(chromosome);
		target.evaluate();
		Assert.assertEquals(0, target.getFirstRepeatCell().getColumn());
		Assert.assertEquals(0, target.getFirstRepeatCell().getRow());
		Assert.assertEquals(6, target.getRepeatValueSolution(target.getFirstRepeatCell()));
	}	
}
