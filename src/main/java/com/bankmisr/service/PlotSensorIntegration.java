package com.bankmisr.service;

import com.bankmisr.data.model.IrrigationTransaction;

public interface PlotSensorIntegration {
	
	boolean executePlotIntegration(IrrigationTransaction irrigationTransaction);

}
