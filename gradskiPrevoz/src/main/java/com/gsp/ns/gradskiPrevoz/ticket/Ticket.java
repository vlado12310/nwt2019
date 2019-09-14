package com.gsp.ns.gradskiPrevoz.ticket;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gsp.ns.gradskiPrevoz.ticketType.TicketType;
import com.gsp.ns.gradskiPrevoz.user.User;

@Entity
public class Ticket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@JsonIgnore
	@ManyToOne(optional= false)
	User user;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm")
	Date timeOfActivation;
	
	@Column(nullable = false)
	Date purchaseDate;
	
	@ManyToOne
	TicketType ticketType;

	public Ticket(long id, User user, Date timeOfActivation, Date purchaseDate, TicketType ticketType) {
		super();
		this.id = id;
		this.user = user;
		this.timeOfActivation = timeOfActivation;
		this.purchaseDate = purchaseDate;
		this.ticketType = ticketType;
	}

	public Ticket() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getTimeOfActivation() {
		return timeOfActivation;
	}

	public void setTimeOfActivation(Date timeOfActivation) {
		this.timeOfActivation = timeOfActivation;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public TicketType getTicketType() {
		return ticketType;
	}

	public void setTicketType(TicketType ticketType) {
		this.ticketType = ticketType;
	}
	
	

}
