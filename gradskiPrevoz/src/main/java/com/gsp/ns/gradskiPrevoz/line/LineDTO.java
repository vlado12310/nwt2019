package com.gsp.ns.gradskiPrevoz.line;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gsp.ns.gradskiPrevoz.station.Station;
import com.gsp.ns.gradskiPrevoz.zone.Zone;



public class LineDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Long id;
	

	private String name;
	

	List<Station> stations;

	Zone zone;

	public LineDTO(){
		
	}
	public LineDTO(Long id, List<Station> stations, Zone zone, String name) {
		super();
		this.id = id;
		this.stations = stations;
		this.zone = zone;
		this.name = name;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<Station> getStations() {
		return stations;
	}

	public void setStations(List<Station> stations) {
		this.stations = stations;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}
	
	
	
}
