package gbe.demoaapi.app.SubscriptionCommands.Handlers;

import gbe.demoaapi.app.AAPIClient;
import gbe.demoaapi.app.MainApp;
import gbe.demoaapi.app.SubscriptionCommands.UnsubscribeRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class UnsubscribeRequestSender {

    private AAPIClient aapiClient;
    private UserInputHandler userInputHandler;

    public UnsubscribeRequestSender(AAPIClient aapiClient, UserInputHandler userInputHandler) {
        this.aapiClient = aapiClient;
        this.userInputHandler = userInputHandler;
    }

    public void sendUnsubscribeRequest() {
        aapiClient.send(unsubscribeWithUserInput().getAsAAPIMessageWithCorrelationId(MainApp.correlationId++));
    }

    private UnsubscribeRequest unsubscribeWithUserInput() {
        String[] idsToUnsubscribeFromTo = userInputHandler.getValidSubscriptionIdsToUnsubscribe();
        return new UnsubscribeRequest(
                new ArrayList<String>(Arrays.asList(idsToUnsubscribeFromTo))
        );
    }
}
