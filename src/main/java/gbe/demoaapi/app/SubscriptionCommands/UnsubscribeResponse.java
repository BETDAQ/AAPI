package gbe.demoaapi.app.SubscriptionCommands;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.AAPIMessageHelper;
import gbe.demoaapi.app.AAPIMessage.AAPIMessageParser;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;

public class UnsubscribeResponse {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(UnsubscribeResponse.class);

    private int correlationId;
    private String responseCode;

    private String[] notActiveSubscriptionIds;


    public void setCorrelationId(int correlationId) {
        this.correlationId = correlationId;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public void setNotActiveSubscriptionIds(String[] notActiveSubscriptionIds) {
        this.notActiveSubscriptionIds = notActiveSubscriptionIds;
    }

    public int getCorrelationId() {
        return correlationId;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public String[] getNotActiveSubscriptionIds() {
        return notActiveSubscriptionIds;
    }

    public static UnsubscribeResponse parse(AAPIMessage message) throws APIException {
        AAPIMessageParser messageParser = new AAPIMessageParser(message);
        return UnsubscribeResponse.parseMessage(messageParser);
    }

    public static UnsubscribeResponse parseMessage(AAPIMessageParser messageParser) throws APIException {

        UnsubscribeResponse toReturnParsed = new UnsubscribeResponse();
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
                case 3:
                    toReturnParsed.setNotActiveSubscriptionIds(AAPIMessageHelper.parseStringArray(fieldValue));
                    break;
                default:
                    logger.info(String.format("Attempted to parse an undefined ordinal number [%d] with field name [%s] and value [%s]", currentMarker.FieldOrdinal,  messageParser.getFieldName(), messageParser.getFieldValue()));
                    break;
            }

        } while ( messageParser.readNextRecord() );

        return toReturnParsed;
    }

}
