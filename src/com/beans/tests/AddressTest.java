package com.beans.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.beans.Address;
import com.beans.utilities.BeanTester;

public class AddressTest {
private BeanTester<Address> beanTester;
	
	/**
	 * This test tests if getters are present for all fields in the class and the names are in the proper format
	 */
	@Test
	public void testGettersAddress() {
		beanTester = new BeanTester<Address>(Address.class);   
        assertTrue(beanTester.testGetters());
	}
	
	/**
	 * This test tests if getters are present for all fields in the class and the names are in the proper format
	 */
	@Test
	public void testSettersAddress() {
		beanTester = new BeanTester<Address>(Address.class);   
        assertTrue(beanTester.testSetters());
	}
	
}
