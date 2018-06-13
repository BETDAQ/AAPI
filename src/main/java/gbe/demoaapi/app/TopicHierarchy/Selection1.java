package gbe.demoaapi.app.TopicHierarchy;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.AAPIMessageHelper;
import gbe.demoaapi.app.AAPIMessage.AAPIMessageParser;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;

public class Selection1 {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(Language3.class);

    private MessageAttribute<Integer> displayOrder;
    private MessageAttribute<String> iconReference;

    //region Setters Getters Constructor

    public Selection1() {
        this.displayOrder = new MessageAttribute<Integer>();
        this.iconReference = new MessageAttribute<String>();
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder.setValue(displayOrder);
    }

    public void setIconReference(String iconReference) {
        this.iconReference.setValue(iconReference);
    }

    public MessageAttribute<Integer> getDisplayOrder() {
        return displayOrder;
    }

    public MessageAttribute<String> getIconReference() {
        return iconReference;
    }

    //endregion

    public static Selection1 parse(AAPIMessage message) throws APIException {
        AAPIMessageParser messageParser = new AAPIMessageParser(message);
        return parseMessage(messageParser);
    }

    public static Selection1 parseMessage(AAPIMessageParser messageParser) throws APIException {

        Selection1 toReturnParsed = new Selection1();
        do {
            AAPIMessageParser.Marker currentMarker = messageParser.moveNextOrdinal();
            String fieldValue = messageParser.getFieldValue();

            switch (currentMarker.FieldOrdinal) {
                case 1:
                    toReturnParsed.setDisplayOrder(AAPIMessageHelper.parseInteger(fieldValue));
                    break;
                case 2:
                    toReturnParsed.setIconReference(fieldValue);
                    break;
                default:
                    logger.info(String.format("Attempted to parse an undefined ordinal number [%d] with field name [%s] and value [%s]", currentMarker.FieldOrdinal,  messageParser.getFieldName(), messageParser.getFieldValue()));
                    break;
            }

        } while ( messageParser.readNextRecord() );

        return toReturnParsed;
    }

    public void applyDelta(Selection1 deltaMessage) {
        if(deltaMessage.getDisplayOrder().isSpecified())
            this.setDisplayOrder(deltaMessage.getDisplayOrder().getValue());
        if(deltaMessage.getIconReference().isSpecified())
            this.setIconReference(deltaMessage.getIconReference().getValue());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Selection1{");
        sb.append("displayOrder=").append(displayOrder);
        sb.append(", iconReference=").append(iconReference);
        sb.append('}');
        return sb.toString();
    }
}
