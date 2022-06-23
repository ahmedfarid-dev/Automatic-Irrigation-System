package com.bankmisr.scheduler;

import java.time.LocalDateTime;
import java.util.Set;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.bankmisr.config.ApplicationConfig;
import com.bankmisr.data.enums.IrrigationTransactionStatus;
import com.bankmisr.data.model.IrrigationTransaction;
import com.bankmisr.data.model.PlotConfiguration;
import com.bankmisr.data.repositories.IrrigationTransactionRepository;
import com.bankmisr.service.PlotSensorIntegration;

@Component
@Transactional
public class FailedIrrigationTransactionScheduler {
	
	private static final Logger log = LoggerFactory.getLogger(FailedIrrigationTransactionScheduler.class);
	
	@Autowired
	IrrigationTransactionRepository irrigationTransactionRepository;
	
	@Autowired
	PlotSensorIntegration plotSensorIntegration;
	
	 @Autowired
	 private ApplicationConfig applicationConfig;
	
	@Scheduled(fixedRate = 5*60*1000)
	public void ExecuteIrrigationTransactions() {
		
		
		Set<IrrigationTransaction> failedIrrigationTransactions = irrigationTransactionRepository.findFailedIrrigationTransactions(applicationConfig.getIrrigationFailedTransactionTrialsConfig());
		
		if(failedIrrigationTransactions.isEmpty()) {
			log.info("No Failed Transactions to handle");
		}else {
			failedIrrigationTransactions.stream().forEach(irrigationTransaction ->{
				//call sensor
				boolean irrigationExecutionResult = plotSensorIntegration.executePlotIntegration(irrigationTransaction);
				
				if(irrigationExecutionResult) {
					irrigationTransaction.setStatus(IrrigationTransactionStatus.SUCCEDED);
					irrigationTransaction.getPlot().setLastIrragtionDate(LocalDateTime.now());
					PlotConfiguration plotConfiguration = irrigationTransaction.getPlot().getPlotConfigurations().stream().filter(p -> p.isCurrentConfig()).toList().get(0);
					irrigationTransaction.getPlot().setNextIrragtionDate(LocalDateTime.now().plusMinutes(plotConfiguration.getIrrigationRate()));
					
				}else {
					irrigationTransaction.setTrials(irrigationTransaction.getTrials()+1);
					irrigationTransaction.setStatus(IrrigationTransactionStatus.FAILED);
				}
				irrigationTransactionRepository.save(irrigationTransaction);
			});
		}
	}

}
