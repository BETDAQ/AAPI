package gbe.demoaapi.app.TopicHierarchy;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.AAPIMessageParser;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;

import static gbe.demoaapi.app.AAPIMessage.AAPIMessageParser.*;

public class SelectionBlurb {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(SelectionBlurb.class);

    private MessageAttribute<String> blurb;

    //region Setters Getters Constructor

    public SelectionBlurb() {
        this.blurb = new MessageAttribute<String>();
    }

    public void setBlurb(String blurb) {
        this.blurb.setValue(blurb);
    }

    public MessageAttribute<String> getBlurb() {
        return blurb;
    }

    //endregion

    public static SelectionBlurb parse(AAPIMessage message) throws APIException {
        AAPIMessageParser messageParser = new AAPIMessageParser(message);
        return parseMessage(messageParser);
    }

    public static SelectionBlurb parseMessage(AAPIMessageParser messageParser) throws APIException {

        SelectionBlurb toReturnParsed = new SelectionBlurb();
        do {
            Marker currentMarker = messageParser.moveNextOrdinal();
            String fieldValue = messageParser.getFieldValue();

            switch (currentMarker.FieldOrdinal) {
                case 1:
                    toReturnParsed.setBlurb(fieldValue);
                    break;
                default:
                    logger.info(String.format("Attempted to parse an undefined ordinal number [%d] with field name [%s] and value [%s]", currentMarker.FieldOrdinal,  messageParser.getFieldName(), messageParser.getFieldValue()));
                    break;
            }

        } while ( messageParser.readNextRecord() );

        return toReturnParsed;
    }

    public void applyDelta(SelectionBlurb deltaMessage) {
        if(deltaMessage.getBlurb().isSpecified())
            this.setBlurb(deltaMessage.getBlurb().getValue());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SelectionBlurb{");
        sb.append("blurb=").append(blurb);
        sb.append('}');
        return sb.toString();
    }
}
