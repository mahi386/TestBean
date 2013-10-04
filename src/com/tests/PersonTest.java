package com.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.beans.Person;
import com.utilities.BeanTester;

public class PersonTest {
	private BeanTester<Person> beanTester;
	
	/**
	 * This test tests if getters are present for all fields in the class and the names are in the proper format
	 */
	@Test
	public void testGettersPerson() {
		beanTester = new BeanTester<Person>(Person.class);   
        assertTrue(beanTester.testGetters());
	}
	
	/**
	 * This test tests if setters are present for all fields in the class and the names are in the proper format
	 */
	@Test
	public void testSettersPerson() {
		beanTester = new BeanTester<Person>(Person.class);   
        assertTrue(beanTester.testSetters());
	}
}
