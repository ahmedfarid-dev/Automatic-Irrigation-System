package com.bankmisr.service;

import java.util.List;

import com.bankmisr.controller.payload.PlotDto;
import com.bankmisr.data.model.Plot;

public interface PlotService {
	
	List<Plot> getAllPlots();
	
	Plot getPlotById(int id);
	
	Plot addNewPlot(PlotDto plotDto);
	
	Plot editPlot(PlotDto plotDto);

}
