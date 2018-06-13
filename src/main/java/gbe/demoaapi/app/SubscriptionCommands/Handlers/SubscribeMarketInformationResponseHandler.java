package gbe.demoaapi.app.SubscriptionCommands.Handlers;

import gbe.demoaapi.app.AAPIMessage.AAPIDataCache;
import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;
import gbe.demoaapi.app.SubscriptionCommands.SubscribeMarketInformationResponse;

import java.util.Arrays;

public class SubscribeMarketInformationResponseHandler implements AAPIMessageHandler {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(SubscribeMarketInformationResponseHandler.class);

    private AAPIDataCache aapiDataCache;
    private MenuHandler menuHandler;

    public SubscribeMarketInformationResponseHandler(AAPIDataCache aapiDataCache, MenuHandler menuHandler) {
        this.aapiDataCache = aapiDataCache;
        this.menuHandler = menuHandler;
    }

    @Override
    public void handle(AAPIMessage message) throws APIException {
        SubscribeMarketInformationResponse response = SubscribeMarketInformationResponse.parse(message);
        logger.logObject(SubscribeMarketInformationResponse.class, response);
        aapiDataCache.getCorrelationIdToSubscribeMarketInformationResponse().put(response.getCorrelationId(), response);
        handleResponse(response, response.getCorrelationId());
        menuHandler.processChoice();
    }

    public void handleResponse(SubscribeMarketInformationResponse response, int correlationId){
        if(response.getResponseCode().equals("0")){
            logger.info(String.format("Subscription to MarketInformation with correlationId: %d was successful", correlationId));
            if(response.getNotActiveMarketIdRequested() != null && response.getNotActiveMarketIdRequested().length > 0){
                logger.info(String.format("There was some inactive markets that you wish to subscribe with ids: %s", Arrays.asList(response.getNotActiveMarketIdRequested()).toString()));
            }
        }else{
            logger.info(String.format("Subscription to MarketInformation with correlationId: %d failed", correlationId));
        }
    }

}
