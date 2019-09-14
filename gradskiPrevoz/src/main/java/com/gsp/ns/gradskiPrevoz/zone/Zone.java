package com.gsp.ns.gradskiPrevoz.zone;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gsp.ns.gradskiPrevoz.line.Line;

@Entity
public class Zone {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Column(nullable = false)
	String name;
	
	@JsonIgnore
	@OneToMany(mappedBy = "zone")
	List<Line> lines;
	
	public Zone(Long id, String name, List<Line> lines) {
		super();
		this.id = id;
		this.name = name;
		this.lines = lines;
	}
	public Zone() {
		super();
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
	public List<Line> getLines() {
		return lines;
	}
	public void setLines(List<Line> lines) {
		this.lines = lines;
	}

	

	
}
