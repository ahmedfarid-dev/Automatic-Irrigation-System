package com.bankmisr.service;

import java.util.List;

import com.bankmisr.data.model.Crop;

public interface CropService extends CommonService{
	
	List<Crop> getAllCropss();
	
	Crop getCropById(int id);

}
