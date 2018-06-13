package gbe.demoaapi.app.SubscriptionCommands.Handlers;

import gbe.demoaapi.app.AAPIClient;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;
import gbe.demoaapi.app.MainApp;
import gbe.demoaapi.app.SubscriptionCommands.SubscribeMarketInformationRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class SubscribeMarketInformationRequestSender {

    private AAPIClient aapiClient;
    private UserInputHandler userInputHandler;

    public SubscribeMarketInformationRequestSender(AAPIClient aapiClient, UserInputHandler userInputHandler) {
        this.aapiClient = aapiClient;
        this.userInputHandler = userInputHandler;
    }

    public void sendSubscribeMarketInformationRequest() {
        int correlationIdForSubscribeDetailedMarketPrices = new Random().nextInt();
        aapiClient.send(subscribeMarketInformationRequestWithUserInput().getAsAAPIMessageWithCorrelationId(MainApp.correlationId++));
    }

    private SubscribeMarketInformationRequest subscribeMarketInformationRequestWithUserInput() {
        String[] idsToSubscribeTo = userInputHandler.getValidMarketIdsToSubscribeTo();
        return new SubscribeMarketInformationRequest(
                new ArrayList<String>(Arrays.asList(idsToSubscribeTo)),
                false,
                true,
                true,
                null,
                true,
                false);
    }

}
