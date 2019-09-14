package com.gsp.ns.gradskiPrevoz.timetable;

import java.util.Date;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Timetable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	Date date;
	
	@Column
	String departures;
	

	@ElementCollection
	@JoinTable(name="ATTRIBUTE_MINUTE_HOUR")
	@MapKeyColumn (name="HOUR_ID")
	@Column(name="MINUTE")
	Map<Integer, String> hoursMinutes;

	
	@Column
	String type;

	public Timetable() {
		super();
	}

	public Timetable(long id, Date date, String departures, Map<Integer, String> hoursMinutes, String type) {

		super();
		this.id = id;
		this.date = date;
		this.departures = departures;
		this.hoursMinutes = hoursMinutes;
		this.type = type;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDepartures() {
		return departures;
	}

	public void setDepartures(String departures) {
		this.departures = departures;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public Map<Integer, String> getHoursMinutes() {
		return hoursMinutes;
	}

	public void setHoursMinutes(Map<Integer, String> hoursMinutes) {
		this.hoursMinutes = hoursMinutes;
	}

}
