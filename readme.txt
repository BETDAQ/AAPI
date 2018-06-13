This is a sample application to demonstrate subscribing and parsing the messages received from the AAPI server.

How to use the application:
Please make sure that you have configured correct values in the demoapp.properties.
The application is configured to work with the AAPI version of 2.2
Start the application and follow the instructions on the screen to login and subscribe to topics.
The application have the subscription commands prepopulated with the values except the marketIds for SubscribeMarketInformation, SubscribeDetailedMarketPrices
and eventClassifierId for the SubscribeEventHierarchy which will be entered by the user when prompted.
The delta messages are not displayed on the screen but there is a count of them displayed within certain time period which can be set in the demoapp.properties.
