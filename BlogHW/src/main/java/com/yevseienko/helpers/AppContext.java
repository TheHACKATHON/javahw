package com.yevseienko.helpers;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppContext {
  private static final String PACKAGE = "com.yevseienko";
  private static ApplicationContext context;

  private AppContext() {
  }

  public static ApplicationContext get() {
    if (context == null) {
      context = new AnnotationConfigApplicationContext(PACKAGE);
    }
    return context;
  }

  public static <T> T getBean(Class<T> tClass) {
    return get().getBean(tClass);
  }
}
