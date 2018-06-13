package gbe.demoaapi.app.TopicHierarchy;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.AAPIMessageParser;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;

import static gbe.demoaapi.app.AAPIMessage.AAPIMessageParser.*;

public class Language6 {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(Language6.class);

    private MessageAttribute<String> name;
    private MessageAttribute<String> selectionBlurb; //this is prior to version 2.2 (in this version was introduced separate topic for blurb)

    //region Setters Getters Constructor

    public Language6() {
        this.name = new MessageAttribute<String>();
        this.selectionBlurb = new MessageAttribute<String>();
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public void setSelectionBlurb(String selectionBlurb) {
        this.selectionBlurb.setValue(selectionBlurb);
    }

    public MessageAttribute<String> getName() {
        return name;
    }

    public MessageAttribute<String> getSelectionBlurb() {
        return selectionBlurb;
    }

    //endregion

    public static Language6 parse(AAPIMessage message) throws APIException {
        AAPIMessageParser messageParser = new AAPIMessageParser(message);
        return parseMessage(messageParser);
    }

    public static Language6 parseMessage(AAPIMessageParser messageParser) throws APIException {

        Language6 toReturnParsed = new Language6();
        do {
            Marker currentMarker = messageParser.moveNextOrdinal();
            String fieldValue = messageParser.getFieldValue();

            switch (currentMarker.FieldOrdinal) {
                case 1:
                    toReturnParsed.setName(fieldValue);
                    break;
                case 2:
                    toReturnParsed.setSelectionBlurb(fieldValue);
                    break;
                default:
                    logger.info(String.format("Attempted to parse an undefined ordinal number [%d] with field name [%s] and value [%s]", currentMarker.FieldOrdinal,  messageParser.getFieldName(), messageParser.getFieldValue()));
                    break;
            }

        } while ( messageParser.readNextRecord() );

        return toReturnParsed;
    }

    public void applyDelta(Language6 deltaMessage) {
        if(deltaMessage.getName().isSpecified())
            this.setName(deltaMessage.getName().getValue());
        if(deltaMessage.getSelectionBlurb().isSpecified())
            this.setSelectionBlurb(deltaMessage.getSelectionBlurb().getValue());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Language6{");
        sb.append("name=").append(name);
        sb.append(", selectionBlurb=").append(selectionBlurb);
        sb.append('}');
        return sb.toString();
    }
}
