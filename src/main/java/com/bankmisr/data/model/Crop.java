package com.bankmisr.data.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity  
@Table
public class Crop {
	
	@Id    
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;  

	@Column  
	private String name;  
	
	@Column  
	private int irrigationRate;  
	
	@Column  
	private int waterAmountUnit;
	
	@OneToMany(mappedBy = "crop")
    Set<PlotConfiguration> plotConfigurations;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIrrigationRate() {
		return irrigationRate;
	}

	public void setIrrigationRate(int irrigationRate) {
		this.irrigationRate = irrigationRate;
	}

	public int getWaterAmountUnit() {
		return waterAmountUnit;
	}

	public void setWaterAmountUnit(int waterAmountUnit) {
		this.waterAmountUnit = waterAmountUnit;
	}  
	
	

}
