package com.beans;

public class Address {
	private String city;
	private String addressLine1;
	//a String is more appropriate to store zipcode since we can handle codes from other countries better
	private String zipcode;
	private String country;
	
	public Address(){
		this.city="Raleigh";
		this.addressLine1="NC State";
		this.zipcode = "27607";
		this.country = "US";
	}
	
	public Address(String city,String addressLine1,String zipcode,String country){
		this.city = city;
		this.addressLine1 = addressLine1;
		this.zipcode = zipcode;
		this.country = country;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
}
