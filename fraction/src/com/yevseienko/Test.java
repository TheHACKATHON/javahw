package com.yevseienko;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test{
	int aNumerator();
	int aDenominator();
	int bNumerator();
	int bDenominator();
	int resultNumerator();
	int resultDenominator();
}
