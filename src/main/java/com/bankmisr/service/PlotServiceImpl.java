package com.bankmisr.service;

import java.time.LocalDateTime;
import java.util.ArrayList;  
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bankmisr.controller.payload.PlotDto;
import com.bankmisr.data.model.Crop;
import com.bankmisr.data.model.Plot;
import com.bankmisr.data.model.PlotConfiguration;
import com.bankmisr.data.model.PlotSensor;
import com.bankmisr.data.repositories.CropRepository;
import com.bankmisr.data.repositories.PlotRepository;
import com.bankmisr.exception.ResourceNotFoundException; 

@Service
public class PlotServiceImpl implements PlotService {
	
	@Autowired  
	PlotRepository plotRepository;  
	
	@Autowired  
	CropRepository cropRepository;
	
	 
	public List<Plot> getAllPlots()   
	{  
		List<Plot> plots = new ArrayList<Plot>();  
		plotRepository.findAll().forEach(plot -> plots.add(plot));  
		return plots;  
	}  

	public Plot getPlotById(int id)   
	{  
		return plotRepository.findById(id).get();  
	}

	public Plot addNewPlot(PlotDto plotDto) {
		
		Plot plot = new Plot();
		plot.setLocation(plotDto.getLocation());
		plot.setArea(plotDto.getArea());
		plot.setOwnerName(plotDto.getOwnerName());
		plot.setNextIrragtionDate(LocalDateTime.now().plusMinutes(plotDto.getPlotConfiguration().getIrrigationRate()));
		
		PlotConfiguration plotConfiguration = new PlotConfiguration();
		Optional<Crop> crop = cropRepository.findById(plotDto.getPlotConfiguration().getCropId());
		plotConfiguration.setWaterAmount(plotDto.getPlotConfiguration().getWaterAmount());
		plotConfiguration.setIrrigationRate(plotDto.getPlotConfiguration().getIrrigationRate());
		plotConfiguration.setCrop(crop.get());
		plotConfiguration.setPlot(plot);
		plotConfiguration.setCurrentConfig(true);
		
		plot.setPlotConfigurations(Set.of(plotConfiguration));
		//plot sensor
		PlotSensor plotSensor = new PlotSensor();
		plotSensor.setAvailable(true);
		
		plot.setPlotSensor(plotSensor);
		
		return plotRepository.save(plot);
	}

	public Plot editPlot(PlotDto plotDto) {
		Plot plot = plotRepository.findById(plotDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Plot with id: "+ plotDto.getId()));
		
		if (StringUtils.hasText(plotDto.getLocation())) {
			plot.setLocation(plotDto.getLocation());
        }
		
		if (plotDto.getArea() !=  plot.getArea()) {
			plot.setArea(plotDto.getArea());
        }
		
		if (StringUtils.hasText(plotDto.getOwnerName())) {
			plot.setOwnerName(plotDto.getOwnerName());
        }
		
		PlotConfiguration plotConfiguration = plot.getPlotConfigurations().stream().filter(p -> p.isCurrentConfig()).toList().get(0);
		
		if (plotDto.getPlotConfiguration().getCropId() > 0) {
			Crop crop = cropRepository.findById(plotDto.getPlotConfiguration().getCropId()).orElseThrow(() -> new ResourceNotFoundException("crop with id: "+ plotDto.getPlotConfiguration().getCropId()));
			if(plotConfiguration.getCrop().getId() != crop.getId()) {
				plotConfiguration.setCrop(crop);
			}
        }
		
		if (plotDto.getPlotConfiguration().getIrrigationRate() > 0) {
			plotConfiguration.setIrrigationRate(plotDto.getPlotConfiguration().getIrrigationRate());
			if(plot.getLastIrragtionDate() != null) {
				plot.setNextIrragtionDate(plot.getLastIrragtionDate().plusMinutes(plotConfiguration.getIrrigationRate()));
			}else {
				plot.setNextIrragtionDate(LocalDateTime.now().plusMinutes(plotConfiguration.getIrrigationRate()));
			}
        }
		
		if (plotDto.getPlotConfiguration().getWaterAmount() > 0) {
			plotConfiguration.setWaterAmount(plotDto.getPlotConfiguration().getWaterAmount());
        }
		
		return plotRepository.save(plot);
	}  

}
