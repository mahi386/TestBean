package com.tests;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.beans.Address;
import com.utilities.BeanTester;

public class AddressTest {
	private static BeanTester<Address> beanTester;
	
	@BeforeClass
	public static void initializeBeanTester() {
		beanTester = new BeanTester<Address>(Address.class);   
	}
	
	/**
	 * This test tests if getters are present for all fields in the class and the names are in the proper format
	 */
	@Test
	public void testGettersAddress() {
		assertTrue(beanTester.testGetters());
	}
	
	/**
	 * This test tests if setters are present for all fields in the class and the names are in the proper format
	 */
	@Test
	public void testSettersAddress() {
		assertTrue(beanTester.testSetters());
	}
	
}
