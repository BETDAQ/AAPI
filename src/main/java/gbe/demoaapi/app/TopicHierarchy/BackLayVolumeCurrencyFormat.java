package gbe.demoaapi.app.TopicHierarchy;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.AAPIMessageParser;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static gbe.demoaapi.app.AAPIMessage.AAPIMessageParser.Marker;

public class BackLayVolumeCurrencyFormat {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(BackLayVolumeCurrencyFormat.class);

    private Map<Long , SelectionDetailedPrice> selectionDetailedPrices;


    public BackLayVolumeCurrencyFormat() {
        this.selectionDetailedPrices = new HashMap<Long , SelectionDetailedPrice>();
    }

    public void addSExchangeInfo(Long selectionId, SelectionDetailedPrice toAdd){
        selectionDetailedPrices.put(selectionId, toAdd);
    }


    public Map<Long, SelectionDetailedPrice> getSelectionDetailedPrices() {
        return selectionDetailedPrices;
    }

    public SelectionDetailedPrice getSelectionDetailedPriceWithKey(Long key) {
        return selectionDetailedPrices.get(key);
    }

    public static BackLayVolumeCurrencyFormat parse(AAPIMessage message) throws APIException {
        AAPIMessageParser messageParser = new AAPIMessageParser(message);
        return parseMessage(messageParser);
    }

    public static BackLayVolumeCurrencyFormat parseMessage(AAPIMessageParser messageParser) throws APIException {

        BackLayVolumeCurrencyFormat toReturnParsed = new BackLayVolumeCurrencyFormat();
        do {
            Marker currentMarker = messageParser.moveNextOrdinal();

            switch (currentMarker.FieldOrdinal) {
                case 1:
                    SelectionDetailedPrice selectionDetailedPrice = SelectionDetailedPrice.parseMessageItem(messageParser);
                    toReturnParsed.addSExchangeInfo(selectionDetailedPrice.getSelectionId() ,selectionDetailedPrice);
                    break;
                default:
                    logger.info(String.format("Attempted to parse an undefined ordinal number [%d] with field name [%s] and value [%s]", currentMarker.FieldOrdinal,  messageParser.getFieldName(), messageParser.getFieldValue()));
                    break;
            }

        } while ( messageParser.readNextRecord() );

        return toReturnParsed;
    }
    //if the delta contains selection id it means it have changed
    public void applyDelta(BackLayVolumeCurrencyFormat deltaMessage) {

        for (SelectionDetailedPrice deltaSelectionDetailedPrice : deltaMessage.getSelectionDetailedPrices().values()) {
            if(this.selectionDetailedPrices.containsKey(deltaSelectionDetailedPrice.getSelectionId())){
                this.selectionDetailedPrices.get(deltaSelectionDetailedPrice.getSelectionId()).applyDelta(deltaSelectionDetailedPrice);
            }else{//does not contain it is new selectionDetailedPrice
                this.selectionDetailedPrices.put(deltaSelectionDetailedPrice.getSelectionId(), deltaSelectionDetailedPrice);
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BackLayVolumeCurrencyFormat{");
        sb.append("selectionDetailedPrices=").append(selectionDetailedPrices);
        sb.append('}');
        return sb.toString();
    }

}
