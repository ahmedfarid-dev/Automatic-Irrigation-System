package com.bankmisr.data.repositories;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bankmisr.data.model.Plot;  

public interface PlotRepository extends CrudRepository<Plot, Integer>  
{  
	
	@Query("SELECT p FROM Plot p WHERE p.nextIrragtionDate >= :minIrragtionDate AND p.nextIrragtionDate < :maxIrragtionDate")
	Set<Plot> findPlotsToBeIrrigated(@Param("minIrragtionDate") LocalDateTime minIrragtionDate,@Param("maxIrragtionDate") LocalDateTime maxIrragtionDate );
}
