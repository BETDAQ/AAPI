package gbe.demoaapi.app.TopicHierarchy;

import gbe.demoaapi.app.AAPIMessage.AAPIMessageHelper;
import gbe.demoaapi.app.AAPIMessage.AAPIMessageParser;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;


import java.math.BigDecimal;
import java.util.HashMap;

import java.util.Map;

public class SelectionDetailedPrice {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(SelectionDetailedPrice.class);

    private long selectionId;
    private Map<String ,DisplayPriceAndStake> backedPrices;
    private Map<String, DisplayPriceAndStake> layedPrices;

    public SelectionDetailedPrice() {
        backedPrices = new HashMap<String ,DisplayPriceAndStake>();
        layedPrices = new HashMap<String ,DisplayPriceAndStake>();
    }

    public void setSelectionId(long selectionId) {
        this.selectionId = selectionId;
    }

    public Map<String, DisplayPriceAndStake> getBackedPrices() {
        return backedPrices;
    }

    public Map<String, DisplayPriceAndStake> getLayedPrices() {
        return layedPrices;
    }

    public void addBackedPrice(DisplayPriceAndStake backedPriceToAdd){
        backedPrices.put(backedPriceToAdd.getDisplayPrice() ,backedPriceToAdd);
    }

    public void addLayedPrice(DisplayPriceAndStake layedPriceToAdd){
        layedPrices.put(layedPriceToAdd.getDisplayPrice() ,layedPriceToAdd);
    }

    public long getSelectionId() {
        return selectionId;
    }

    public DisplayPriceAndStake getBackedPriceWithKey(String key){
        return backedPrices.get(key);
    }

    public DisplayPriceAndStake getLayedPriceWithKey(String key){
        return layedPrices.get(key);
    }

    public static SelectionDetailedPrice parseMessageItem(AAPIMessageParser messageParser) throws APIException {

        SelectionDetailedPrice toReturnParsed = new SelectionDetailedPrice();

        do {
            AAPIMessageParser.Marker currentMarker = messageParser.moveNextOrdinal();
            String fieldValue = messageParser.getFieldValue();

            switch (currentMarker.FieldOrdinal) {
                case 1:
                    toReturnParsed.setSelectionId(AAPIMessageHelper.parseLong(fieldValue));
                    break;
                case 2:
                    DisplayPriceAndStake backedPrice = DisplayPriceAndStake.parseMessageItem(messageParser);
                    toReturnParsed.addBackedPrice(backedPrice);
                    break;
                case 3:
                    DisplayPriceAndStake layedPrice = DisplayPriceAndStake.parseMessageItem(messageParser);
                    toReturnParsed.addLayedPrice(layedPrice);
                    break;
                default:
                    logger.info(String.format("Attempted to parse an undefined ordinal number [%d] with field name [%s] and value [%s]", currentMarker.FieldOrdinal,  messageParser.getFieldName(), messageParser.getFieldValue()));
                    break;
            }

        } while ( messageParser.readNextRecord() );

        return toReturnParsed;
    }

    public void applyDelta(SelectionDetailedPrice deltaMessage) {
        for (DisplayPriceAndStake deltaDisplayPriceAndStake : deltaMessage.getBackedPrices().values()) {
            updateOrRemoveFromPrices(this.backedPrices, deltaDisplayPriceAndStake);
        }
        for (DisplayPriceAndStake deltaDisplayPriceAndStake : deltaMessage.getLayedPrices().values()) {
            updateOrRemoveFromPrices(this.layedPrices, deltaDisplayPriceAndStake);
        }
    }

    public void updateOrRemoveFromPrices(Map<String ,DisplayPriceAndStake> prices, DisplayPriceAndStake deltaDisplayPriceAndStake){
        if(prices.containsKey(deltaDisplayPriceAndStake.getDisplayPrice())){
            if(deltaDisplayPriceAndStake.getStake() != null && !deltaDisplayPriceAndStake.getStake().equals(BigDecimal.ZERO)){
                prices.put(deltaDisplayPriceAndStake.getDisplayPrice() ,deltaDisplayPriceAndStake);
            }else{
                prices.remove(deltaDisplayPriceAndStake.getDisplayPrice());
            }
        }else{//new price add it
            prices.put(deltaDisplayPriceAndStake.getDisplayPrice(), deltaDisplayPriceAndStake);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SelectionDetailedPrice{");
        sb.append("selectionId=").append(selectionId);
        sb.append(", backedPrices=").append(backedPrices);
        sb.append(", layedPrices=").append(layedPrices);
        sb.append('}');
        return sb.toString();
    }
}
