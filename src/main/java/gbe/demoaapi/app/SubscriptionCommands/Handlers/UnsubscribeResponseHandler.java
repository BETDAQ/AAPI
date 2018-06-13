package gbe.demoaapi.app.SubscriptionCommands.Handlers;

import gbe.demoaapi.app.AAPIMessage.AAPIDataCache;
import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;
import gbe.demoaapi.app.SubscriptionCommands.UnsubscribeResponse;

import java.util.Arrays;

public class UnsubscribeResponseHandler implements AAPIMessageHandler{

    private final static ConsoleLogger logger = LoggerFactory.getLogger(UnsubscribeResponseHandler.class);

    private AAPIDataCache aapiDataCache;
    private MenuHandler menuHandler;

    public UnsubscribeResponseHandler(AAPIDataCache aapiDataCache, MenuHandler menuHandler) {
        this.aapiDataCache = aapiDataCache;
        this.menuHandler = menuHandler;
    }

    @Override
    public void handle(AAPIMessage message) throws APIException {
        UnsubscribeResponse response = UnsubscribeResponse.parse(message);
        logger.logObject(UnsubscribeResponse.class, response);
        aapiDataCache.getCorrelationIdToUnsubscribeResponse().put(response.getCorrelationId(), response);
        handleResponse(response, response.getCorrelationId());
        menuHandler.processChoice();
    }

    public void handleResponse(UnsubscribeResponse response, int correlationId){
        if(response.getResponseCode().equals("0")){
            logger.info(String.format("Unsubscribe request with correlationId: %d was successful", correlationId));
            if(response.getNotActiveSubscriptionIds() != null && response.getNotActiveSubscriptionIds().length > 0){
                logger.info(String.format("There was some inactive subscriptionIds that you wish to unsubscribe from with ids: %s", Arrays.asList(response.getNotActiveSubscriptionIds()).toString()));
            }
        }else{
            logger.info(String.format("Unsubscribe request with correlationId: %d failed", correlationId));
        }
    }

}
