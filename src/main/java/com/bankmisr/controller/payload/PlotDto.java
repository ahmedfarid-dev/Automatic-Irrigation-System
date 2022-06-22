package com.bankmisr.controller.payload;

public class PlotDto {
	
	private int id;  

	private String location;  
	
	private int area;

	private String ownerName;  
	
	private PlotConfigurationDto plotConfiguration;

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

	public PlotConfigurationDto getPlotConfiguration() {
		return plotConfiguration;
	}

	public void setPlotConfiguration(PlotConfigurationDto plotConfiguration) {
		this.plotConfiguration = plotConfiguration;
	}

}
