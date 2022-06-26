package com.bankmisr.service.scheduler;

import java.time.LocalDateTime;
import java.util.Set;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bankmisr.common.util.Constants;
import com.bankmisr.data.enums.IrrigationTransactionStatus;
import com.bankmisr.data.model.IrrigationTransaction;
import com.bankmisr.data.model.PlotAlert;
import com.bankmisr.data.model.PlotConfiguration;
import com.bankmisr.data.repositories.IrrigationTransactionRepository;
import com.bankmisr.service.PlotSensorIntegration;

@Component
@Transactional
public class FailedIrrigationTransactionScheduler implements Constants{
	
	private static final Logger log = LoggerFactory.getLogger(FailedIrrigationTransactionScheduler.class);
	
	@Autowired
	private IrrigationTransactionRepository irrigationTransactionRepository;
	
	@Autowired
	private PlotSensorIntegration plotSensorIntegration;
	
	@Value("${irrigation.failed.transactions.trials}")
	private int irrigationFailedTransactionTrials;
	
	@Scheduled(fixedRate = FAILED_IRRIGATION_TRANSACTION_SCHEDULER_FIXED_RATE)
	public void ExecuteIrrigationTransactions() {
		
		Set<IrrigationTransaction> failedIrrigationTransactions = irrigationTransactionRepository.findFailedIrrigationTransactions(irrigationFailedTransactionTrials);
		
		if(failedIrrigationTransactions.isEmpty()) {
			log.info("No Failed Transactions to handle");
		}else {
			failedIrrigationTransactions.stream().forEach(irrigationTransaction ->{
				//call sensor
				boolean irrigationExecutionResult = plotSensorIntegration.executePlotIntegration(irrigationTransaction);
				
				if(irrigationExecutionResult) {
					irrigationTransaction.setStatus(IrrigationTransactionStatus.SUCCEDED);
					irrigationTransaction.setTrials(0);
					irrigationTransaction.getPlot().setLastIrragtionDate(LocalDateTime.now());
					PlotConfiguration plotConfiguration = irrigationTransaction.getPlot().getPlotConfigurations().stream().filter(p -> p.isCurrentConfig()).toList().get(0);
					irrigationTransaction.getPlot().setNextIrragtionDate(LocalDateTime.now().plusMinutes(plotConfiguration.getIrrigationRate()));
					
				}else {
					irrigationTransaction.setTrials(irrigationTransaction.getTrials()+1);
					
					if(irrigationTransaction.getTrials() == irrigationFailedTransactionTrials) {
						
						PlotAlert plotAlert = new PlotAlert();
						plotAlert.setCreationDate(LocalDateTime.now());
						plotAlert.setPlot(irrigationTransaction.getPlot());
						plotAlert.setIrrigationTransaction(irrigationTransaction);

						irrigationTransaction.getPlot().getPlotAlerts().add(plotAlert);
					}
				}
				irrigationTransactionRepository.save(irrigationTransaction);
			});
		}
	}

}
