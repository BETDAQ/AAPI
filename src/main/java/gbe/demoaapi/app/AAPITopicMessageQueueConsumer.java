package gbe.demoaapi.app;

import gbe.demoaapi.app.AAPIMessage.AAPIDataCache;
import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.AAPIMessage.Parsers.*;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class AAPITopicMessageQueueConsumer extends Thread {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(AAPICommandQueueConsumer.class);

    private final BlockingQueue<AAPIMessage> queue;
    private List<RegexAAPIMessageParser> availableMessageParsers;
    private volatile boolean finished;

    public void initializeAvailableParsers(AAPIDataCache aapiDataCache){
        List<RegexAAPIMessageParser> parsersToReturn = new ArrayList<RegexAAPIMessageParser>();
        parsersToReturn.add(new BackLayVolumeCurrencyParser(aapiDataCache));
        parsersToReturn.add(new Market1Parser(aapiDataCache));
        parsersToReturn.add(new MExchangeInfoParser(aapiDataCache));
        parsersToReturn.add(new Language3Parser(aapiDataCache));
        parsersToReturn.add(new Selection1Parser(aapiDataCache));
        parsersToReturn.add(new SExchangeInfoParser(aapiDataCache));
        parsersToReturn.add(new Language6Parser(aapiDataCache));
        parsersToReturn.add(new SelectionBlurbParser(aapiDataCache));
        parsersToReturn.add(new Event1Parser(aapiDataCache));
        parsersToReturn.add(new EExchangeInfoParser(aapiDataCache));
        availableMessageParsers = parsersToReturn;
    }

    public AAPITopicMessageQueueConsumer(BlockingQueue<AAPIMessage> queue) {
        this.queue = queue;
        finished = false;
    }

    public void run() {
        try {
            while ( !finished ) {
                AAPIMessage aapiMessage = queue.take();
                parseLoadOrDeltaAAPIMessage(aapiMessage);
            }
        }
        catch ( APIException | InterruptedException e ) {
            logger.warn(String.format("Exception processing Topic AAPIMessage: %s", e.toString()));
        }
    }

    public void finishProcessingMessages(){
        finished = true;
    }

    public void parseLoadOrDeltaAAPIMessage(AAPIMessage parsedAAPIMessage) throws APIException {
        if(parsedAAPIMessage.isLoadMessage())
            logger.info(String.format("Raw message received: %s", parsedAAPIMessage.getRawMessage()));
        for (RegexAAPIMessageParser parser : availableMessageParsers) {
            if(parser.parse(parsedAAPIMessage))
                return;
        }
        logger.warn(String.format("Didn't find any AAPIMessage parser to parse the message: %s", parsedAAPIMessage.getTopicName()));
    }

}
