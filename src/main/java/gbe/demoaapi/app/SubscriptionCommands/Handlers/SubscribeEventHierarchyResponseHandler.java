package gbe.demoaapi.app.SubscriptionCommands.Handlers;

import gbe.demoaapi.app.AAPIMessage.AAPIDataCache;
import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;
import gbe.demoaapi.app.SubscriptionCommands.SubscribeEventHierarchyResponse;
import gbe.demoaapi.app.SubscriptionCommands.SubscribeMarketInformationResponse;

import java.util.Arrays;

public class SubscribeEventHierarchyResponseHandler implements AAPIMessageHandler {


    private final static ConsoleLogger logger = LoggerFactory.getLogger(SubscribeEventHierarchyResponseHandler.class);

    private AAPIDataCache aapiDataCache;
    private MenuHandler menuHandler;

    public SubscribeEventHierarchyResponseHandler(AAPIDataCache aapiDataCache, MenuHandler menuHandler) {
        this.aapiDataCache = aapiDataCache;
        this.menuHandler = menuHandler;
    }

    @Override
    public void handle(AAPIMessage message) throws APIException {
        SubscribeEventHierarchyResponse response = SubscribeEventHierarchyResponse.parse(message);
        logger.logObject(SubscribeEventHierarchyResponse.class, response);
        aapiDataCache.getCorrelationIdToSubscribeEventHierarchyResponse().put(response.getCorrelationId(), response);
        handleResponse(response, response.getCorrelationId());
        menuHandler.processChoice();
    }

    private void handleResponse(SubscribeEventHierarchyResponse response ,int correlationId){
        if(response.getResponseCode().equals("0")){
            logger.info(String.format("Subscription to EventHierarchy with correlationId: %d was successful", correlationId));
        }else{
            logger.info(String.format("Subscription to EventHierarchy with correlationId: %d failed", correlationId));
        }
    }
}
