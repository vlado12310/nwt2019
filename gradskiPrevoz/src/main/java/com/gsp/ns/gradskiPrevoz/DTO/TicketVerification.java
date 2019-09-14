package com.gsp.ns.gradskiPrevoz.DTO;

import com.gsp.ns.gradskiPrevoz.line.Line;
import com.gsp.ns.gradskiPrevoz.ticket.Ticket;

public class TicketVerification {
	Ticket ticket;
	Line line;
	public TicketVerification(Ticket ticket, Line line) {
		super();
		this.ticket = ticket;
		this.line = line;
	}
	public TicketVerification() {
		super();
	}
	public Ticket getTicket() {
		return ticket;
	}
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	public Line getLine() {
		return line;
	}
	public void setLine(Line line) {
		this.line = line;
	}
	
}
