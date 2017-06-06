package com.*******.*******;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Supplier;

import org.junit.Before;
import org.junit.Test;


import java.util.List;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;

//Utility Class 
public class DomainModelTest {
	private String packageName = "**********.***********.************";
	Class<?>[] objectArray;
	@Before
    public void Setup() throws Exception {
           objectArray = getClasses(packageName);
    }

	final static HashMap<Class<?>, Supplier<?>> testData = new HashMap<Class<?>, Supplier<?>>();
	static{
	testData.put(int.class, ()->0);
	testData.put(double.class, () -> 0.0d);
        testData.put(float.class, () -> 0.0f);
        testData.put(long.class, () -> 0l);
        testData.put(boolean.class, () -> true);
        testData.put(short.class, () -> (short) 0);
        testData.put(byte.class, () -> (byte) 0);
        testData.put(char.class, () -> (char) 0);
        testData.put(String.class, ()->String.valueOf(""));
        testData.put(Integer.class, () -> Integer.valueOf(0));
        testData.put(Double.class, () -> Double.valueOf(0.0));
        testData.put(Float.class, () -> Float.valueOf(0.0f));
        testData.put(Long.class, () -> Long.valueOf(0));
        testData.put(Boolean.class, () -> Boolean.TRUE);
        testData.put(Short.class, () -> Short.valueOf((short) 0));
        testData.put(Byte.class, () -> Byte.valueOf((byte) 0));
        testData.put(Character.class, () -> Character.valueOf((char) 0));
        testData.put(Date.class, () -> new Date());
        testData.put(BigDecimal.class, () -> BigDecimal.ONE);
	}
	 
	
	@Test
    public <T> void testGettersAndSetters() throws Exception { 
        /* Sort items for consistent test runs. */
		 
        final SortedMap<String, GetterSetterPairTest> getterSetterMapping = new TreeMap<>();
        for(Class<?> clazz:objectArray){
        	final T instance; 
        	
        	if(clazz.getName().contains("Test")){
        		continue;
        	}
        	if(clazz.isEnum()){
        		//instance = (T) clazz.getEnumConstants()[0];
        		continue;
        	}
        	else {
        		instance = (T) Class.forName(clazz.getName()) ;
        	}
        for (final Method method : Class.forName(clazz.getName()).getMethods()){
            final String methodName = method.getName();
            if (testData.containsValue(methodName)) {
                continue;
            }
            if(methodName.equalsIgnoreCase("equals")){
            	instance.equals(null);
            	instance.equals(instance);
            	instance.equals(getClass());
            }
            if(methodName.equalsIgnoreCase("toString"))
            	instance.toString();
            if(methodName.equalsIgnoreCase("hashCode"))
            	instance.hashCode();
            String objectName;
            if (methodName.startsWith("get") && method.getParameters().length == 0) {
                objectName = methodName.substring(3);
                GetterSetterPairTest getterSettingPair = getterSetterMapping.get(objectName);
                if (getterSettingPair == null) {
                    getterSettingPair = new GetterSetterPairTest();
                    getterSetterMapping.put(objectName, getterSettingPair);
                }
                getterSettingPair.setGetter(method);
            } else if (methodName.startsWith("set") && method.getParameters().length == 1) {
                objectName = methodName.substring(3);
                GetterSetterPairTest getterSettingPair = getterSetterMapping.get(objectName);
                if (getterSettingPair == null) {
                    getterSettingPair = new GetterSetterPairTest();
                    getterSetterMapping.put(objectName, getterSettingPair);
                }
                getterSettingPair.setSetter(method);
            } else if (methodName.startsWith("is") && method.getParameters().length == 0) {
                objectName = methodName.substring(2);
                GetterSetterPairTest getterSettingPair = getterSetterMapping.get(objectName);
                if (getterSettingPair == null) {
                    getterSettingPair = new GetterSetterPairTest();
                    getterSetterMapping.put(objectName, getterSettingPair);
                }
                getterSettingPair.setGetter(method);
            }
        }
        
        for (final Entry<String, GetterSetterPairTest> entry : getterSetterMapping.entrySet()) {
            final GetterSetterPairTest pair = entry.getValue();
            final String objectName = entry.getKey();
            final String fieldName = objectName.substring(0, 1).toLowerCase() + objectName.substring(1);
            if (pair.hasGetterAndSetter() && !(fieldName.equalsIgnoreCase("class"))) {
                final Class<?> parameterType = pair.getSetter().getParameterTypes()[0];
                final Object newObject = createObject(fieldName, parameterType);
                try {
                	pair.getSetter().invoke(instance, newObject);  
                } catch(IllegalArgumentException e) {
                	e.printStackTrace();
                }
                callGetter(fieldName, pair.getGetter(), instance, newObject);
            } else if (pair.getGetter() != null && !(fieldName.equalsIgnoreCase("class"))) {
               // Object is immutable Used reflection to set object and verify that same object is returned when calling the getter.
                final Object newObject = createObject(fieldName, pair.getGetter().getReturnType());
                final Field field = instance.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(instance, newObject);
                callGetter(fieldName, pair.getGetter(), instance, newObject);
            }
        }
    }
}
	private void callGetter(String fieldName, Method getter, Object instance, Object expected)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final Object getResult = getter.invoke(instance, expected);
        if (getter.getReturnType().isPrimitive()) {
            //Calling assetEquals() here due to auto-boxing of primitive to object type.
            assertEquals(fieldName + " is different", expected, getResult);
        } else {
            //This is a normal object. The object passed in should be the exactly same object we get back.
            assertSame(fieldName + " is different", expected, getResult);
        }
    }
	
	private Object createObject(String fieldName, Class<?> clazz)
            throws InstantiationException, IllegalAccessException {

      try {      
          final Supplier<?> supplier = testData.get(clazz);
          if (supplier != null) {
        	  System.out.println(supplier.get());
              return supplier.get();
          }
  
          if (clazz.isEnum()) {
              return clazz.getEnumConstants()[0];
          }
          
          return clazz.newInstance();        
        } 
      catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException("Unable to create objects for field '" + fieldName + "'.", e);
        }
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
    

	
}

