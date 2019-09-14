package com.gsp.ns.gradskiPrevoz.pricelistItem;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PricelistItemId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Column(name = "pricelistId")
	private long pricelistId;
	
	@Column(name = "ticketTypeId")
	private long ticketTypeId;
	
	public PricelistItemId(){}
	public PricelistItemId(long priceListId, long ticketTypeId){
		this.pricelistId = priceListId;
		this.ticketTypeId = ticketTypeId;
	}
	
	
	 public long getPricelistId() {
		return pricelistId;
	}
	public void setPricelistId(long priceListId) {
		this.pricelistId = priceListId;
	}
	public long getTicketTypeId() {
		return ticketTypeId;
	}
	public void setTicketTypeId(long ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}
	@Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	 
	        if (o == null || getClass() != o.getClass()) 
	            return false;
	 
	        PricelistItemId that = (PricelistItemId) o;
	        return Objects.equals(this.pricelistId, that.pricelistId) && 
	               Objects.equals(this.ticketTypeId, that.ticketTypeId);
	 }
	    @Override
	    public int hashCode() {
	        return Objects.hash(pricelistId, ticketTypeId);
	    }
}
