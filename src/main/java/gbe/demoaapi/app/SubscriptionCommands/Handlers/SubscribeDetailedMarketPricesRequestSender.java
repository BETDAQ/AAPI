package gbe.demoaapi.app.SubscriptionCommands.Handlers;

import gbe.demoaapi.app.AAPIClient;
import gbe.demoaapi.app.AAPIMessage.AAPIDataCache;

import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;
import gbe.demoaapi.app.MainApp;
import gbe.demoaapi.app.SubscriptionCommands.SubscribeDetailedMarketPricesRequest;
import gbe.demoaapi.app.SubscriptionCommands.SubscribeDetailedMarketPricesResponse;
import sun.applet.Main;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SubscribeDetailedMarketPricesRequestSender {

    private AAPIClient aapiClient;
    private UserInputHandler userInputHandler;

    public SubscribeDetailedMarketPricesRequestSender(AAPIClient aapiClient, UserInputHandler userInputHandler) {
        this.aapiClient = aapiClient;
        this.userInputHandler = userInputHandler;

    }

    public void sendSubscribeDetailedMarketPricesRequest() {
        aapiClient.send(subscribeDetailedMarketPricesRequestWithUserInput().getAsAAPIMessageWithCorrelationId(MainApp.correlationId++));
    }

    private SubscribeDetailedMarketPricesRequest subscribeDetailedMarketPricesRequestWithUserInput(){
        String[] idsToSubscribeTo = userInputHandler.getValidMarketIdsToSubscribeTo();
        return new SubscribeDetailedMarketPricesRequest(
                new ArrayList<String>(Arrays.asList(idsToSubscribeTo)),
                3,
                3,
                new BigDecimal("10"),
                false);
    }

}
