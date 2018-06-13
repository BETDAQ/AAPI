package gbe.demoaapi.app.SubscriptionCommands.Handlers;

import gbe.demoaapi.app.AAPIMessage.AAPIDataCache;
import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;
import gbe.demoaapi.app.SubscriptionCommands.PingResponse;
import gbe.demoaapi.app.SubscriptionCommands.SubscribeMarketInformationResponse;

import java.util.Arrays;

public class PingResponseHandler implements AAPIMessageHandler{

    private final static ConsoleLogger logger = LoggerFactory.getLogger(PingResponseHandler.class);

    private AAPIDataCache aapiDataCache;
    private MenuHandler menuHandler;

    public PingResponseHandler(AAPIDataCache aapiDataCache, MenuHandler menuHandler) {
        this.aapiDataCache = aapiDataCache;
        this.menuHandler = menuHandler;
    }

    @Override
    public void handle(AAPIMessage message) throws APIException {
        PingResponse response = PingResponse.parse(message);
        logger.logObject(PingResponse.class, response);
        aapiDataCache.getCorrelationIdToPingResponse().put(response.getCorrelationId(), response);
        handleResponse(response, response.getCorrelationId());
        menuHandler.processChoice();
    }

    public void handleResponse(PingResponse response, int correlationId){
        if(response.getResponseCode().equals("0")){
            logger.info(String.format("Ping with correlationId: %d was successful", correlationId));
        }else{
            logger.info(String.format("Ping with correlationId: %d failed", correlationId));
        }
    }

}
