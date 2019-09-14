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
import com.gsp.ns.gradskiPrevoz.timetable.Timetable;
import com.gsp.ns.gradskiPrevoz.zone.Zone;


@Entity
public class Line implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	Long id;
	
	@Column(nullable = false)
	private String name;
	
	@ManyToMany
	List<Station> stations;
	
	@ManyToOne
	Zone zone;
	
	//@ManyToOne
	//Timetable timetable;
	
	
	public Line(){
		
	}
	
	public Line(Long id, List<Station> stations, Zone zone, String name) {
		super();
		this.id = id;
		this.stations = stations;
		this.zone = zone;
		this.name = name;
		//this.timetable = timetable;
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
	
/*	public Timetable getTimetable() {
		return timetable;
	}
	public void setTimetable(Timetable timetable) {
		this.timetable = timetable;
	}*/
	
	
	
}
