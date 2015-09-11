package ar.edu.iaa.sudoku.domain;

import java.util.ArrayList;
import java.util.List;

public class HomeQuadrants {

	private List<Quadrant> values;
	private static HomeQuadrants instance = null;

	public HomeQuadrants() {
		super();
		this.values = new ArrayList<Quadrant>();
		this.values.add(new Quadrant(1, 0, 0));
		this.values.add(new Quadrant(2, 0, 3));
		this.values.add(new Quadrant(3, 0, 6));
		this.values.add(new Quadrant(4, 3, 0));
		this.values.add(new Quadrant(5, 3, 3));
		this.values.add(new Quadrant(6, 3, 6));
		this.values.add(new Quadrant(7, 6, 0));
		this.values.add(new Quadrant(8, 6, 3));
		this.values.add(new Quadrant(9, 6, 6));
	}

	public static HomeQuadrants getInstance() {
		if (instance == null) {
			instance = new HomeQuadrants();
		}
		return instance;
	}

	public Quadrant findQuadrant(int number) {
		Quadrant aQuadrant = null;
		for (Quadrant q : this.values) {
			if (q.getNumber() == number)
				aQuadrant = q;
		}
		return aQuadrant;
	}

	public Quadrant findQuadrant(int column, int file) {
		Quadrant aQuadrant = null;
		if (column < 3) {
			if (file < 3)
				return this.findQuadrant(1);
			else if (file < 6)
				return this.findQuadrant(4);
			else if (file < 9)
				return this.findQuadrant(7);
		} else if (column < 6) {
			if (file < 3)
				return this.findQuadrant(2);
			else if (file < 6)
				return this.findQuadrant(5);
			else if (file < 9)
				return this.findQuadrant(8);
		} else {
			if (file < 3)
				return this.findQuadrant(3);
			else if (file < 6)
				return this.findQuadrant(6);
			else if (file < 9)
				return this.findQuadrant(9);
		}
		return aQuadrant;
	}

}
