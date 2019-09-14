package com.gsp.ns.gradskiPrevoz.station;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gsp.ns.gradskiPrevoz.location.Location;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Station implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	


	@Column(nullable = false)
	private String name;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToOne( cascade = CascadeType.ALL)
	private Location location;
	//to do
	//private ArrayList<Line> lines;
	
	public Station(Long id, String name, Location location) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
	}
	public Station() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	

	
	
}
