package gbe.demoaapi.app.TopicHierarchy;

import gbe.demoaapi.app.AAPIMessage.AAPIMessageHelper;
import gbe.demoaapi.app.AAPIMessage.AAPIMessageParser;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;

import java.util.Date;

public class Occurrence {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(Occurrence.class);

    private String occurrenceType;
    private Date predictedTime;
    private Date actualTime;


    public void setOccurrenceType(String occurrenceType) {
        this.occurrenceType = occurrenceType;
    }

    public void setPredictedTime(Date predictedTime) {
        this.predictedTime = predictedTime;
    }

    public void setActualTime(Date actualTime) {
        this.actualTime = actualTime;
    }

    public String getOccurrenceType() {
        return occurrenceType;
    }

    public Date getPredictedTime() {
        return predictedTime;
    }

    public Date getActualTime() {
        return actualTime;
    }

    public static Occurrence parseMessageItem(AAPIMessageParser messageParser) throws APIException {

        Occurrence toReturnParsed = new Occurrence();
        do {
            AAPIMessageParser.Marker currentMarker = messageParser.moveNextOrdinal();
            String fieldValue = messageParser.getFieldValue();

            switch (currentMarker.FieldOrdinal) {
                case 1:
                    toReturnParsed.setOccurrenceType(fieldValue);
                    break;
                case 2:
                    toReturnParsed.setPredictedTime(AAPIMessageHelper.parseDate(fieldValue));
                    break;
                case 3:
                    toReturnParsed.setActualTime(AAPIMessageHelper.parseDate(fieldValue));
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
        final StringBuilder sb = new StringBuilder("Occurrence{");
        sb.append("occurrenceType='").append(occurrenceType).append('\'');
        sb.append(", predictedTime=").append(predictedTime);
        sb.append(", actualTime=").append(actualTime);
        sb.append('}');
        return sb.toString();
    }
}
