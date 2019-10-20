package com.yevseienko;

import org.w3c.dom.ls.LSOutput;

public class Fraction {
	private int numerator;
	private int denominator;

	public Fraction(int numerator, int denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
	}

	public int getNumerator() {
		return numerator;
	}

	public int getDenominator() {
		return denominator;
	}

	public Fraction add(Fraction f2) {
		if (this.denominator == f2.denominator) {
			int num = this.numerator + f2.numerator;
			return new Fraction(num, this.denominator);
		}
		int denominator = this.denominator * f2.denominator / nod(this.denominator, f2.denominator);
		int numerator1 = this.numerator * (denominator / this.denominator);
		int numerator2 = f2.numerator * (denominator / f2.denominator);
		return new Fraction(numerator1 + numerator2, denominator);
	}

	private int nod(int a, int b)
	{
		for (int i = a; i > 0; i--) {
			if (a % i == 0 && b % i == 0) {
				return i;
			}
		}
		return 1;
	}

	public Fraction minus(Fraction f2) {
		return null;
	}

	public Fraction multiple(Fraction f2) {
		return null;
	}

	public Fraction division(Fraction f2) {
		return null;
	}

	public double toDouble() {
		return (double) numerator / denominator;
	}

	@Override
	public String toString() {
		int intPart = numerator / denominator;
		String intPathString = "";
		String secondPart;
		if (intPart > 0) {
			numerator = numerator % denominator;
			intPathString = intPart + " ";
			if (numerator == 0) {
				secondPart = "";
			} else {
				int nod = nod(numerator % denominator, denominator);
				secondPart = (numerator % denominator) / nod + "/" + denominator / nod;
			}
		} else {
			int nod = nod(numerator, denominator);
			secondPart = numerator / nod + "/" + denominator / nod;
		}

		return intPathString + secondPart;
	}
}
