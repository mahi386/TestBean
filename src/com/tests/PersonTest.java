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
	public void testGettersPerson() {
		assertTrue(beanTester.testGetters());
	}
	
	/**
	 * This test tests if setters are present for all fields in the class and the names are in the proper format
	 */
	@Test
	public void testSettersPerson() {
		assertTrue(beanTester.testSetters());
	}
}
