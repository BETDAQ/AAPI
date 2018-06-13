package gbe.demoaapi.app.TopicHierarchy;

import gbe.demoaapi.app.AAPIMessage.AAPIMessageHelper;
import gbe.demoaapi.app.AAPIMessage.AAPIMessageParser;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;

import java.math.BigDecimal;

public class DisplayPriceAndStake {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(DisplayPriceAndStake.class);

    private String displayPrice;
    private BigDecimal stake;

    public void setDisplayPrice(String displayPrice) {
        this.displayPrice = displayPrice;
    }

    public void setStake(BigDecimal stake) {
        this.stake = stake;
    }

    public String getDisplayPrice() {
        return displayPrice;
    }

    public BigDecimal getStake() {
        return stake;
    }

    public static DisplayPriceAndStake parseMessageItem(AAPIMessageParser messageParser) throws APIException {

        DisplayPriceAndStake toReturnParsed = new DisplayPriceAndStake();
        do {
            AAPIMessageParser.Marker currentMarker = messageParser.moveNextOrdinal();
            String fieldValue = messageParser.getFieldValue();

            switch (currentMarker.FieldOrdinal) {
                case 1:
                    toReturnParsed.setDisplayPrice(fieldValue);
                    break;
                case 2:
                    toReturnParsed.setStake(AAPIMessageHelper.parseBigDecimal(fieldValue));
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
        final StringBuilder sb = new StringBuilder("DisplayPriceAndStake{");
        sb.append("displayPrice='").append(displayPrice).append('\'');
        sb.append(", stake=").append(stake);
        sb.append('}');
        return sb.toString();
    }
}
