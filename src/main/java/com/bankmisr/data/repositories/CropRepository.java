package com.bankmisr.data.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.bankmisr.data.model.Crop;

public interface CropRepository extends CrudRepository<Crop, Integer>  
{  
	Optional<Crop> findByName(String name);
}
