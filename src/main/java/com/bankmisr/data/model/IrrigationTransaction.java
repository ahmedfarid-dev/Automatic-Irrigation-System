package com.bankmisr.data.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bankmisr.data.enums.IrrigationTransactionStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity  
@Table
public class IrrigationTransaction {
	
	@Id    
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column  
	private IrrigationTransactionStatus status;  
	
	@Column  
	private LocalDateTime irragtionDate;
	
	@Column  
	private int trials;
	
	
	@ManyToOne
	@JsonBackReference
    @JoinColumn(name="plot_id", nullable=false)
    private Plot plot;
	
	@OneToMany(mappedBy = "irrigationTransaction")
    Set<PlotAlert> plotAlerts;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public IrrigationTransactionStatus getStatus() {
		return status;
	}


	public void setStatus(IrrigationTransactionStatus status) {
		this.status = status;
	}


	public LocalDateTime getIrragtionDate() {
		return irragtionDate;
	}


	public void setIrragtionDate(LocalDateTime irragtionDate) {
		this.irragtionDate = irragtionDate;
	}


	public int getTrials() {
		return trials;
	}


	public void setTrials(int trials) {
		this.trials = trials;
	}


	public Plot getPlot() {
		return plot;
	}


	public void setPlot(Plot plot) {
		this.plot = plot;
	}
	
}
