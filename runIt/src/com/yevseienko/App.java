package com.yevseienko;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class App {
	public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
		Class<?> appClass = Class.forName("com.yevseienko.App");
		Constructor<?> constructor = appClass.getDeclaredConstructor();
		for (Method method : appClass.getDeclaredMethods()) {
			if (method.isAnnotationPresent(RunIt.class)) {
				RunIt annotation = method.getAnnotation(RunIt.class);
				int modifiers = method.getModifiers();
				boolean isStatic = Modifier.isStatic(modifiers);
				method.invoke(isStatic ? null : constructor.newInstance(), annotation.a(), annotation.b());
			}
		}
	}

	@RunIt(a = 2, b = 5)
	public void add(int a, int b) {
		System.out.printf("%d + %d = %d\n", a, b, a + b);
	}

	@RunIt(a = 5, b = 5)
	public static void multi(int a, int b) {
		System.out.printf("%d * %d = %d\n", a, b, a * b);
	}
}

