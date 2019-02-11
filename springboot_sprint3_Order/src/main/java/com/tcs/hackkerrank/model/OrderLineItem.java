package com.tcs.hackkerrank.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="ORDER_LINE_ITEM")
public class OrderLineItem implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ORDER_LINE_ITEM")
	private Long orderLineItem;
	@Column(name="SKU_ID")
	private Long skuId;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="ORDER_ID")
	private Order order;
	@Column(name="ITEM_OTY")
	private Integer itemQty;
	public OrderLineItem() {
		
	}
	public OrderLineItem(Long skuId, Integer itemQty) {
		super();
		this.skuId = skuId;
		this.itemQty = itemQty;
	}
	public Long getOrderLineItem() {
		return orderLineItem;
	}
	public void setOrderLineItem(Long orderLineItem) {
		this.orderLineItem = orderLineItem;
	}
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Integer getItemQty() {
		return itemQty;
	}
	public void setItemQty(Integer itemQty) {
		this.itemQty = itemQty;
	}
}
