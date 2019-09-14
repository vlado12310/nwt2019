package com.gsp.ns.gradskiPrevoz.user;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gsp.ns.gradskiPrevoz.security.UserAuthority;
import com.gsp.ns.gradskiPrevoz.ticket.Ticket;



@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	long idUser;

	@Column(nullable = false)
	String name;
	@Column(nullable = false)
	String lName;
	@Column(nullable = false)
	String email;
	@Column(nullable = false)
	String password;
	@Column(nullable = false)
	UserStatus status;
	@Column(nullable = false)
	double balance;
	
	@OneToMany(mappedBy="user")
	List<Ticket> tickets;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<UserAuthority> userAuthorities = new HashSet<UserAuthority>();
	
	
	public User(){
		
	}

	public User(long idUser, String name, String lName, String email, String password, UserStatus status,
			double balance, List<Ticket> tickets, Set<UserAuthority> userAuthorities) {
		super();
		this.idUser = idUser;
		this.name = name;
		this.lName = lName;
		this.email = email;
		this.password = password;
		this.status = status;
		this.balance = balance;
		this.tickets = tickets;
		this.userAuthorities = userAuthorities;
	}

	public Set<UserAuthority> getUserAuthorities() {
		return userAuthorities;
	}
	
	public void setUserAuthorities(Set<UserAuthority> userAuthorities) {
		this.userAuthorities = userAuthorities;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}
	

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public static enum UserStatus {
		STANDARD, STUDENT, ELDERLY, ADMIN, CONDUCTER
	}
}
