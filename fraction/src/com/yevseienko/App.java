package com.yevseienko;

public class App {
	public static void main(String[] args) {
		Fraction f1 = new Fraction(15,15);
		Fraction f2 = new Fraction(4, 18);
		Fraction result = f1.add(f2);
		System.out.println(result);
	}
}

