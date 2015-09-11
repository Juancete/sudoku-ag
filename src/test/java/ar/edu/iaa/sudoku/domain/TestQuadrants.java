package ar.edu.iaa.sudoku.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestQuadrants {

	@Before
	public void init(){
		
	}
	
	@Test
	public void paraLaFilaCeroyLaColumna3DevuelveCuadranteNueve(){
		Quadrant aQuadrant = HomeQuadrants.getInstance().findQuadrant(3, 0);
		Assert.assertEquals(2,aQuadrant.getNumber());
	}
}
