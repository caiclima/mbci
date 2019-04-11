package br.com.mbci.utils.impls;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReflectionUtils implements Serializable {

	private static final long serialVersionUID = 1L;

	private ReflectionUtils(){
		
	}
	
	@SuppressWarnings("rawtypes")
	public static Class<?> getClass(Type type) {

		if (type instanceof Class) {
			return (Class) type;
		} else if (type instanceof ParameterizedType) {
			return getClass(((ParameterizedType) type).getRawType());
		} else if (type instanceof GenericArrayType) {
			Type componentType = ((GenericArrayType) type).getGenericComponentType();
			Class<?> componentClass = getClass(componentType);

			if (componentClass != null) {
				return Array.newInstance(componentClass, 0).getClass();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public static List<Class<?>> getTypeArguments(Class<?> baseClass, Class<?> childClass) {

		Map<Type, Type> resolvedTypes = new HashMap<Type, Type>();
		Type type = childClass;

		while (!getClass(type).equals(baseClass)) {

			if (type instanceof Class) {
				type = ((Class) type).getGenericSuperclass();
			} else {
				ParameterizedType parameterizedType = (ParameterizedType) type;
				Class<?> rawType = (Class) parameterizedType.getRawType();

				Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
				TypeVariable<?>[] typeParameters = rawType.getTypeParameters();

				for (int i = 0; i < actualTypeArguments.length; i++) {
					resolvedTypes.put(typeParameters[i], actualTypeArguments[i]);
				}

				if (!rawType.equals(baseClass)) {
					type = rawType.getGenericSuperclass();
				}
			}
		}

		Type[] actualTypeArguments;

		if (type instanceof Class) {
			actualTypeArguments = ((Class) type).getTypeParameters();
		} else {
			actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
		}

		List<Class<?>> typeArgumentsAsClasses = new ArrayList<Class<?>>();

		for (Type baseType : actualTypeArguments) {

			while (resolvedTypes.containsKey(baseType)) {
				baseType = resolvedTypes.get(baseType);
			}

			typeArgumentsAsClasses.add(getClass(baseType));
		}

		return typeArgumentsAsClasses;
	}

	public static Type[] getTypeArguments(Field field) {
		return getTypeArguments(field.getGenericType());
	}

	public static Type[] getTypeArguments(Type type) {
		ParameterizedType parameterizedType = (ParameterizedType) type;
		return parameterizedType.getActualTypeArguments();
	}

	@SuppressWarnings("rawtypes")
	public static List<Class> getAllInterfaces(Class clazz) {
		List<Class> interfaces = new ArrayList<Class>();

		if (clazz != null) {
			interfaces.addAll(Arrays.asList(clazz.getInterfaces()));

			if (clazz.getSuperclass() != null && !clazz.getSuperclass().getName().equals("java.lang.Object")) {
				List<Class> superInterfaces = getAllInterfaces(clazz.getSuperclass());
				interfaces.addAll(superInterfaces);
			}
		}

		return interfaces;
	}

	@SuppressWarnings("rawtypes")
	public static Boolean implementsInterface(Object object, Class clazz1) {
		return getAllInterfaces(object.getClass()).contains(clazz1);
	}

	@SuppressWarnings("rawtypes")
	public static Boolean implementsInterface(Field field, Class clazz) {
		return getAllInterfaces(field.getType()).contains(clazz);
	}

	@SuppressWarnings("rawtypes")
	public static List<Field> getAllFieldsByClass(Class clazz) {

		List<Field> fields = new ArrayList<Field>();

		if (clazz != null) {
			fields.addAll(Arrays.asList(clazz.getDeclaredFields()));

			if (clazz.getSuperclass() != null) {
				fields.addAll(getAllFieldsByClass(clazz.getSuperclass()));
			}
		}

		return fields;
	}

	@SuppressWarnings("rawtypes")
	public static List<Field> getOnlyFieldsByClass(Class clazz) {

		if (clazz != null) {
			return Arrays.asList(clazz.getDeclaredFields());
		} else {
			return new ArrayList<Field>();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object getInstance(Class clazz) throws Exception {
		return clazz.getConstructor().newInstance();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object getInstance(Class clazz, Object... objects) throws Exception {
		return clazz.getConstructor().newInstance(objects);
	}

	public static Object getInstance(Field field) throws Exception {
		return getInstance(field.getType());
	}

	public static Object getInstance(Field field, Object... objects) throws Exception {
		return getInstance(field.getType(), objects);
	}

	@SuppressWarnings("rawtypes")
	public static Field getField(Object object, String fieldName) throws Exception {
		Class clazz = object instanceof Class ? ((Class) object) : object.getClass();
		return clazz.getDeclaredField(fieldName);
	}

	@SuppressWarnings("rawtypes")
	public static Class getClass(Object object, String fieldName) throws Exception {
		return getField(object, fieldName).getType();
	}

	@SuppressWarnings("rawtypes")
	public static Boolean existFieldInClass(Class clazz, String fieldName, Boolean includeSuperClass) {

		List<Field> fields = null;

		if (includeSuperClass) {
			fields = getAllFieldsByClass(clazz);
		} else {
			fields = getOnlyFieldsByClass(clazz);
		}

		for (Field field : fields) {
			if (field.getName().equals(fieldName)) {
				return Boolean.TRUE;
			}
		}

		return Boolean.FALSE;
	}

	public static Object getValue(Object object, Field field) throws Exception {

		Object value = null;

		field.setAccessible(Boolean.TRUE);

		if (object != null) {
			value = field.get(object);
		}

		field.setAccessible(Boolean.FALSE);
		return value;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void setValue(Object object, Field field, Object value) throws Exception {

		field.setAccessible(Boolean.TRUE);

		if (field.getType().isEnum() && value != null) {
			value = Enum.valueOf((Class) field.getType(), value.toString());
		}

		field.set(object, value);

		field.setAccessible(Boolean.FALSE);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object clone(Object object) throws Exception {

		Object clone = getInstance(object.getClass());

		for (Field field : getAllFieldsByClass(clone.getClass())) {

			if (!field.getName().contains("serialVersionUID")) {
				if (implementsInterface(field, Collection.class)) {

					Object value = getValue(object, field);

					if (value != null) {
						setValue(clone, field, getInstance(value.getClass()));
						for (Object object1 : (Collection) getValue(object, field)) {
							((Collection) getValue(clone, field)).add(clone(object1));
						}
					}
				} else {
					setValue(clone, field, getValue(object, field));
				}
			}
		}

		return clone;
	}
}
