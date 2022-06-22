package com.bankmisr.controller;

import java.util.List;  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankmisr.controller.payload.PlotDto;
import com.bankmisr.data.model.Plot;
import com.bankmisr.service.PlotService;  

@RestController
@RequestMapping("/plot")
public class PlotController {
	
	@Autowired
	PlotService plotService;  
	
	@GetMapping()  
	private List<Plot> getAllPlots()   
	{
		return plotService.getAllPlots();  
	}
	
	@GetMapping("/{id}")  
	private Plot getPlot(@PathVariable("id") int id)   
	{  
		return plotService.getPlotById(id);
	}
	
	@PostMapping
    public ResponseEntity<Plot> addNewPlot(@RequestBody PlotDto plotDto) {
        return ResponseEntity.ok(plotService.addNewPlot(plotDto));
    }
	
	@PatchMapping("/{id}")
    public ResponseEntity<Plot> editPlot(@PathVariable("id") int id,@RequestBody PlotDto plotDto) {
		plotDto.setId(id);
        return ResponseEntity.ok(plotService.editPlot(plotDto));
    }

}
