package com.bankmisr.data.dataseed;

import java.time.LocalDateTime;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.bankmisr.data.enums.IrrigationTransactionStatus;
import com.bankmisr.data.model.Crop;
import com.bankmisr.data.model.IrrigationTransaction;
import com.bankmisr.data.model.Plot;
import com.bankmisr.data.model.PlotAlert;
import com.bankmisr.data.model.PlotConfiguration;
import com.bankmisr.data.model.PlotSensor;
import com.bankmisr.data.repositories.CropRepository;
import com.bankmisr.data.repositories.PlotAlertRepository;
import com.bankmisr.data.repositories.PlotRepository;


@Component
public class IrrigationDataLoader implements CommandLineRunner {

	@Autowired
	PlotRepository plotRepository;
	
	@Autowired
	CropRepository cropRepository;
	
	@Autowired
	PlotAlertRepository plotAlertRepository;

	@Override
	public void run(String... args) throws Exception {
		
		loadCropData();
		
		loadUseCase1Data();
		loadUseCase2Data();
	}

	private void loadCropData() {
		Crop crop = new Crop();
		
		crop.setName("tomatos");
		crop.setIrrigationRate(5);
		crop.setWaterAmountUnit(100);
		
		cropRepository.save(crop);
		
		crop = new Crop();
		crop.setName("Sugarcane");
		crop.setIrrigationRate(5);
		crop.setWaterAmountUnit(500);
		
		cropRepository.save(crop);
		
		crop = new Crop();
		crop.setName("wheat");
		crop.setIrrigationRate(10);
		crop.setWaterAmountUnit(300);
		
		cropRepository.save(crop);
		
		crop = new Crop();
		crop.setName("ٌRice");
		crop.setIrrigationRate(10);
		crop.setWaterAmountUnit(600);
		
		cropRepository.save(crop);	
	}

	private void loadUseCase1Data() {
			Plot plot = new Plot();
			
			plot.setLocation("Giza,Kerdasa");
			plot.setOwnerName("Ahmed abd el kareem");
			plot.setArea(70);
			plot.setLastIrragtionDate(LocalDateTime.now());
			plot.setNextIrragtionDate(LocalDateTime.now().plusMinutes(5));
		
			//plot configurations
			PlotConfiguration plotConfiguration = new PlotConfiguration();
			Crop crop = cropRepository.findByName("tomatos").get();
			plotConfiguration.setCrop(crop);
			plotConfiguration.setPlot(plot);
			plotConfiguration.setCurrentConfig(true);
			plotConfiguration.setIrrigationRate(crop.getIrrigationRate());
			plotConfiguration.setWaterAmount(crop.getWaterAmountUnit()*plot.getArea());

			plot.setPlotConfigurations(Set.of(plotConfiguration));
			
			//plot irrigation transaction history
			IrrigationTransaction irrigationTransaction1 = new IrrigationTransaction();
			irrigationTransaction1.setPlot(plot);
			irrigationTransaction1.setIrragtionDate(LocalDateTime.now().minusMinutes(5));
			irrigationTransaction1.setStatus(IrrigationTransactionStatus.SUCCEDED);
			irrigationTransaction1.setTrials(0);
			
			IrrigationTransaction irrigationTransaction2 = new IrrigationTransaction();
			irrigationTransaction2.setPlot(plot);
			irrigationTransaction2.setIrragtionDate(LocalDateTime.now().minusMinutes(10));
			irrigationTransaction2.setStatus(IrrigationTransactionStatus.SUCCEDED);
			irrigationTransaction2.setTrials(0);
			
			IrrigationTransaction irrigationTransaction3 = new IrrigationTransaction();
			irrigationTransaction3.setPlot(plot);
			irrigationTransaction3.setIrragtionDate(LocalDateTime.now().minusDays(2));
			irrigationTransaction3.setStatus(IrrigationTransactionStatus.FAILED);
			irrigationTransaction3.setTrials(3);
			
			plot.setIrrigationTransactions(Set.of(irrigationTransaction1,irrigationTransaction2,irrigationTransaction3));
			
			//plot sensor
			PlotSensor plotSensor = new PlotSensor();
			plotSensor.setAvailable(true);
			
			plot.setPlotSensor(plotSensor);
			
			plotRepository.save(plot);

			
			//plot Alerts - old alerts for display purposes
			PlotAlert plotAlert1 = new PlotAlert();
			plotAlert1.setCreationDate(LocalDateTime.now().minusDays(2).plusMinutes(15));
			plotAlert1.setPlot(plot);
			plotAlert1.setIrrigationTransaction(irrigationTransaction3);

		
			plotAlertRepository.save(plotAlert1);
			
			System.out.println(plotRepository.count());
	}
	
	private void loadUseCase2Data() {
		Plot plot = new Plot();
		
		plot.setLocation("Dakahlia,mansoura");
		plot.setArea(80);
		plot.setOwnerName("hany ahmed");
		plot.setLastIrragtionDate(LocalDateTime.now());
		plot.setNextIrragtionDate(LocalDateTime.now().plusMinutes(10));
		
		plotRepository.save(plot);
		System.out.println(plotRepository.count());
	}
}
