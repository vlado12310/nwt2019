package com.gsp.ns.gradskiPrevoz.ticketType;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gsp.ns.gradskiPrevoz.pricelistItem.PricelistItem;
import com.gsp.ns.gradskiPrevoz.user.User.UserStatus;
import com.gsp.ns.gradskiPrevoz.zone.Zone;

@Entity
@Table(name = "ticketType")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TicketType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long id;
	
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private int durationInHours;

	@JsonIgnore
	@OneToMany(mappedBy = "ticketType", cascade = CascadeType.ALL)
	private List<PricelistItem> pricelistItems;
	
	//ZONE U KOJIM VAZI KARTA
	@ManyToMany
	List<Zone> zones;
	
	@Column(nullable = false)
	UserStatus requiredStatus;

	public TicketType() {
		super();
	}

	public TicketType( String name, List<PricelistItem> items, int durationInHours, List<Zone> zones, UserStatus requiredStatus) {
		super();
		
		this.name = name;
		this.zones = zones;
		this.pricelistItems = items;
		this.durationInHours = durationInHours;
		this.requiredStatus = requiredStatus;
	}
	
	

	public int getDurationInHours() {
		return durationInHours;
	}

	public void setDurationInHours(int durationInHours) {
		this.durationInHours = durationInHours;
	}

	public List<PricelistItem> getPricelistItems() {
		return pricelistItems;
	}

	public void setPricelistItems(List<PricelistItem> items) {
		this.pricelistItems = items;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

	public List<Zone> getZones() {
		return zones;
	}

	public void setZones(List<Zone> zones) {
		this.zones = zones;
	}
	

	public UserStatus getRequiredStatus() {
		return requiredStatus;
	}

	public void setRequiredStatus(UserStatus requiredStatus) {
		this.requiredStatus = requiredStatus;
	}

	@Override
	public boolean equals(Object obj) {
		return this.getId() == ((TicketType)obj).getId();
		
	}

	



	

	

	
	
}
