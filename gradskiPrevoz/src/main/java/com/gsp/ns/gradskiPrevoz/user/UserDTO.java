package com.gsp.ns.gradskiPrevoz.user;


import com.gsp.ns.gradskiPrevoz.user.User.UserStatus;

public class UserDTO {
	long idUser;
	String name;
	String lName;
	String email;
	String token;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserDTO (User user) {
		idUser = user.idUser;
		name = user.name;
		lName = user.lName;
		email = user.email;
		status = user.status;
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

	UserStatus status;

	double balance;
}
