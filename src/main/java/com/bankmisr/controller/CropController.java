package com.bankmisr.controller;

import java.util.List;  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;  
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankmisr.data.model.Crop;
import com.bankmisr.service.CropService;  

@RestController
@RequestMapping("/crop")
@CrossOrigin(origins = "http://localhost:4200")
public class CropController {
	
	@Autowired
	CropService cropService;  
	
	
	@GetMapping()  
	private List<Crop> getAllCrops()   
	{  
		return cropService.getAllCropss();  
	}  
	
	@GetMapping("/{id}")  
	private Crop getCrop(@PathVariable("id") int id)   
	{  
		return cropService.getCropById(id);
	}  

}
