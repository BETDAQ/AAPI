package gbe.demoaapi.app.SubscriptionCommands;

import java.util.List;

import static gbe.demoaapi.app.CommonFunctions.getListOfStringsAsAAPIString;
import static gbe.demoaapi.app.MessageConstructor.AddRecord;
import static gbe.demoaapi.app.MessageConstructor.Initialise;

public class UnsubscribeRequest {

    private static final int commandId = 20;
    private static final int subscriptionIdsOrdinal = 1;


    private List<String> subscriptionIds;

    public UnsubscribeRequest(List<String> subscriptionIds) {
        this.subscriptionIds = subscriptionIds;
    }

    public String getAsAAPIMessageWithCorrelationId(int correlationId){
        String messageToSend = Initialise(commandId, correlationId);
        messageToSend = AddRecord(messageToSend, subscriptionIdsOrdinal, getListOfStringsAsAAPIString(subscriptionIds));
        return messageToSend;
    }

}
