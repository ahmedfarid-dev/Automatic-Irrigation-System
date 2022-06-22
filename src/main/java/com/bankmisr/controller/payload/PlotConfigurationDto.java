package com.bankmisr.controller.payload;

public class PlotConfigurationDto{

	private int cropId;
	
	private int irrigationRate;
	
	private int waterAmount;

	public int getCropId() {
		return cropId;
	}

	public void setCropId(int cropId) {
		this.cropId = cropId;
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
	
}
