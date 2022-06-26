package com.bankmisr.service.scheduler;


import java.time.LocalDateTime;
import java.util.Set;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bankmisr.common.util.Constants;
import com.bankmisr.data.enums.IrrigationTransactionStatus;
import com.bankmisr.data.model.IrrigationTransaction;
import com.bankmisr.data.model.Plot;
import com.bankmisr.data.model.PlotConfiguration;
import com.bankmisr.data.repositories.IrrigationTransactionRepository;
import com.bankmisr.data.repositories.PlotRepository;
import com.bankmisr.service.CommonService;
import com.bankmisr.service.PlotSensorIntegration;

@Component
@Transactional
public class IrrigationTransactionScheduler implements CommonService{
	
	private static final Logger log = LoggerFactory.getLogger(IrrigationTransactionScheduler.class);
	
	@Autowired
	PlotRepository plotRepository;
	
	@Autowired
	PlotSensorIntegration plotSensorIntegration;
	
	@Autowired
	IrrigationTransactionRepository irrigationTransactionRepository;

	@Scheduled(fixedRate = IRRIGATION_TRANSACTION_SCHEDULER_FIXED_RATE)
	public void ExecuteIrrigationTransactions() {
		
		Set<Plot> plots = plotRepository.findPlotsToBeIrrigated(LocalDateTime.now().minusMinutes(5),LocalDateTime.now().plusSeconds(60));
		
		if(plots.isEmpty()) {
			log.info("No Plots to be irrigated Now");
		}else {
			plots.stream().forEach(plot ->{
				
				IrrigationTransaction irrigationTransaction = new IrrigationTransaction();
				irrigationTransaction.setPlot(plot);
				irrigationTransaction.setIrragtionDate(LocalDateTime.now());
				irrigationTransaction.setStatus(IrrigationTransactionStatus.SCHEDULED);
				irrigationTransaction.setTrials(0);
				
				irrigationTransactionRepository.save(irrigationTransaction);
				//call sensor
				boolean irrigationExecutionResult = plotSensorIntegration.executePlotIntegration(irrigationTransaction);
				
				if(irrigationExecutionResult) {
					irrigationTransaction.setStatus(IrrigationTransactionStatus.SUCCEDED);
					plot.setLastIrragtionDate(LocalDateTime.now());
					PlotConfiguration plotConfiguration = plot.getPlotConfigurations().stream().filter(p -> p.isCurrentConfig()).toList().get(0);
					plot.setNextIrragtionDate(LocalDateTime.now().plusMinutes(plotConfiguration.getIrrigationRate()));
					
				}else {
					irrigationTransaction.setTrials(1);
					irrigationTransaction.setStatus(IrrigationTransactionStatus.FAILED);
				}
				plotRepository.save(plot);
			});
		}
	}

}
