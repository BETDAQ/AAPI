package gbe.demoaapi.app.SubscriptionCommands.Handlers;

import gbe.demoaapi.app.AAPIMessage.AAPIDataCache;
import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;
import gbe.demoaapi.app.SubscriptionCommands.SubscribeDetailedMarketPricesResponse;

import java.util.Arrays;

public class SubscribeDetailedMarketPricesResponseHandler implements AAPIMessageHandler {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(SubscribeDetailedMarketPricesResponseHandler.class);

    private AAPIDataCache aapiDataCache;
    private MenuHandler menuHandler;

    public SubscribeDetailedMarketPricesResponseHandler(AAPIDataCache aapiDataCache, MenuHandler menuHandler) {
        this.aapiDataCache = aapiDataCache;
        this.menuHandler = menuHandler;
    }

    @Override
    public void handle(AAPIMessage message) throws APIException {
        SubscribeDetailedMarketPricesResponse response = SubscribeDetailedMarketPricesResponse.parse(message);
        logger.logObject(SubscribeDetailedMarketPricesResponse.class, response);
        aapiDataCache.getCorrelationIdToSubscribeDetailedMarketPricesResponse().put(response.getCorrelationId(), response);
        handleResponse(response, response.getCorrelationId());
        menuHandler.processChoice();
    }

    public void handleResponse(SubscribeDetailedMarketPricesResponse response, int correlationId){
        if(response.getResponseCode().equals("0")){
            logger.info(String.format("Subscription to DetailedMarketPrices with correlationId: %d was successful", correlationId));
            if(response.getNotActiveMarketIdRequested() != null && response.getNotActiveMarketIdRequested().length > 0){
                logger.info(String.format("There was some inactive markets that you wish to subscribe with ids: %s", Arrays.asList(response.getNotActiveMarketIdRequested()).toString()));
            }
        }else{
            logger.info(String.format("Subscription to DetailedMarketPrices with correlationId: %d failed", correlationId));
        }
    }


}
