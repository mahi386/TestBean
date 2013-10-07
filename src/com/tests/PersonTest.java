package com.tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import com.beans.Person;
import com.utilities.BeanTester;

public class PersonTest {
	private static BeanTester<Person> beanTester;
	
	@BeforeClass
	public static void initializeBeanTester() {
		beanTester = new BeanTester<Person>(Person.class);   
	}
	
	/**
	 * This test tests if getters are present for all fields in the class and the names are in the proper format
	 */
	@Test
	public void testAllGettersPerson() {
		assertTrue(beanTester.testGetters());
	}
	
	/**
	 * This test tests if setters are present for all fields in the class and the names are in the proper format
	 */
	@Test
	public void testAllSettersPerson() {
		assertTrue(beanTester.testSetters());
	}
	
	/**
	 * This test tests if getters are present for specific fields in the class and the names are in the proper format
	 */
	@Test
	public void testIndividualGettersPerson() {
		assertTrue(beanTester.testGetterForField("name"));
	}
	
	/**
	 * This test tests if setters are present for specific fields in the class and the names are in the proper format
	 */
	@Test
	public void testIndividualSettersPerson() {
		assertTrue(beanTester.testSetterForField("name"));
	}
	
	/**
	 * This test tests that exception is thrown if null is passed as a field for the getter
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testPassNullToGettersPerson() {
		beanTester.testGetterForField(null);
	}
	
	/**
	 * This test tests that exception is thrown if null is passed as a field for the setter
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testPassNullToSettersPerson() {
		beanTester.testSetterForField(null);
	}
	
	/**
	 * This test tests that if invalidfield is passed as a field for the getter 
	 */
	@Test(expected=AssertionError.class)
	public void testPassInvalidFieldToGettersPerson() {
		beanTester.testGetterForField("invalid");
	}
	
	/**
	 * This test tests that invalidfield is passed as a field for the setter 
	 */
	@Test(expected=AssertionError.class)
	public void testPassInvalidToSettersPerson() {
		beanTester.testSetterForField("invalid");
	}
	
	/**
	 * This test tests that final fields cannot be set with a value in the setter
	 */
	@Test(expected=AssertionError.class)
	public void testPassFinalFieldToSettersPerson() {
		beanTester.testSetterForField("count");
	}
}
