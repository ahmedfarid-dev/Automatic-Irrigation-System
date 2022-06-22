package com.bankmisr.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity  
@Table  
public class PlotConfiguration {
	
	@Id    
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JsonBackReference
    @JoinColumn(name = "plot_id")
    Plot plot;

    @ManyToOne
    @JoinColumn(name = "crop_id")
    Crop crop;
    
    @Column  
	private int irrigationRate;  
	
	@Column  
	private int waterAmount;
	
	@Column  
	private boolean currentConfig;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Plot getPlot() {
		return plot;
	}

	public void setPlot(Plot plot) {
		this.plot = plot;
	}

	public Crop getCrop() {
		return crop;
	}

	public void setCrop(Crop crop) {
		this.crop = crop;
	}

	public int getIrrigationRate() {
		return irrigationRate;
	}

	public void setIrrigationRate(int irrigationRate) {
		this.irrigationRate = irrigationRate;
	}

	public int getWaterAmount() {
		return waterAmount;
	}

	public void setWaterAmount(int waterAmount) {
		this.waterAmount = waterAmount;
	}

	public boolean isCurrentConfig() {
		return currentConfig;
	}

	public void setCurrentConfig(boolean currentConfig) {
		this.currentConfig = currentConfig;
	}
	
}
