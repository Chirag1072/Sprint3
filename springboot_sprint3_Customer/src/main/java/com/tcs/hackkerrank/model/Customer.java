package com.tcs.hackkerrank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Customer")
public class Customer extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CUSTOMER_ID")
	private Long customerId;
	@Column(name="CUSTOMER_NAME")
	private String customerName;
	@Column (name="CUSTOMER_CONTACT")
	private Long contactNumber;
	@Column(name="ADDRESS")
	private String address;
	@Column(name="GENDER")
	private String gender;
	public Customer() {
		
	}
	public Customer( String name, Long contact, String address, String gender) {
		this.customerName=name;
		this.contactNumber=contact;
		this.address=address;
		this.gender=gender;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Long getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(Long contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
}
