package com.gsp.ns.gradskiPrevoz.pricelistItem;


import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gsp.ns.gradskiPrevoz.pricelist.Pricelist;
import com.gsp.ns.gradskiPrevoz.ticketType.TicketType;


@Entity
public class PricelistItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PricelistItemId id;
	
	
	@JsonIgnore
	@ManyToOne( optional = false)
	@MapsId("pricelistId")
	private Pricelist pricelist;
	 
	 
	 
	 @ManyToOne( optional = false)
	 @MapsId("ticketTypeId")
	 private TicketType ticketType;
	 
	 @Column
	 private double price;
	 
	 
	 public PricelistItem(){
		 
	 }
	 
	 
	 public PricelistItem(Pricelist list, TicketType type, double price){
		 this.price = price;
		 this.pricelist = list;
		 this.ticketType = type;
		 this.id = new PricelistItemId(list.getId(),type.getId());
	 }

	public PricelistItemId getId() {
		return id;
	}

	public void setId(PricelistItemId id) {
		this.id = id;
	}





	public Pricelist getPricelist() {
		return pricelist;
	}


	public void setPricelist(Pricelist pricelist) {
		this.pricelist = pricelist;
	}


	public void setTicketType(TicketType type) {
		this.ticketType = type;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public TicketType getTicketType() {
		return ticketType;
	}
	 
}
