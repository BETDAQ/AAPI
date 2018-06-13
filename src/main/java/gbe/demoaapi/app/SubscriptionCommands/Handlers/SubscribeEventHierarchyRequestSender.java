package gbe.demoaapi.app.SubscriptionCommands.Handlers;

import gbe.demoaapi.app.AAPIClient;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;
import gbe.demoaapi.app.MainApp;
import gbe.demoaapi.app.SubscriptionCommands.SubscribeEventHierarchyRequest;

import java.util.Random;

public class SubscribeEventHierarchyRequestSender {

    private AAPIClient aapiClient;
    private UserInputHandler userInputHandler;

    public SubscribeEventHierarchyRequestSender(AAPIClient aapiClient, UserInputHandler userInputHandler) {
        this.aapiClient = aapiClient;
        this.userInputHandler = userInputHandler;
    }

    public void sendSubscribeEventHierarchy() {
        aapiClient.send(subscribeEventHierarchyRequestWithUserInput().getAsAAPIMessageWithCorrelationId(MainApp.correlationId++));
    }

    private SubscribeEventHierarchyRequest subscribeEventHierarchyRequestWithUserInput() {
        long eventClassifierToSubscribeTo = userInputHandler.getValidEventClassifierToSubscribeTo();
        return new SubscribeEventHierarchyRequest(eventClassifierToSubscribeTo,
                true,
                false,
                false,
                null,
                null,
                true,
                null,
                null,
                true,
                false,
                true,
                false);
    }
}
