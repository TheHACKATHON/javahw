package com.yevseienko;

import java.util.function.BinaryOperator;

@Test(methodName = "add", aNumerator = 15, aDenominator = 15, bNumerator = 4, bDenominator = 18, resultNumerator = 110, resultDenominator = 90)
public class Fraction {
	private int numerator;
	private int denominator;

	public Fraction(int numerator, int denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
	}

	// наименьший общий делитель
	private int nod(int a, int b) {
		for (int i = a; i > 0; i--) {
			if (a % i == 0 && b % i == 0) {
				return i;
			}
		}
		return 1;
	}

	private Fraction func(Fraction f2, BinaryOperator<Integer> main) {
		if (this.denominator == f2.denominator) {
			int num = main.apply(this.numerator, f2.numerator);
			return new Fraction(num, this.denominator);
		}
		int denominator = this.denominator * f2.denominator / nod(this.denominator, f2.denominator);
		int numerator1 = this.numerator * (denominator / this.denominator);
		int numerator2 = f2.numerator * (denominator / f2.denominator);
		return new Fraction(main.apply(numerator1, numerator2), denominator);
	}

	public Fraction add(Fraction f2) {
		return func(f2, Integer::sum);
	}

	public Fraction minus(Fraction f2) {
		return func(f2, (a, b) -> a - b);
	}

	public Fraction multiple(Fraction f2) {
		return func(f2, (a, b) -> a * b);
	}

	public Fraction division(Fraction f2) {
		int tmp = f2.numerator;
		f2.numerator = f2.denominator;
		f2.denominator = tmp;
		return multiple(f2);
	}

	@Override
	public String toString() {
		int num = numerator;
		int denom = denominator;

		int intPart = num / denom;
		String intPathString = "";
		String secondPart;
		if (intPart > 0) {
			num = num % denom;
			intPathString = intPart + " ";
			if (num == 0) {
				secondPart = "";
			} else {
				int nod = nod(num % denom, denom);
				secondPart = (num % denom) / nod + "/" + denom / nod;
			}
		} else {
			int nod = nod(num, denom);
			secondPart = num / nod + "/" + denom / nod;
		}

		return intPathString + secondPart;
	}

	/*private void cut(){
		int nod = nod(numerator,denominator);
		numerator = numerator/nod;
		denominator = denominator/nod;
	}*/

	@Override
	public boolean equals(Object obj) {
		// в toString дроби нормализуются, такое сравнение эффективней чем просто сравнивать числитель и знаминатель
		return this.toString().equals(obj.toString());
	}
}
