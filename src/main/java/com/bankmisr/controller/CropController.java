package com.bankmisr.controller;

import java.util.List;  
import org.springframework.beans.factory.annotation.Autowired;   
import org.springframework.web.bind.annotation.GetMapping;  
import org.springframework.web.bind.annotation.PathVariable;    
import org.springframework.web.bind.annotation.RestController;

import com.bankmisr.data.model.Crop;
import com.bankmisr.service.CropService;  

@RestController  
public class CropController {
	
	@Autowired
	CropService cropService;  
	
	
	@GetMapping("/crop")  
	private List<Crop> getAllCrops()   
	{  
		return cropService.getAllCropss();  
	}  
	
	@GetMapping("/crop/{id}")  
	private Crop getCrop(@PathVariable("id") int id)   
	{  
		return cropService.getCropById(id);
	}  

}
