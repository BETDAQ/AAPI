package gbe.demoaapi.app.SubscriptionCommands.Handlers;

import gbe.demoaapi.app.AAPIClient;
import gbe.demoaapi.app.MainApp;
import gbe.demoaapi.app.SubscriptionCommands.PingRequest;
import gbe.demoaapi.app.SubscriptionCommands.UnsubscribeRequest;


import java.util.Date;
import java.util.Random;

public class PingRequestSender {

    private AAPIClient aapiClient;

    public PingRequestSender(AAPIClient aapiClient) {
        this.aapiClient = aapiClient;
    }

    public void sendPingRequest() {
        aapiClient.send(pingRequest().getAsAAPIMessageWithCorrelationId(MainApp.correlationId++));
    }

    private PingRequest pingRequest() {
        return new PingRequest(
                new Date()
        );
    }

}
