package gbe.demoaapi.app.SubscriptionCommands;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.AAPIMessageHelper;
import gbe.demoaapi.app.AAPIMessage.AAPIMessageParser;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;

public class PingResponse {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(PingResponse.class);

    private int correlationId;
    private String responseCode;

    private int messagesInQueue;


    public void setCorrelationId(int correlationId) {
        this.correlationId = correlationId;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public void setMessagesInQueue(int messagesInQueue) {
        this.messagesInQueue = messagesInQueue;
    }

    public int getCorrelationId() {
        return correlationId;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public int getMessagesInQueue() {
        return messagesInQueue;
    }

    public static PingResponse parse(AAPIMessage message) throws APIException {
        AAPIMessageParser messageParser = new AAPIMessageParser(message);
        return PingResponse.parseMessage(messageParser);
    }

    public static PingResponse parseMessage(AAPIMessageParser messageParser) throws APIException {

        PingResponse toReturnParsed = new PingResponse();
        do {
            AAPIMessageParser.Marker currentMarker = messageParser.moveNextOrdinal();
            String fieldValue = messageParser.getFieldValue();

            switch (currentMarker.FieldOrdinal) {
                case 0:
                    toReturnParsed.setCorrelationId(AAPIMessageHelper.parseInt(fieldValue));
                    break;
                case 1:
                    toReturnParsed.setResponseCode(fieldValue);
                    break;
                case 2:
                    toReturnParsed.setMessagesInQueue(AAPIMessageHelper.parseInt(fieldValue));
                    break;
                default:
                    logger.info(String.format("Attempted to parse an undefined ordinal number [%d] with field name [%s] and value [%s]", currentMarker.FieldOrdinal,  messageParser.getFieldName(), messageParser.getFieldValue()));
                    break;
            }

        } while ( messageParser.readNextRecord() );

        return toReturnParsed;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PingResponse{");
        sb.append("correlationId=").append(correlationId);
        sb.append(", responseCode='").append(responseCode).append('\'');
        sb.append(", messagesInQueue=").append(messagesInQueue);
        sb.append('}');
        return sb.toString();
    }
}
