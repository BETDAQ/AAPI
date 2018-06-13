package gbe.demoaapi.app.TopicHierarchy;

import gbe.demoaapi.app.AAPIMessage.AAPIMessageHelper;
import gbe.demoaapi.app.AAPIMessage.AAPIMessageParser;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;

import java.util.Date;

import static gbe.demoaapi.app.AAPIMessage.AAPIMessageParser.*;

public class Score {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(Score.class);

    private Date occurredAt;
    private String score;

    public void setOccurredAt(Date occurredAt) {
        this.occurredAt = occurredAt;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Date getOccurredAt() {
        return occurredAt;
    }

    public String getScore() {
        return score;
    }

    public static Score parseMessageItem(AAPIMessageParser messageParser) throws APIException {

        Score toReturnParsed = new Score();
        do {
            Marker currentMarker = messageParser.moveNextOrdinal();
            String fieldValue = messageParser.getFieldValue();

            switch (currentMarker.FieldOrdinal) {
                case 1:
                    toReturnParsed.setOccurredAt(AAPIMessageHelper.parseDate(fieldValue));
                    break;
                case 2:
                    toReturnParsed.setScore(fieldValue);
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
        final StringBuilder sb = new StringBuilder("Score{");
        sb.append("occurredAt=").append(occurredAt);
        sb.append(", score='").append(score).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
