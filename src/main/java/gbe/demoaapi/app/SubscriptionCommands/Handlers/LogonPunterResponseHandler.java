package gbe.demoaapi.app.SubscriptionCommands.Handlers;

import gbe.demoaapi.app.AAPIMessage.AAPIDataCache;
import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Configuration.Settings;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;
import gbe.demoaapi.app.SubscriptionCommands.LogonPunterRequest;
import gbe.demoaapi.app.SubscriptionCommands.LogonPunterResponse;

import java.util.UUID;

public class LogonPunterResponseHandler implements AAPIMessageHandler {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(LogonPunterResponseHandler.class);
    private AAPIDataCache aapiDataCache;
    private MenuHandler menuHandler;

    public LogonPunterResponseHandler(AAPIDataCache aapiDataCache, MenuHandler menuHandler) {
        this.aapiDataCache = aapiDataCache;
        this.menuHandler = menuHandler;
    }

    @Override
    public void handle(AAPIMessage message) throws APIException {
        LogonPunterResponse response = LogonPunterResponse.parse(message);
        logger.logObject(LogonPunterResponse.class, response);
        aapiDataCache.getCorrelationIdToLogonPunterResponse().put(response.getCorrelationId(), response);
        handleResponse(response, response.getCorrelationId());
    }

    public void handleResponse(LogonPunterResponse response, int correlationIdForLogonClient){
        if(response.getResponseCode().equals("0")){
            logger.info(String.format("LogonPunter with correlationId: %d was successful", correlationIdForLogonClient));
            menuHandler.processChoice();
        }else{
            logger.info(String.format("LogonPunter with correlationId: %d failed with error code: %s", correlationIdForLogonClient, response.getResponseCode()));
            logger.info("Exiting the app please reopen it and try again.");
            System.exit(0);
        }
    }
}
