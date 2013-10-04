package com.beans.utilities;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Contains utility method to test that getters/setters are present for the specified bean
 * @author Prajakta
 */
public class BeanTester<T> {
	private Class<T> testClassName;
	private static Logger logger = Logger.getLogger("BeanTester");
	
	public BeanTester(Class<T> testClassName) {
        this.testClassName = testClassName;
    }
	
	/**
	 * This method tests all the getters in this class
	 */
	public boolean testGetters(){
		Field[] allFields = testClassName.getDeclaredFields();
		boolean hasGetters = true;
		for(Field aField:allFields){
			try{
				testGetterMethod(aField);
			}catch(NoSuchMethodException e){
				logger.log(Level.SEVERE, testClassName.getName() + " Issue with getter for field " + aField.getName());
				hasGetters = false;
			}
		}
		return hasGetters;
	}
	
	/**
	 * This tests if a getter method is present in the required format
	 * @param fieldName the field name of the field
	 * @throws NoSuchMethodException 
	 */
	private void testGetterMethod(Field field) throws NoSuchMethodException {
		StringBuilder fieldNameBuilder = new StringBuilder();
		String fieldName = field.getName();
		fieldNameBuilder.append("get").append(Character.toUpperCase(fieldName.charAt(0))).append(fieldName.substring(1));
		try {
			testClassName.getDeclaredMethod(fieldNameBuilder.toString());           
		} catch (NoSuchMethodException ex) {
			try {
				fieldNameBuilder.replace(0, 3, "is");
				testClassName.getDeclaredMethod(fieldNameBuilder.toString());
			} catch (NoSuchMethodException ex1) {                
				throw new NoSuchMethodException();
			}
		}
	}
	
	/**
	 * This method tests all the setters in this class
	 */
	public boolean testSetters(){
		Field[] allFields = testClassName.getDeclaredFields();
		boolean hasSetters = true;
		for(Field aField:allFields){
			try{
				testSetterMethod(aField);
			}catch(NoSuchMethodException e){
				logger.log(Level.SEVERE, testClassName.getName() + " Issue with setter for field " + aField.getName());
				hasSetters = false;
			}
		}
		return hasSetters;
	}
	
	/**
	 * This tests if a getter method name is present in the required format
	 * @param fieldName the field name of the field
	 * @throws NoSuchMethodException 
	 */
	private void testSetterMethod(Field field) throws NoSuchMethodException {
		//this method is private so field will never be null
		StringBuilder fieldNameBuilder = new StringBuilder();
		String fieldName = field.getName();
		fieldNameBuilder.append("set").append(Character.toUpperCase(fieldName.charAt(0))).append(fieldName.substring(1));
		try {
			testClassName.getDeclaredMethod(fieldNameBuilder.toString(),field.getType());           
		} catch (NoSuchMethodException ex) {
			throw new NoSuchMethodException();
		}
	}
}
