package gbe.demoaapi.app.SubscriptionCommands;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.AAPIMessageHelper;
import gbe.demoaapi.app.AAPIMessage.AAPIMessageParser;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;

import java.util.Arrays;

public class SubscribeMarketInformationResponse {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(SubscribeMarketInformationResponse.class);

    private int correlationId;
    private String responseCode;

    private Long subscriptionId;
    private String[] notActiveMarketIdRequested;
    private Integer availableMarketsCount;

    public int getCorrelationId() {
        return correlationId;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public String[] getNotActiveMarketIdRequested() {
        return notActiveMarketIdRequested;
    }

    public Integer getAvailableMarketsCount() {
        return availableMarketsCount;
    }

    public void setCorrelationId(int correlationId) {
        this.correlationId = correlationId;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public void setNotActiveMarketIdRequested(String[] notActiveMarketIdRequested) {
        this.notActiveMarketIdRequested = notActiveMarketIdRequested;
    }

    public void setAvailableMarketsCount(Integer availableMarketsCount) {
        this.availableMarketsCount = availableMarketsCount;
    }

    public static SubscribeMarketInformationResponse parse(AAPIMessage message) throws APIException {
        AAPIMessageParser messageParser = new AAPIMessageParser(message);
        return SubscribeMarketInformationResponse.parseMessage(messageParser);
    }

    public static SubscribeMarketInformationResponse parseMessage(AAPIMessageParser messageParser) throws APIException {

        SubscribeMarketInformationResponse toReturnParsed = new SubscribeMarketInformationResponse();
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
                    toReturnParsed.setSubscriptionId(AAPIMessageHelper.parseLong(fieldValue));
                    break;
                case 3:
                    toReturnParsed.setNotActiveMarketIdRequested(AAPIMessageHelper.parseStringArray(fieldValue));
                    break;
                case 4:
                    toReturnParsed.setAvailableMarketsCount(AAPIMessageHelper.parseInt(fieldValue));
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
        final StringBuilder sb = new StringBuilder("SubscribeMarketInformationResponse{");
        sb.append("correlationId=").append(correlationId);
        sb.append(", responseCode='").append(responseCode).append('\'');
        sb.append(", subscriptionId=").append(subscriptionId);
        sb.append(", notActiveMarketIdRequested=").append(Arrays.toString(notActiveMarketIdRequested));
        sb.append(", availableMarketsCount=").append(availableMarketsCount);
        sb.append('}');
        return sb.toString();
    }
}
