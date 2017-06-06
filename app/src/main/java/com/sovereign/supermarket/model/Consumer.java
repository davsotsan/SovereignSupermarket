package com.sovereign.supermarket.model;

public class Consumer extends Actor {

	// Constructors -----------------------------------------------------------
	public Consumer() {
		super();
	}

	// Attributes ---------------------------------------------------------
	private CreditCard creditCard;
	private String address;

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
