package com.utilities;

import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Contains utility methods to test that getters/setters are present for the specified bean
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
	 * @return true if getters are present for all variables in this class
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
	 * @throws NoSuchMethodException if a getter for the field is not present then this exception is thrown 
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
			if(!Modifier.isFinal(aField.getModifiers())){
				try{
					testSetterMethod(aField);
				}catch(NoSuchMethodException e){
					logger.log(Level.SEVERE, testClassName.getName() + " Issue with setter for field " + aField.getName());
					hasSetters = false;
				}
			}
		}
		return hasSetters;
	}
	
	/**
	 * This tests if a getter method name is present in the required format
	 * @param fieldName the field name of the field
	 * @throws NoSuchMethodException if a  setter method is not found for this field this exception is thrown
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
	
	/**
	 * Test for a specific setter
	 * @param fieldName The field name that needs to be checked for a setter
	 * @return if setter is present for this field then true is returned
	 */
	public boolean testSetterForField(String fieldName){
		if(fieldName == null){
			throw new IllegalArgumentException();
		}
		try {
			Field aField = testClassName.getDeclaredField(fieldName);
			if(Modifier.isFinal(aField.getModifiers())){
				logger.log(Level.SEVERE, testClassName.getName() + " Final field will not have a setter " + aField.getName());
				fail(" Final field will not have a setter " + aField.getName());
			}
			testSetterMethod(aField);
		} catch (NoSuchFieldException ex) {
			logger.log(Level.SEVERE, testClassName.getName() + " Issue with setter for field. No field found by the name: " + fieldName);
			fail(" Issue with setter for field. No field found by the name: " + fieldName);
		}catch(NoSuchMethodException e){
			logger.log(Level.SEVERE, testClassName.getName() + " Issue with setter for field " + fieldName);
			fail(" Issue with setter for field " + fieldName);
		}
		return true;
	}
	
	/**
	 * Test for a specific getter
	 * @param fieldName The field name that needs to be checked for a getter
	 * @return if a getter is present for the field then a true is returned
	 */
	public boolean testGetterForField(String fieldName){
		if(fieldName == null){
			throw new IllegalArgumentException();
		}
		try {
			Field aField = testClassName.getDeclaredField(fieldName);
			testGetterMethod(aField);
		} catch (NoSuchFieldException ex) {
			logger.log(Level.SEVERE, testClassName.getName() + " Issue with getter for field. No field found by the name: " + fieldName);
			fail(" Issue with getter for field. No field found by the name:" + fieldName);
		}catch(NoSuchMethodException e){
			logger.log(Level.SEVERE, testClassName.getName() + " Issue with getter for field " + fieldName);
			fail(" Issue with getter for field " + fieldName);
		}
		return true;
	}
}
