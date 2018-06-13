package gbe.demoaapi.app.SubscriptionCommands.Handlers;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;

public interface AAPIMessageHandler {

    void handle(AAPIMessage aapiMessage) throws APIException;
}
