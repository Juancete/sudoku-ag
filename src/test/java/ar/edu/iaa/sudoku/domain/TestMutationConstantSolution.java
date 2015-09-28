package ar.edu.iaa.sudoku.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestMutationConstantSolution {
	private Sudoku target;
	private int[] chromosome;

	@Before
	public void init() {
		this.target = new Sudoku(
				"[0 0 6 4 2 8 0 0 0 4 5 0 1 7 6 0 3 2 0 8 7 3 9 5 0 4 1 0 9 3 5 8 0 7 0 0 0 4 0 2 0 7 0 9 3 7 2 0 9 0 0 5 6 0 5 6 8 0 3 4 2 0 9 0 1 0 8 0 0 3 7 6 0 7 0 6 1 0 4 8 0]");
		target.setEvaluationMethod(new PureEvaluate());
		chromosome = new int[31];
		chromosome[0] = 5;
		chromosome[1] = 3;
		chromosome[2] = 9;
		chromosome[3] = 1;
		chromosome[4] = 7;
		chromosome[5] = 9;
		chromosome[6] = 8;
		chromosome[7] = 6;
		chromosome[8] = 2;
		chromosome[9] = 2;
		chromosome[10] = 6;
		chromosome[11] = 1;
		chromosome[12] = 4;
		chromosome[13] = 1;
		chromosome[14] = 5;
		chromosome[15] = 6;
		chromosome[16] = 8;
		chromosome[17] = 4;
		chromosome[18] = 8;
		chromosome[19] = 1;
		chromosome[20] = 3;
		chromosome[21] = 1;
		chromosome[22] = 7;
		chromosome[23] = 9;
		chromosome[24] = 4;
		chromosome[25] = 5;
		chromosome[26] = 2;
		chromosome[27] = 2;
		chromosome[28] = 5;
		chromosome[29] = 9;
		chromosome[30] = 3;

		target.setGene(chromosome);
		target.evaluate();
	}

	@Test
	public void elPrimerRepetidoEstaEnLaPosicion9() {
		Assert.assertEquals(9, target.getCellValue(target.getFirstRepeatCell()));
	}

}
