package gbe.demoaapi.app.TopicHierarchy;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.AAPIMessageHelper;
import gbe.demoaapi.app.AAPIMessage.AAPIMessageParser;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;

import java.util.Date;

public class EExchangeInfo {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(EExchangeInfo.class);

    private MessageAttribute<Long> eventClassifierId;
    private MessageAttribute<Boolean> isEnabledForMultiples;
    private MessageAttribute<Date> startTime;

    public EExchangeInfo() {
        this.eventClassifierId = new MessageAttribute<Long>();
        this.isEnabledForMultiples = new MessageAttribute<Boolean>();
        this.startTime = new MessageAttribute<Date>();
    }

    public void setEventClassifierId(Long eventClassifierId) {
        this.eventClassifierId.setValue(eventClassifierId);
    }

    public void setIsEnabledForMultiples(Boolean isEnabledForMultiples) {
        this.isEnabledForMultiples.setValue(isEnabledForMultiples);
    }

    public void setStartTime(Date startTime) {
        this.startTime.setValue(startTime);
    }

    public MessageAttribute<Long> getEventClassifierId() {
        return eventClassifierId;
    }

    public MessageAttribute<Boolean> getIsEnabledForMultiples() {
        return isEnabledForMultiples;
    }

    public MessageAttribute<Date> getStartTime() {
        return startTime;
    }

    public static EExchangeInfo parse(AAPIMessage message) throws APIException {
        AAPIMessageParser messageParser = new AAPIMessageParser(message);
        return parseMessage(messageParser);
    }

    public static EExchangeInfo parseMessage(AAPIMessageParser messageParser) throws APIException {

        EExchangeInfo toReturnParsed = new EExchangeInfo();
        do {
            AAPIMessageParser.Marker currentMarker = messageParser.moveNextOrdinal();
            String fieldValue = messageParser.getFieldValue();

            switch (currentMarker.FieldOrdinal) {
                case 1:
                    toReturnParsed.setEventClassifierId(AAPIMessageHelper.parseLongObject(fieldValue));
                    break;
                case 2:
                    toReturnParsed.setIsEnabledForMultiples(AAPIMessageHelper.parseBooleanObject(fieldValue));
                    break;
                case 3:
                    toReturnParsed.setStartTime(AAPIMessageHelper.parseDate(fieldValue));
                    break;
                default:
                    logger.info(String.format("Attempted to parse an undefined ordinal number [%d] with field name [%s] and value [%s]", currentMarker.FieldOrdinal,  messageParser.getFieldName(), messageParser.getFieldValue()));
                    break;
            }

        } while ( messageParser.readNextRecord() );

        return toReturnParsed;
    }

    public void applyDelta(EExchangeInfo deltaMessage) {
        if (deltaMessage.getEventClassifierId().isSpecified())
            this.setEventClassifierId(deltaMessage.getEventClassifierId().getValue());
        if (deltaMessage.getIsEnabledForMultiples().isSpecified())
            this.setIsEnabledForMultiples(deltaMessage.getIsEnabledForMultiples().getValue());
        if (deltaMessage.getStartTime().isSpecified())
            this.setStartTime(deltaMessage.getStartTime().getValue());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EExchangeInfo{");
        sb.append("eventClassifierId=").append(eventClassifierId);
        sb.append(", isEnabledForMultiples=").append(isEnabledForMultiples);
        sb.append(", startTime=").append(startTime);
        sb.append('}');
        return sb.toString();
    }
}
