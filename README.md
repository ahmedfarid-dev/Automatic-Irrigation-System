Requirements
For building and running the application you need:

JDK 17
Maven 4

Running the application locally

execute the main method in the com.bankmisr.AutomaticIrrigationSystemApplication class from your IDE.

Alternatively you can use the Spring Boot Maven plugin like so:

mvn spring-boot:run

Data base : this application is H2 in-memory database

Data Seeds:

	com/bankmisr/data/dataseed/IrrigationDataLoader.java will run at server initialization to 
	seed the data for basic use cases

Assumptions

	each plot has a sensor that shall be simulated by an availability flag in the database
		
	irrigation cycle will run every 5 minute (for testing purposes)
		
	irrigation rates can be every 5 minutes or its multiples 10,15,20,25 ...... 

Data Model

![Alt text](./src/main/resources/static/AIS_datamodel.PNG?raw=true "Title")

Application Features

	Rest End points 
		getAllCrops() --> get all available Crops
		getCrop(int id) --> get Crop by Id
		
		getAllPlots(); --> get all available Plots
		getPlot(int id); --> get all Plot by Id
		addNewPlot(PlotDto plotDto); --> add new Plot
		editPlot(int id,PlotDto plotDto); --> Edit plot with given Id
		
		
	Schedulers
	
		IrrigationTransactionScheduler
		
			scheduler will run every 5 minutes to execute irrigation transactions that should happen now or in the last 5 minutes by calling plot sensor interface
				if sensor available --> 
					irrigation will be executed --> 
								irrigation status will be updated to success
								next irrigation date will be updated
								last irrigation date will be updated
				if sensor not available --> 
					irrigation will not be executed -->
								irrigation status will be updated to failed
								irrigation trials will be incremented by one
								
		FailedIrrigationTransactionScheduler
		
			scheduler will run every 5 minutes to execute Failed irrigation transactions that has number of trials less than 3 by calling sensor interface
				if sensor available --> 
					irrigation will be executed --> 
								irrigation status will be updated to success
								irrigation trials will be resetted
								next irrigation date will be updated
								last irrigation date will be updated
				if sensor not available --> 
					irrigation will not be executed -->
								irrigation status will be updated to failed
								irrigation trials will be incremented by one
								new alert with plot and transaction details will be created
		

Use Cases

	Use Case 1
	
	
	Use Case 2
	
	
	Use Case 3
	

