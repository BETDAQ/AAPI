package gbe.demoaapi.app.AAPIMessage.Parsers;

import gbe.demoaapi.app.AAPIMessage.AAPIDataCache;
import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;
import gbe.demoaapi.app.TopicHierarchy.Event1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Event1Parser implements RegexAAPIMessageParser {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(Event1Parser.class);

    private static final Pattern regexPattern = Pattern.compile("^.*/E/E_([0-9]*)$");

    private AAPIDataCache aapiDataCache;

    public Event1Parser(AAPIDataCache aapiDataCache) {
        this.aapiDataCache = aapiDataCache;
    }

    @Override
    public boolean parse(AAPIMessage message) throws APIException {
        Matcher matcher = regexPattern.matcher(message.getTopicName());
        if(!matcher.find())
            return false;

        int eventIdOfTopicReceived = Integer.parseInt(matcher.group(1));
        if(message.getNvps().size() > 0) {
            Event1 parsedEvent1 = Event1.parse(message);
            if (message.isLoadMessage()) {
                aapiDataCache.getEventIdToEvent1Map().put(eventIdOfTopicReceived, parsedEvent1);
                logger.logObject(Event1.class, parsedEvent1);
            } else {
                aapiDataCache.getEventIdToEvent1Map().get(eventIdOfTopicReceived).applyDelta(parsedEvent1);
                aapiDataCache.addDeltaToCount();
            }
        }else{
            logger.info(String.format("The message received for EventId: %d have no body to be parsed.", eventIdOfTopicReceived));
        }
        return true;
    }

}
