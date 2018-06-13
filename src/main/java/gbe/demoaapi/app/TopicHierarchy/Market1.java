package gbe.demoaapi.app.TopicHierarchy;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.AAPIMessageHelper;
import gbe.demoaapi.app.AAPIMessage.AAPIMessageParser;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Market1 {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(Market1.class);

    private MessageAttribute<Integer> displayOrder;
    private Map<String ,Score> marketScores;

    public Market1() {
        this.displayOrder = new MessageAttribute<Integer>();
        this.marketScores = new HashMap<>();
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder.setValue(displayOrder);
    }

    public void addMarketScore(Score score){
        marketScores.put(score.getScore(), score);
    }

    public MessageAttribute<Integer> getDisplayOrder() {
        return displayOrder;
    }

    public Map<String, Score> getMarketScores() {
        return marketScores;
    }

    public static Market1 parse(AAPIMessage message) throws APIException {
        AAPIMessageParser messageParser = new AAPIMessageParser(message);
        return parseMessage(messageParser);
    }

    public static Market1 parseMessage(AAPIMessageParser messageParser) throws APIException {

        Market1 toReturnParsed = new Market1();
        do {
            AAPIMessageParser.Marker currentMarker = messageParser.moveNextOrdinal();
            String fieldValue = messageParser.getFieldValue();

            switch (currentMarker.FieldOrdinal) {
                case 1:
                    toReturnParsed.setDisplayOrder(AAPIMessageHelper.parseInteger(fieldValue));
                    break;
                case 2:
                    Score score = Score.parseMessageItem(messageParser);
                    toReturnParsed.addMarketScore(score);
                    break;
                default:
                    logger.info(String.format("Attempted to parse an undefined ordinal number [%d] with field name [%s] and value [%s]", currentMarker.FieldOrdinal,  messageParser.getFieldName(), messageParser.getFieldValue()));
                    break;
            }

        } while ( messageParser.readNextRecord() );

        return toReturnParsed;
    }

    public void applyDelta(Market1 deltaMessage) {
        if(deltaMessage.getDisplayOrder().isSpecified())
            this.setDisplayOrder(deltaMessage.getDisplayOrder().getValue());
        for (Score deltaMarketScore : deltaMessage.getMarketScores().values()) {
            if(this.marketScores.containsKey(deltaMarketScore.getScore())){
                this.marketScores.put(deltaMarketScore.getScore(), deltaMarketScore);
            }else{
                this.marketScores.put(deltaMarketScore.getScore(), deltaMarketScore);
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Market1{");
        sb.append("displayOrder=").append(displayOrder);
        sb.append(", marketScores=").append(marketScores);
        sb.append('}');
        return sb.toString();
    }
}
