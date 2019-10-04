package com.yevseienko;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Service {
	@Value("db.properties")
	private String properties;
	@Value("localhost")
	private String host;
	private String connectionString;

	public Service(String connectionString) {
		this.connectionString = connectionString;
		handleAnnotations(this);
	}

	static {
		handleAnnotations(null);
	}

	private static void handleAnnotations(Service object) {
		Field[] fields = Service.class.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Value.class)) {
				int modifiers = field.getModifiers();
				boolean isStatic = Modifier.isStatic(modifiers);
				try {
					if (isStatic && object == null) {
						field.set(null, field.getAnnotation(Value.class).value());
					} else if (!isStatic && object != null) {
						field.set(object, field.getAnnotation(Value.class).value());
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public String getConnectionString() {
		return connectionString;
	}

	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public String toString() {
		return String.format("%s %s %s", this.connectionString, this.properties, this.host);
	}
}