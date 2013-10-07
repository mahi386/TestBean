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
	
	/**
	 * This test tests if getters are present for specific fields in the class and the names are in the proper format
	 */
	@Test
	public void testIndividualGettersAddress() {
		assertTrue(beanTester.testGetterForField("zipcode"));
	}
	
	/**
	 * This test tests if setters are present for specific fields in the class and the names are in the proper format
	 */
	@Test
	public void testIndividualSettersAddress() {
		assertTrue(beanTester.testSetterForField("zipcode"));
	}
	
	/**
	 * This test tests that exception is thrown if null is passed as a field for the getter
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testPassNullToGettersAddress() {
		beanTester.testGetterForField(null);
	}
	
	/**
	 * This test tests that exception is thrown if null is passed as a field for the setter
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testPassNullToSettersAddress() {
		beanTester.testSetterForField(null);
	}
	
	/**
	 * This test tests that if invalidfield is passed as a field for the getter 
	 */
	@Test(expected=AssertionError.class)
	public void testPassInvalidFieldToGettersAddress() {
		beanTester.testGetterForField("invalid");
	}
	
	/**
	 * This test tests that invalidfield is passed as a field for the setter 
	 */
	@Test(expected=AssertionError.class)
	public void testPassInvalidToSettersAddress() {
		beanTester.testSetterForField("invalid");
	}
}
