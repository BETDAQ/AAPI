package gbe.demoaapi.app.TopicHierarchy;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.AAPIMessageHelper;
import gbe.demoaapi.app.AAPIMessage.AAPIMessageParser;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Event1 {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(Event1.class);

    private MessageAttribute<Integer> displayOrder;
    private Map<String, Score> eventScores;
    private Map<String, Occurrence> eventOccurrences;

    public Event1() {
        this.displayOrder = new MessageAttribute<Integer>();
        this.eventScores = new HashMap<String, Score>();
        this.eventOccurrences = new HashMap<String, Occurrence>();
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder.setValue(displayOrder);
    }

    public void addEventScore(Score score){
        eventScores.put(score.getScore(), score);
    }

    public void addOccurrence(Occurrence occurrence){
        eventOccurrences.put(occurrence.getOccurrenceType(), occurrence);
    }

    public MessageAttribute<Integer> getDisplayOrder() {
        return displayOrder;
    }

    public Map<String, Score> getEventScores() {
        return eventScores;
    }

    public Map<String, Occurrence> getEventOccurrences() {
        return eventOccurrences;
    }

    public static Event1 parse(AAPIMessage message) throws APIException {
        AAPIMessageParser messageParser = new AAPIMessageParser(message);
        return parseMessage(messageParser);
    }

    public static Event1 parseMessage(AAPIMessageParser messageParser) throws APIException {

        Event1 toReturnParsed = new Event1();
        do {
            AAPIMessageParser.Marker currentMarker = messageParser.moveNextOrdinal();
            String fieldValue = messageParser.getFieldValue();

            switch (currentMarker.FieldOrdinal) {
                case 1:
                    toReturnParsed.setDisplayOrder(AAPIMessageHelper.parseInteger(fieldValue));
                    break;
                case 2:
                    Score score = Score.parseMessageItem(messageParser);
                    toReturnParsed.addEventScore(score);
                    break;
                case 3:
                    Occurrence occurrence = Occurrence.parseMessageItem(messageParser);
                    toReturnParsed.addOccurrence(occurrence);
                    break;
                default:
                    logger.info(String.format("Attempted to parse an undefined ordinal number [%d] with field name [%s] and value [%s]", currentMarker.FieldOrdinal,  messageParser.getFieldName(), messageParser.getFieldValue()));
                    break;
            }

        } while ( messageParser.readNextRecord() );

        return toReturnParsed;
    }

    public void applyDelta(Event1 deltaMessage) {
        if(deltaMessage.getDisplayOrder().isSpecified())
            this.setDisplayOrder(deltaMessage.getDisplayOrder().getValue());
        for (Score deltaEventScore : deltaMessage.getEventScores().values()) {
            if(this.eventScores.containsKey(deltaEventScore.getScore())){
                this.eventScores.put(deltaEventScore.getScore(), deltaEventScore);
            }else{
                this.eventScores.put(deltaEventScore.getScore(), deltaEventScore);
            }
        }
        for (Occurrence deltaEventOccurrence : deltaMessage.getEventOccurrences().values()) {
            if(this.eventOccurrences.containsKey(deltaEventOccurrence.getOccurrenceType())){
                this.eventOccurrences.put(deltaEventOccurrence.getOccurrenceType(), deltaEventOccurrence);
            }else{
                this.eventOccurrences.put(deltaEventOccurrence.getOccurrenceType(), deltaEventOccurrence);
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Event1{");
        sb.append("displayOrder=").append(displayOrder);
        sb.append(", eventScores=").append(eventScores);
        sb.append(", eventOccurrences=").append(eventOccurrences);
        sb.append('}');
        return sb.toString();
    }
}
