package com.tcs.hackkerrank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="Vendor")
public class Vendor extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="VENDOR_ID")
	private Long vendorId;
	@Column(name="VENDOR_NAME")
	private String vendorName;
	@Column(name="CONTACT_NO")
	private Long vendorContactNo;
	@Column(name="EMAIL")
	private String vendorEmail;
	@Column(name="USER_NAME")
	private String vendorUsername;
	@Column(name="ADDRESS")
	private String vendorAddress;
	public Long getVendorId() {
		return vendorId;
	}
	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public Long getVendorContactNo() {
		return vendorContactNo;
	}
	public void setVendorContactNo(Long vendorContactNo) {
		this.vendorContactNo = vendorContactNo;
	}
	public String getVendorEmail() {
		return vendorEmail;
	}
	public void setVendorEmail(String vendorEmail) {
		this.vendorEmail = vendorEmail;
	}
	public String getVendorUsername() {
		return vendorUsername;
	}
	public void setVendorUsername(String vendorUsername) {
		this.vendorUsername = vendorUsername;
	}
	public String getVendorAddress() {
		return vendorAddress;
	}
	public void setVendorAddress(String vendorAddress) {
		this.vendorAddress = vendorAddress;
	}
	// Default Constructors
	public Vendor(String vendorName, Long vendorContactNo, String vendorEmail, String vendorUsername,
			String vendorAddress) {
		this.vendorName = vendorName;
		this.vendorContactNo = vendorContactNo;
		this.vendorEmail = vendorEmail;
		this.vendorUsername = vendorUsername;
		this.vendorAddress = vendorAddress;
	}
	public Vendor() {
	
	}
}
