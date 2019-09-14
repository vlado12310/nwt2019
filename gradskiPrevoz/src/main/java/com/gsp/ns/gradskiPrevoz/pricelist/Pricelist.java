package com.gsp.ns.gradskiPrevoz.pricelist;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gsp.ns.gradskiPrevoz.exceptions.ResourceNotFoundException;
import com.gsp.ns.gradskiPrevoz.pricelistItem.PricelistItem;
import com.gsp.ns.gradskiPrevoz.ticketType.TicketType;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Pricelist implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	
	@OneToMany(mappedBy = "pricelist", cascade = CascadeType.ALL)
	private List<PricelistItem> pricelistItems;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy", timezone="CET")
	@Column(nullable = false)
	private Date start;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy", timezone="CET")
	@Column(nullable = false)
	private Date end;
	

	public Pricelist(Long id, List<PricelistItem> items, Date start, Date end) {
		super();
		this.id = id;
		this.pricelistItems = items;
		this.start = start;
		this.end = end;

	}

	public Pricelist() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<PricelistItem> getPricelistItems() {
		return pricelistItems;
	}

	public void setPricelistItems(List<PricelistItem> items) {
		this.pricelistItems = items;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	public PricelistItem findPricelistItem(TicketType tt){
		for (PricelistItem item : pricelistItems){
			if (tt.getId() == item.getTicketType().getId()){
				return item;
			}
		}
		throw new ResourceNotFoundException();
	}

	
	

}
