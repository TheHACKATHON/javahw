package com.yevseienko.branches;

public class Unit extends Branch {

	public Unit(String name) {
		super(name);
		type = "Подразделение";
	}

	@Override
	public void addChildren(Branch... branches) {
		throw new RuntimeException("Подразделение не составной объект");
	}
}
