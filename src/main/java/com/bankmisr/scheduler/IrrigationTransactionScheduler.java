package com.bankmisr.scheduler;


import java.time.LocalDateTime;
import java.util.Set;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.bankmisr.data.enums.IrrigationTransactionStatus;
import com.bankmisr.data.model.IrrigationTransaction;
import com.bankmisr.data.model.Plot;
import com.bankmisr.data.model.PlotConfiguration;
import com.bankmisr.data.repositories.IrrigationTransactionRepository;
import com.bankmisr.data.repositories.PlotRepository;
import com.bankmisr.service.PlotSensorIntegration;

@Component
@Transactional
public class IrrigationTransactionScheduler {
	
	private static final Logger log = LoggerFactory.getLogger(IrrigationTransactionScheduler.class);
	
	@Autowired
	PlotRepository plotRepository;
	
	@Autowired
	PlotSensorIntegration plotSensorIntegration;
	
	@Autowired
	IrrigationTransactionRepository irrigationTransactionRepository;

	@Scheduled(fixedRate = 5*60*1000)
	public void ExecuteIrrigationTransactions() {
		
		Set<Plot> plots = plotRepository.findPlotsToBeIrrigated(LocalDateTime.now().minusMinutes(5),LocalDateTime.now());
		
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
