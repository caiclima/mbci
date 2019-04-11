package br.com.mbci.utils.impls;

import java.lang.reflect.Method;

public abstract class ObjectConverter {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends Enum> T getNullSafeEnum(Object value, Class<T> clazz, boolean ordinal) throws Exception {
		if (value == null) {
			return null;
		}

		if (ordinal) {
			return (T) enumValues(clazz)[Integer.parseInt(value.toString())];
		} else {
			return (T) Enum.valueOf(clazz, value.toString());
		}
	}

	public static <T> T getNullSafeValue(Object value, Class<T> clazz) throws Exception {
		return getNullSafeValue(value, clazz, false);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getNullSafeValue(Object value, Class<T> clazz, boolean zeroValues) throws Exception {

		if (value == null && clazz == Boolean.class) {
			return (T) Boolean.FALSE;
		} else if (value == null) {
			if (zeroValues && Number.class.isAssignableFrom(clazz)) {
				return clazz.getConstructor(String.class).newInstance("0");
			}
			if (zeroValues && String.class.isAssignableFrom(clazz)) {
				return (T) "";
			}
			return null;
		} else if (Number.class.isAssignableFrom(clazz)) {
			return clazz.getConstructor(String.class).newInstance(value.toString());
		} else if (Boolean.class.isAssignableFrom(clazz)) {
			return (T) Boolean.valueOf(!value.toString().equals("0"));
		} else if (Character.class.isAssignableFrom(clazz)) {
			return (T) Character.valueOf(value.toString().charAt(0));
		} else if (String.class.isAssignableFrom(clazz)) {
			return (T) value.toString();
		} else {
			return (T) value;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Object[] enumValues(Class enumClass) throws Exception {
		Method valuesMethod = enumClass.getMethod("values");
		return (Object[]) valuesMethod.invoke(null);
	}
}
