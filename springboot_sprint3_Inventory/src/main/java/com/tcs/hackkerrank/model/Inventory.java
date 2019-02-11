package com.tcs.hackkerrank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="Inventory")
public class Inventory extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SKUID")
	private Long skuId;
	@Column(name="PRODUCT_NAME")
	private String productName;
	@Column(name="PRODUCT_LABEL")
	private String productLabel;
	@Column(name="INVENTORY_ON_HAND")
	private Integer inventoryOnHand;
	@Column(name="MIN_QUANTITY_REQUIRED")
	private Integer minQtyReq;
	@Column(name="PRICE")
	private Double price;
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductLabel() {
		return productLabel;
	}
	public void setProductLabel(String productLabel) {
		this.productLabel = productLabel;
	}
	public Integer getInventoryOnHand() {
		return inventoryOnHand;
	}
	public void setInventoryOnHand(Integer inventoryOnHand) {
		this.inventoryOnHand = inventoryOnHand;
	}
	public Integer getMinQtyReq() {
		return minQtyReq;
	}
	public void setMinQtyReq(Integer minQtyReq) {
		this.minQtyReq = minQtyReq;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Inventory(String productName, String productLabel, Integer inventoryOnHand, Integer minQtyReq,
			Double price) {
		this.productName = productName;
		this.productLabel = productLabel;
		this.inventoryOnHand = inventoryOnHand;
		this.minQtyReq = minQtyReq;
		this.price = price;
	}
	public Inventory() {
		
	}
}
