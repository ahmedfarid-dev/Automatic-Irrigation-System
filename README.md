# Requirements
For building and running the application you need:

JDK 17
Maven 4

# Running the application locally

execute the main method in the com.bankmisr.AutomaticIrrigationSystemApplication class from your IDE.

Alternatively you can use the Spring Boot Maven plugin like so:

mvn spring-boot:run

# Data base : this application is H2 in-memory database

# Data Seeds:

	com/bankmisr/data/dataseed/IrrigationDataLoader.java will run at server initialization to 
	seed the data for basic use cases

# Assumptions

	each plot has a sensor that shall be simulated by an availability flag in the database
		
	irrigation cycle will run every 5 minute (for testing purposes)
		
	irrigation rates can be minimum every 5 minutes

# Data Model

![Alt text](./src/main/resources/static/AIS_datamodel.PNG?raw=true "Title")

# Application Features

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
		
			scheduler will run every minute to execute Failed irrigation transactions that has number of trials less than 3 by calling sensor interface
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
		

# Use Cases

	Use Case 1
	
		--> plot with area 70 m2 is planted with tomatos which has irrigation rate 5 minutes and water amount 100 Liter per m2
		--> sensor is available 
		--> plot will be picked by IrrigationTransactionScheduler and will have 
			a new successful irrigation transaction every 5 minutes with water amount 7000 Liter
	
	
	Use Case 2
	
		--> plot with area 80 m2 is planted with SugarCane which has irrigation rate 5 minutes and water amount 500 Liter per m2
		--> sensor is not available 
		--> plot will be picked by IrrigationTransactionScheduler and irrigation transaction will be failed for 1 trial
		--> irrigation transaction will be picked FailedIrrigationTransactionScheduler
		--> after two trials , new alert will triggered for this failed transaction
	
	
	Use Case 3
	
		--> plot with area 60 m2 is planted with wheat which has irrigation rate 10 minutes and water amount 300 Liter per m2
		--> last transaction failed with two trials 
		--> sensor is now available 
		--> irrigation transaction will be picked FailedIrrigationTransactionScheduler
		--> irrigation transaction will success and all required updates will take place 
		--> plot will be picked normally by IrrigationTransactionScheduler after that
		--> a new successful irrigation transaction every 10 minutes with water amount 18000 Liter
	
# Front-end application : https://github.com/ahmedfarid-dev/irrigation-system-portal
	
