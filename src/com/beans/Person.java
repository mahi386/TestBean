package com.beans;

public class Person {
	private String name;
	private Address address;
	private final int count = 0;
	
	public Person(){
		this.name = "Prajakta";
		this.address = new Address();
	}
	
	public Person(String name, Address address){
		this.name = name;
		this.address = address;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public int getCount() {
		return count;
	}
}
