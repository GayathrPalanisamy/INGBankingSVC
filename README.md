# ING Banking Loan Request Service

### Overview:

* This application is used to create a loan request and retrieve loan details for ING customers.
* When the customer is requesting for loan details, Our application will check if the customer already present.
* If customer present then the loan details will be added to the existing customer.
* If customer is requesting  loan details for the first time, then customer and loan details will be created.

### Data required from the ING Customer:
* Customer ID
* Customer Name
* Loan Amount
* Loan Unique Identifier which describes about loan as car loan, personal loan.

### Operations:

* CreateLoanRequest - To request for loan.
* RetrieveLoanDetails - To retrieve the total amount of loan the customer requested.

### Project Setup to run in local

#### Prerequirements
* Java 17 (preferable)
* IDE
* Maven

#### Things to do to get the project up and running

* Download the project as Zip file or git clone the repo.
* Import the project as Maven in any IDE.
* Maven refresh;
    * mvn clean install;
* Run the application as Spring Boot APP.
* After the application is up and running use the swagger URL mentioned below to test the application.
  * http://localhost:8080/swagger-ui/index.html#
* If want to test the application in PostMan. Then the URL as below
  * POST - CreateLoanRequest - http://localhost:8080/v1/loan/loanRequest
  * GET - RetrieveLoanDetails - http://localhost:8080/v1/loan/loanDetails/{customerId}
* Inbuilt memory is used for better performance, no need of external Database.

### Additional features built:
* Swagger for dev ease.
* Validation with proper error handling.
* Junit covering most scenarios.
