package com.bankmisr.service;

import com.bankmisr.data.model.IrrigationTransaction;

public interface PlotSensorIntegration extends CommonService{
	
	boolean executePlotIntegration(IrrigationTransaction irrigationTransaction);

}
