package gbe.demoaapi.app.TopicHierarchy;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.AAPIMessageParser;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;

import static gbe.demoaapi.app.AAPIMessage.AAPIMessageParser.Marker;

public class Language3 {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(Language3.class);

    private MessageAttribute<String> name;
    private MessageAttribute<String> description;

    public Language3() {
        this.name = new MessageAttribute<String>();
        this.description = new MessageAttribute<String>();
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public void setDescription(String description) {
        this.description.setValue(description);
    }

    public MessageAttribute<String> getName() {
        return name;
    }

    public MessageAttribute<String> getDescription() {
        return description;
    }

    public static Language3 parse(AAPIMessage message) throws APIException {
        AAPIMessageParser messageParser = new AAPIMessageParser(message);
        return parseMessage(messageParser);
    }

    public static Language3 parseMessage(AAPIMessageParser messageParser) throws APIException {

        Language3 toReturnParsed = new Language3();
        do {
            Marker currentMarker = messageParser.moveNextOrdinal();
            String fieldValue = messageParser.getFieldValue();

            switch (currentMarker.FieldOrdinal) {
                case 1:
                    toReturnParsed.setName(fieldValue);
                    break;
                case 2:
                    toReturnParsed.setDescription(fieldValue);
                    break;
                default:
                    logger.info(String.format("Attempted to parse an undefined ordinal number [%d] with field name [%s] and value [%s]", currentMarker.FieldOrdinal,  messageParser.getFieldName(), messageParser.getFieldValue()));
                    break;
            }

        } while ( messageParser.readNextRecord() );

        return toReturnParsed;
    }

    public void applyDelta(Language3 deltaMessage) {
        if(deltaMessage.getName().isSpecified())
            this.setName(deltaMessage.getName().getValue());
        if(deltaMessage.getDescription().isSpecified())
            this.setDescription(deltaMessage.getDescription().getValue());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Language3{");
        sb.append("name=").append(name);
        sb.append(", description=").append(description);
        sb.append('}');
        return sb.toString();
    }
}
