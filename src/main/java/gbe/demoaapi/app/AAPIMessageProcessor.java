package gbe.demoaapi.app;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;

import java.util.concurrent.BlockingQueue;

public class AAPIMessageProcessor {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(AAPIMessageProcessor.class);

    private final BlockingQueue<AAPIMessage> aAPICommandQueue;
    private final BlockingQueue<AAPIMessage> topicAAPIMessageQueue;

    public AAPIMessageProcessor(BlockingQueue<AAPIMessage> aAPICommandQueue, BlockingQueue<AAPIMessage> topicAAPIMessageQueue) {
        this.aAPICommandQueue = aAPICommandQueue;
        this.topicAAPIMessageQueue = topicAAPIMessageQueue;
    }

    public void parseMessageAndPutToCorrectQueue(String message){

        try{
            AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(message);
            if(parsedAAPIMessage.getMessageIdentifier() > 0){
                aAPICommandQueue.put(parsedAAPIMessage);
            }else {
                topicAAPIMessageQueue.put(parsedAAPIMessage);
            }
        }catch (Exception e){
            logger.warn(String.format("Exception parsing AAPIMessage: %s", e.toString()));
        }
    }

}
