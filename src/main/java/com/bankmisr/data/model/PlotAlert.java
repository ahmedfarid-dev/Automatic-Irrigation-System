package com.bankmisr.data.model;

import java.time.LocalDateTime;

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
public class PlotAlert {
	
	@Id    
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id; 
	
	@Column  
	private LocalDateTime creationDate;
	
	@ManyToOne
	@JsonBackReference
    @JoinColumn(name = "plot_id")
    Plot plot;

    @ManyToOne
    @JoinColumn(name = "irrigation_transaction_id")
    IrrigationTransaction irrigationTransaction;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public Plot getPlot() {
		return plot;
	}

	public void setPlot(Plot plot) {
		this.plot = plot;
	}

	public IrrigationTransaction getIrrigationTransaction() {
		return irrigationTransaction;
	}

	public void setIrrigationTransaction(IrrigationTransaction irrigationTransaction) {
		this.irrigationTransaction = irrigationTransaction;
	}
	
}
