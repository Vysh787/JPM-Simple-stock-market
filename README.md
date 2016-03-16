#Simple Stock Market

The Simple Stock market is implemented as a Spring-Boot micro service application.Micro service architectures are the becoming an industry standard as the most evolved form of service oriented architectures (SOA). It relies on the concept of small application suites which are self contained and can be deployed independently. Spring-boot is one of the most widely used technology for implementing micro services.<br/>
The Simple-stock-market micro service has multiple REST service endpoints exposed for every major operation.
Being a service oriented application basic thread safety has been taken care of.
<br/>
Note: Being a demo application, Advanced functionalities such as clean up expired trades and use of concurrent data structures have not been used.

Few of the other concepts used in this simple applications are below,

	-Spring MVC
	-Spring Core
	-Swagger UI integration.
	-Spring annotation based configuration.
	-JUnit		
	-Maven
	-Java 8
        -Jackson

##Problem definition:

     Provide the source code for an application that will:- 
	a. For a given stock, 
	i. Given any price as input, calculate the dividend yield 
	ii. Given any price as input, calculate the P/E Ratio 
	iii. Record a trade, with timestamp, quantity, buy or sell indicator and price 
	iv. Calculate Volume Weighted Stock Price based on trades in past 5 minutes 
	b. Calculate the GBCE All Share Index using the geometric mean of the Volume Weighted Stock Price for all stocks 
##Architecture and Design:

![Simple Stock Market - Architecture](https://github.com/Vysh787/JPM-Simple-stock-market/blob/master/src/main/resources/Simple-stock-market-Architecture.png)

##Implementation specifics: 

       com.jpm.stockmarket.config.StockWebApplication
The Starter class which launches the application inside the spring-boot container. The class also import Spring configuration classes containing bean definition.

       com.jpm.stockmarket.config.StockConfig
The primary configuration class which contains the StockMarket service bean definition.

       com.jpm.stockmarket.controller.StockServiceController 
Spring MVC controller class where all the service endpoints are defined. Corresponding business logic is invoked from the service methods.<br/>
The “StockManager” instance is a singleton bean injected to the Controller.

       com.jpm.stockmarket.service.IStockManager
       com.jpm.stockmarket.service.StockManager 
The service class containing the root implementation.

       com.jpm.stockmarket.service.ITradeManager  
       com.jpm.stockmarket.service.TradeManager  
Stores the trades which are made and provides additional operation on the trades. 

       com.jpm.stockmarket.dto.Stock  
       com.jpm.stockmarket.dto.StockIndicator  
       com.jpm.stockmarket.dto.StockSymbol  
       com.jpm.stockmarket.dto.Trade  
DTO Objects.

       com.jpm.stockmarket.domainmodel.StockRequest  
       com.jpm.stockmarket.domainmodel.StockResponse 
Domain object containing REST request and response mappings.


##Build and Unit Tests:

Unit tests are added to the test/java directory. <br/>Please find the instruction below on building and executing unit tests

###Pre requisites-
	Java 1.8
	Maven 3.3
	Git client

###Build/invoke tests-

	git clone https://github.com/Vysh787/JPM-Simple-stock-market.git
	cd JPM-Simple-stock-market
	mvn clean install

Builds the project and executes Unit tests.

##See it working! -
The entire micro service has been integrated with Swagger UI which provides an intuitive GUI for the testing the rest service and also scope for extensive documentation in the code.
Swagger UI can be seamlessly integrated with Spring MVC based applications.

###Running the spring-boot application-

mvn spring-boot:run

###Swagger GUI- 

The swagger GUI can be accessed at http://localhost:9192/stock-market-api/swagger-ui.html
Using the swagger GUI the rest API’s can be invoked by passing the request body/params. Please find a sample screenshot below.

![Swagger UI snapshot](https://github.com/Vysh787/JPM-Simple-stock-market/blob/master/src/main/resources/Swagger-UI.png)