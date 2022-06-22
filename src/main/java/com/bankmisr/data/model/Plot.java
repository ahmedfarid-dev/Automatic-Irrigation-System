package com.bankmisr.data.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;  
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;  

  
@Entity  
@Table  
public class Plot {
	
	@Id    
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;  

	@Column  
	private String location;  
	
	@Column  
	private int area;

	@Column  
	private String ownerName;  
  
	@Column  
	private LocalDateTime nextIrragtionDate;
	
	@Column  
	private LocalDateTime lastIrragtionDate;
	
	@OneToMany(mappedBy = "plot",cascade = {CascadeType.ALL})
	@JsonManagedReference
    Set<PlotConfiguration> plotConfigurations;
	
	@OneToMany(mappedBy="plot",cascade = {CascadeType.ALL})
	@JsonManagedReference
    private Set<IrrigationTransaction> irrigationTransactions;
	
	@OneToMany(mappedBy = "plot",cascade = {CascadeType.ALL})
	@JsonManagedReference
    Set<PlotAlert> plotAlerts;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JsonManagedReference
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private PlotSensor plotSensor;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public LocalDateTime getNextIrragtionDate() {
		return nextIrragtionDate;
	}

	public void setNextIrragtionDate(LocalDateTime nextIrragtionDate) {
		this.nextIrragtionDate = nextIrragtionDate;
	}

	public LocalDateTime getLastIrragtionDate() {
		return lastIrragtionDate;
	}

	public void setLastIrragtionDate(LocalDateTime lastIrragtionDate) {
		this.lastIrragtionDate = lastIrragtionDate;
	}

	public Set<PlotConfiguration> getPlotConfigurations() {
		return plotConfigurations;
	}

	public void setPlotConfigurations(Set<PlotConfiguration> plotConfigurations) {
		this.plotConfigurations = plotConfigurations;
	}

	public Set<IrrigationTransaction> getIrrigationTransactions() {
		return irrigationTransactions;
	}

	public void setIrrigationTransactions(Set<IrrigationTransaction> irrigationTransactions) {
		this.irrigationTransactions = irrigationTransactions;
	}

	public Set<PlotAlert> getPlotAlerts() {
		return plotAlerts;
	}

	public void setPlotAlerts(Set<PlotAlert> plotAlerts) {
		this.plotAlerts = plotAlerts;
	}

	public PlotSensor getPlotSensor() {
		return plotSensor;
	}

	public void setPlotSensor(PlotSensor plotSensor) {
		this.plotSensor = plotSensor;
	}
	

}
