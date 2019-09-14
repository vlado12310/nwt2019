package com.gsp.ns.gradskiPrevoz.vehicle;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.gsp.ns.gradskiPrevoz.line.Line;
import com.gsp.ns.gradskiPrevoz.location.Location;

@Entity
public class Vehicle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column(nullable = false)
	vehicleType type;
	
	
	@OneToOne
	Location currentLocation;
	
	@ManyToOne
	Line line;
	
	public Vehicle(Long id, vehicleType type, Location currentLocation, Line line) {
		super();
		this.id = id;
		this.type = type;
		this.currentLocation = currentLocation;
		this.line = line;
	}
	
	public Vehicle() {
		super();
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public vehicleType getType() {
		return type;
	}


	public void setType(vehicleType type) {
		this.type = type;
	}


	public Location getCurrentLocation() {
		return currentLocation;
	}


	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
	}


	public Line getLine() {
		return line;
	}


	public void setLine(Line line) {
		this.line = line;
	}


	
	
	
	public static enum  vehicleType{
		BUS, SUBWAY, TROLLEYBUS;

		private static final List<vehicleType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
		private static final int SIZE = VALUES.size();
		private static final Random RANDOM = new Random();

		public static vehicleType randomType() {
			return VALUES.get(RANDOM.nextInt(SIZE));
		}
	}
}
