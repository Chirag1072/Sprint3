package com.tcs.hackkerrank.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Order_1")
public class Order implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ORDER_ID")
	private Long orderId;
	@Column(name="CUSTOMER_ID")
	private Long customerId;
	@Column(name="PAYMENT_CHANNEL")
	private String paymentChannel;
	@Column(name="IS_COD")
	private Boolean isCod;
	@Column(name="ORDER_STATUS")
	private String orderStatus;
	@Column(name="TOTAL_AMOUNT")
	private Double totalAmount;
	@Column(name="SHIPPING_ADDRESS")
	private String shippingAddress;
	@OneToMany
	@JoinColumn(name="ORDER_ID")
	private List<OrderLineItem> orderLineItemList;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getPaymentChannel() {
		return paymentChannel;
	}
	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}
	public Boolean getIsCod() {
		return isCod;
	}
	public void setIsCod(Boolean isCod) {
		this.isCod = isCod;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getShippingAddress() {
		return this.shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public List<OrderLineItem> getOrderLineItemList() {
		return orderLineItemList;
	}
	public void setOrderLineItemList(List<OrderLineItem> orderLineItemList) {
		this.orderLineItemList = orderLineItemList;
	}
	public Order(Long customerId, String paymentChannel, Boolean isCod, String orderStatus, Double totalAmount,
			String shippingAddress, List<OrderLineItem> orderLineItemList) {
		super();
		this.customerId = customerId;
		this.paymentChannel = paymentChannel;
		this.isCod = isCod;
		this.orderStatus = orderStatus;
		this.totalAmount = totalAmount;
		this.shippingAddress = shippingAddress;
		this.orderLineItemList = orderLineItemList;
	}
	public Order() {
		
	}
}
