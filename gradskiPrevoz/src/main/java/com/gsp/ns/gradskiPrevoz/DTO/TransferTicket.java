package com.gsp.ns.gradskiPrevoz.DTO;

import com.gsp.ns.gradskiPrevoz.ticket.Ticket;
import com.gsp.ns.gradskiPrevoz.user.User;

public class TransferTicket {
	User user;
	Ticket ticket;



	public TransferTicket() {
		super();
	}
	public TransferTicket(User user, Ticket ticket) {
		super();
		this.user = user;
		this.ticket = ticket;
	}
	public Ticket getTicket() {
		return ticket;
	}
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
