package com.bankmisr.service;

import java.util.ArrayList;  
import java.util.List;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;

import com.bankmisr.data.model.Crop;
import com.bankmisr.data.repositories.CropRepository;

@Service
public class CropServiceImpl  implements CropService{
	
	@Autowired  
	CropRepository cropRepository;  
	
	
	 
	public List<Crop> getAllCropss()   
	{  
		List<Crop> crops = new ArrayList<Crop>();  
		cropRepository.findAll().forEach(crop -> crops.add(crop));  
		return crops;  
	}  

	public Crop getCropById(int id)   
	{  
		return cropRepository.findById(id).get();  
	}  

}
