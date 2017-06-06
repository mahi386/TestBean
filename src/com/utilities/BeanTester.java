package com.objectpartners.pojodemo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.function.Supplier;

import org.junit.Test;


/**
 * A utility class which allows for testing entity and transfer object classes.
 * This is mainly for code coverage since these types of objects are normally
 * nothing more than getters and setters. If any logic exists in the method,
 * then the get method name should be sent in as an ignored field and a custom
 * test function should be written.
 *
 * @param <T>
 *            The object type to test.
 */
public class DomainModelTest{

	final static HashMap<Class<?>, Supplier<?>> testData = new HashMap<Class<?>, Supplier<?>>();

	/*
	 * A utility class which allows for testing entity and transfer object
	 * classes. This is mainly for code coverage since these types of objects
	 * are normally getters and setters.
	 */

	static {

		/**
		 * loading all Primitives data in testData map
		 **/

		testData.put(int.class, () -> 0);
		testData.put(double.class, () -> 0.0d);
		testData.put(float.class, () -> 0.0f);
		testData.put(long.class, () -> 0l);
		testData.put(boolean.class, () -> true);
		testData.put(short.class, () -> (short) 0);
		testData.put(byte.class, () -> (byte) 0);
		testData.put(char.class, () -> (char) 0);

		testData.put(Integer.class, () -> Integer.valueOf(0));
		testData.put(Double.class, () -> Double.valueOf(0.0));
		testData.put(Float.class, () -> Float.valueOf(0.0f));
		testData.put(Long.class, () -> Long.valueOf(0));
		testData.put(Boolean.class, () -> Boolean.TRUE);
		testData.put(Short.class, () -> Short.valueOf((short) 0));
		testData.put(Byte.class, () -> Byte.valueOf((byte) 0));
		testData.put(Character.class, () -> Character.valueOf((char) 0));

		testData.put(BigDecimal.class, () -> BigDecimal.ONE);
		testData.put(Date.class, () -> new Date());

		// loading all Collection Types data in testData map

		testData.put(Set.class, () -> Collections.emptySet());
		testData.put(SortedSet.class, () -> Collections.emptySortedSet());
		testData.put(List.class, () -> Collections.emptyList());
		testData.put(Map.class, () -> Collections.emptyMap());
		testData.put(SortedMap.class, () -> Collections.emptySortedMap());

	}

	/**
	 * Calls a getter and verifies the result is what is expected.
	 *
	 * @param fieldName
	 *            The field name (used for error messages).
	 * @param getter
	 *            The get {@link Method}.
	 * @param instance
	 *            The test instance.
	 * @param expected
	 *            The expected result.
	 *
	 * @throws IllegalAccessException
	 *             if this Method object is enforcing Java language access
	 *             control and the underlying method is inaccessible.
	 * @throws IllegalArgumentException
	 *             if the method is an instance method and the specified object
	 *             argument is not an instance of the class or interface
	 *             declaring the underlying method (or of a subclass or
	 *             implementor thereof); if the number of actual and formal
	 *             parameters differ; if an unwrapping conversion for primitive
	 *             arguments fails; or if, after possible unwrapping, a
	 *             parameter value cannot be converted to the corresponding
	 *             formal parameter type by a method invocation conversion.
	 * @throws InvocationTargetException
	 *             if the underlying method throws an exception.
	 */

	// fieldName is variable in POJO class
	// getter method in POJO class
	// This method calls junit assertEquals method for Primitives and assertSame
	// method for non primitives

	private void callGetter(String fieldName, Method getter, Object instance,
			Object expected) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		final Object getResult = getter.invoke(instance);
		if (getter.getReturnType().isPrimitive()) {
			/*
			 * Calling assetEquals() here due to autoboxing of primitive to
			 * object type.
			 */
			assertEquals(fieldName + " is different", expected, getResult);
		} else {
			/*
			 * This is a normal object. The object passed in should be the
			 * exactly same object we get back.
			 */
			assertSame(fieldName + " is different", expected, getResult);
		}
	}

	
	private void callEnumValidator(Object newObject,Class<?> instance){
		assertEquals( "Enum values are different or not ", newObject.toString(), instance.getEnumConstants()[0].toString());
		
	}
	
	
//This is the starting point for class
	
private String packageName = "com.objectpartners.dtotester";
	
	Class<?>[] objectArray;
	
    public ArrayList<Object> Setup() throws Exception {
    	
    	 ArrayList<Object> classList=new ArrayList<Object>();
           objectArray = getClasses(packageName);
           for(Class<?> clazz:objectArray){
           System.out.println("class name :"+clazz.getClass());
           Class<?> clazz1 = Class.forName(clazz.getName());
			Object data = null;
			
			if(clazz.isEnum()){
				testEnumclass(clazz1);
				continue;
			}
			
		    data = clazz1.newInstance();
			classList.add(data);
           }
          
		return classList;
           
    }
    
    public Class<?>[] getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        //return classes.toArray(new Class[classes.size()]);
        return classes.toArray(new Class[classes.size()]);
    }


    public List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
	
	
	/**
	 * Creates an object for the given {@link Class}.
	 *
	 * @param fieldName
	 *            The name of the field.
	 * @param clazz
	 *            The {@link Class} type to create.
	 *
	 * @return A new instance for the given {@link Class}.
	 *
	 * @throws InstantiationException
	 *             If this Class represents an abstract class, an interface, an
	 *             array class, a primitive type, or void; or if the class has
	 *             no nullary constructor; or if the instantiation fails for
	 *             some other reason.
	 * @throws IllegalAccessException
	 *             If the class or its nullary constructor is not accessible.
	 *
	 */

	// method is returns the object this object is nothing but value inserted in
	// testData hash map
	
	private Object createObject(String fieldName, Class<?> instance)
			throws InstantiationException, IllegalAccessException {

		try {

			final Supplier<?> supplier = testData.get(instance);
			if (supplier != null) {
				return supplier.get();
			}
			//  Test the object is enum or not 
			if (instance.isEnum()) {
				return instance.getEnumConstants()[0];
			}

			return instance.newInstance();

		} catch (IllegalAccessException | InstantiationException e) {
			throw new RuntimeException("Unable to create objects for field '"+ fieldName + "'.", e);
		}
	}

	/**
	 * Returns an instance to use to test the get and set methods.
	 *
	 * @return An instance to use to test the get and set methods.
	 */

	/**
	 * Tests all the getters and setters. Verifies that when a set method is
	 * called, that the get method returns the same thing. This will also use
	 * reflection to set the field if no setter exists
	 * 
	 * @throws Exception
	 *             If an expected error occurs.
	 */
   // Test method is executed for each end every POJO test class is loaded in JUNIT
	
	@Test
	public void test() throws Exception{

	ArrayList<Object>  instance1 = Setup();
	System.out.println(instance1.size());
	Object instance = null;
	for(Object object: instance1){
		instance=object;
		testGettersAndSetters(instance);
	}
	}
	
	
	
	void testEnumclass(Class<?> instance ) throws Exception{
		/* Sort items for consistent test runs. */
		final SortedMap<String, GetterSetterPair> getterSetterMapping = new TreeMap<>();
		System.out.println(instance.getName());
		
		// for loop  is used to get the all the methods of pojo class 
		for (final Method method : instance.getDeclaredMethods()) {
			final String methodName = method.getName();
			
			System.out.println(" method name "+ methodName);
			// Ignoring the object class methods
			if (methodName.equalsIgnoreCase("wait")
					|| methodName.equalsIgnoreCase("equals")
					|| methodName.equalsIgnoreCase("toString")
					|| methodName.equalsIgnoreCase("notify")
					|| methodName.equalsIgnoreCase("notifyAll")
					|| methodName.equalsIgnoreCase("toString")
					|| methodName.equalsIgnoreCase("hashCode")
					|| methodName.equalsIgnoreCase("getClass")) {
				continue;
			}

			if (testData.containsValue(methodName)) {
				continue;
			}

			String objectName;
			if (methodName.startsWith("get") && method.getParameters().length == 0) {
				/* Found the get method for pojo class . */
				objectName = methodName.substring("get".length());

				GetterSetterPair getterSettingPair = getterSetterMapping.get(objectName);
				if (getterSettingPair == null) {
					getterSettingPair = new GetterSetterPair();
					getterSetterMapping.put(objectName, getterSettingPair);
				}
				getterSettingPair.setGetter(method);
			} else if (methodName.startsWith("set") && method.getParameters().length == 1) {
				/* Found the set method for pojo class .  */
				objectName = methodName.substring("set".length());

				GetterSetterPair getterSettingPair = getterSetterMapping.get(objectName);
				if (getterSettingPair == null) {
					getterSettingPair = new GetterSetterPair();
					getterSetterMapping.put(objectName, getterSettingPair);
				}
				getterSettingPair.setSetter(method);
			} else if (methodName.startsWith("is") && method.getParameters().length == 0) {
				/* Found the is method, which really is a get method. */
				objectName = methodName.substring("is".length());

				GetterSetterPair getterSettingPair = getterSetterMapping.get(objectName);
				if (getterSettingPair == null) {
					getterSettingPair = new GetterSetterPair();
					getterSetterMapping.put(objectName, getterSettingPair);
				}
				getterSettingPair.setGetter(method);
			}
		
	}
		
		
		/*
		 * Found all our mappings. Now call the getter and setter or set the
		 * field via reflection and call the getting it doesn't have a setter.
		 */
		for (final Entry<String, GetterSetterPair> entry : getterSetterMapping.entrySet()) {
			final GetterSetterPair pair = entry.getValue();

			final String objectName = entry.getKey();
			final String fieldName = objectName.substring(0, 1).toLowerCase()
					+ objectName.substring(1);

			if (pair.hasGetterAndSetter()) {
				/* Create an object. */
				final Object newObject = createObject(fieldName, instance);
				callEnumValidator(newObject,instance);
			} else if (pair.getGetter() != null) {
				final Object newObject = createObject(fieldName, pair.getGetter().getReturnType());
				final Field field = instance.getClass().getDeclaredField(fieldName);
				field.setAccessible(true);
				field.set(instance, newObject);
				callEnumValidator(newObject,instance);
			}
		
	}
}
	
	public void testGettersAndSetters(Object instance) throws Exception {
		/* Sort items for consistent test runs. */
		final SortedMap<String, GetterSetterPair> getterSetterMapping = new TreeMap<>();
		
		// for loop  is used to get the all the methods of pojo class 
		for (final Method method : instance.getClass().getMethods()) {
			final String methodName = method.getName();
			
			
			// Ignoring the object class methods
			if (methodName.equalsIgnoreCase("wait")
					|| methodName.equalsIgnoreCase("equals")
					|| methodName.equalsIgnoreCase("toString")
					|| methodName.equalsIgnoreCase("notify")
					|| methodName.equalsIgnoreCase("notifyAll")
					|| methodName.equalsIgnoreCase("toString")
					|| methodName.equalsIgnoreCase("hashCode")
					|| methodName.equalsIgnoreCase("getClass")) {
				continue;
			}

			if (testData.containsValue(methodName)) {
				continue;
			}

			String objectName;
			if (methodName.startsWith("get") && method.getParameters().length == 0) {
				/* Found the get method for pojo class . */
				objectName = methodName.substring("get".length());

				GetterSetterPair getterSettingPair = getterSetterMapping.get(objectName);
				if (getterSettingPair == null) {
					getterSettingPair = new GetterSetterPair();
					getterSetterMapping.put(objectName, getterSettingPair);
				}
				getterSettingPair.setGetter(method);
			} else if (methodName.startsWith("set") && method.getParameters().length == 1) {
				/* Found the set method for pojo class .  */
				objectName = methodName.substring("set".length());

				GetterSetterPair getterSettingPair = getterSetterMapping.get(objectName);
				if (getterSettingPair == null) {
					getterSettingPair = new GetterSetterPair();
					getterSetterMapping.put(objectName, getterSettingPair);
				}
				getterSettingPair.setSetter(method);
			} else if (methodName.startsWith("is") && method.getParameters().length == 0) {
				/* Found the is method, which really is a get method. */
				objectName = methodName.substring("is".length());

				GetterSetterPair getterSettingPair = getterSetterMapping.get(objectName);
				if (getterSettingPair == null) {
					getterSettingPair = new GetterSetterPair();
					getterSetterMapping.put(objectName, getterSettingPair);
				}
				getterSettingPair.setGetter(method);
			}
		}
		

		/*
		 * Found all our mappings. Now call the getter and setter or set the
		 * field via reflection and call the getting it doesn't have a setter.
		 */
		for (final Entry<String, GetterSetterPair> entry : getterSetterMapping.entrySet()) {
			final GetterSetterPair pair = entry.getValue();

			final String objectName = entry.getKey();
			final String fieldName = objectName.substring(0, 1).toLowerCase()
					+ objectName.substring(1);

			if (pair.hasGetterAndSetter()) {
				/* Create an object. */
				final Class<?> parameterType = pair.getSetter()
						.getParameterTypes()[0];
				final Object newObject = createObject(fieldName, parameterType);
				pair.getSetter().invoke(instance, newObject);
				callGetter(fieldName, pair.getGetter(), instance, newObject);
			} else if (pair.getGetter() != null) {
				final Object newObject = createObject(fieldName, pair.getGetter().getReturnType());
				final Field field = instance.getClass().getDeclaredField(fieldName);
				field.setAccessible(true);
				field.set(instance, newObject);

				callGetter(fieldName, pair.getGetter(), instance, newObject);
			}
		
	}
}
}
