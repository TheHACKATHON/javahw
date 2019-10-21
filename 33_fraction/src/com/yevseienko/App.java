package com.yevseienko;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class App {
	public static void main(String[] args) {
		Class<?> fractionClass = null;
		String fractionClassName = "com.yevseienko.Fraction";
		try {
			fractionClass = Class.forName(fractionClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (fractionClass == null) {
			System.out.printf("Не найдены нужные классы %s.\n", fractionClassName);
			return;
		}

		Constructor constructorFraction = null;
		try {
			Class[] arr = new Class[]{int.class, int.class};
			constructorFraction = fractionClass.getDeclaredConstructor(arr);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		if (constructorFraction == null) {
			System.out.println("Не найден нужный констуктор.");
			return;
		}

		if (!fractionClass.isAnnotationPresent(Test.class)) {
			System.out.println("Нет аннотаций для теста");
			return;
		}

		// тестовые значения в аннотации Test над Fraction
		Test annotation = fractionClass.getAnnotation(Test.class);
		try {
			Object f1 = constructorFraction.newInstance(annotation.aNumerator(), annotation.aDenominator());
			Object f2 = constructorFraction.newInstance(annotation.bNumerator(), annotation.bDenominator());
			Object expectedResult = constructorFraction.newInstance(annotation.resultNumerator(), annotation.resultDenominator());

			Method method = fractionClass.getDeclaredMethod(annotation.methodName(), fractionClass);
			System.out.printf("%s((%d, %d), (%d, %d)) ожитаемый ответ: (%d, %d)\n", annotation.methodName(),
					annotation.aNumerator(), annotation.aDenominator(), annotation.bNumerator(), annotation.bDenominator(),
					annotation.resultNumerator(), annotation.resultDenominator());

			Object result = method.invoke(f1, f2);
			if (result.equals(expectedResult)) {
				System.out.println("Тест пройден");
			} else {
				System.out.println("Тест не пройден");
			}
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
			ex.printStackTrace();
		} catch (NoSuchMethodException ex) {
			System.out.println("Не найден тестируемый метод");
		}
	}
}

