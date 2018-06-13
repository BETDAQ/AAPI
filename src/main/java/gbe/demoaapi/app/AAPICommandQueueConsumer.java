package gbe.demoaapi.app;

import gbe.demoaapi.app.AAPIMessage.AAPIDataCache;
import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;
import gbe.demoaapi.app.SubscriptionCommands.Handlers.*;

import java.util.HashMap;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class AAPICommandQueueConsumer extends Thread {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(AAPICommandQueueConsumer.class);

    private final BlockingQueue<AAPIMessage> queue;
    private Map<Integer, AAPIMessageHandler> availableCommandParsers;
    private volatile boolean finished;

    public void initializeAvailableCommandParsers(AAPIDataCache aapiDataCache, MenuHandler menuHandler) {
        Map<Integer,AAPIMessageHandler> commandParserMapToReturn = new HashMap<Integer,AAPIMessageHandler>();
        commandParserMapToReturn.put(9, new SubscribeMarketInformationResponseHandler(aapiDataCache, menuHandler));
        commandParserMapToReturn.put(10, new SubscribeDetailedMarketPricesResponseHandler(aapiDataCache, menuHandler));
        commandParserMapToReturn.put(2, new LogonPunterResponseHandler(aapiDataCache, menuHandler));
        commandParserMapToReturn.put(20, new UnsubscribeResponseHandler(aapiDataCache, menuHandler));
        commandParserMapToReturn.put(22, new PingResponseHandler(aapiDataCache, menuHandler));
        commandParserMapToReturn.put(12, new SubscribeEventHierarchyResponseHandler(aapiDataCache, menuHandler));
        availableCommandParsers = commandParserMapToReturn;
    }

    public AAPICommandQueueConsumer(BlockingQueue<AAPIMessage> queue) {
        this.queue = queue;
        this.finished = false;
    }

    public void run() {
        try {
            while ( !finished ) {
                AAPIMessage aapiMessage = queue.take();
                parseCommandMessage(aapiMessage);
            }
        }
        catch ( APIException | InterruptedException e ) {
            logger.warn(String.format("Exception processing Command AAPIMessage: %s", e.toString()));
        }
    }

    public void finishProcessingMessages(){
        finished = true;
    }

    public void parseCommandMessage(AAPIMessage parsedAAPIMessage) throws APIException, InterruptedException {
        TimeUnit.SECONDS.sleep(2);//delay for the print of the menu so it is the last on the screen (handle(parsedAAPIMessage) will call the print of the menu)
        logger.info(String.format("Raw message received: %s", parsedAAPIMessage.getRawMessage()));
        if(availableCommandParsers.containsKey(parsedAAPIMessage.getMessageIdentifier())){
            availableCommandParsers.get(parsedAAPIMessage.getMessageIdentifier()).handle(parsedAAPIMessage);
        }else {
            logger.info(String.format("Didn't find any AAPICommand parser to parse the message with commandID: %d", parsedAAPIMessage.getMessageIdentifier()));
        }
    }

}
