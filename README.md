# PayPro Transaction Service
Transaction Microservice for the PayPro App

This microservice works hand in hand with two other microservices which are:
- [PayPro Account Service](https://github.com/Victor-Chinewubeze/paypro-account-service "PayPro Account Service")
- [PayPro Notification Service](https://github.com/Victor-Chinewubeze/paypro-notification-service "PayPro Notification Service")

## Technologies Used
- Spring Boot
- PostgreSQL Database
- Kafka for messaging

## Usage
- Download all three microservices to your local system 
- Set up PostgreSQL databases for each of them and add your database configuration to the two application.properties files found in the config folder and resources folder.
- Start up your Kafka server
- Start the application
The application should start running on PORT 7501

You can access swagger documentation from this link http://localhost:7501/api/v1/swagger-ui/index.html
