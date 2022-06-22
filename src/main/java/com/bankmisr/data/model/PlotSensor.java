package com.bankmisr.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity  
@Table 
public class PlotSensor {

	
	@Id    
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;  

	@Column  
	private boolean available;
	
	@OneToOne(mappedBy = "plotSensor")
	@JsonBackReference
    private Plot plot;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Plot getPlot() {
		return plot;
	}

	public void setPlot(Plot plot) {
		this.plot = plot;
	}
	
}
