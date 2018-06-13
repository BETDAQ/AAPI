package gbe.demoaapi.app.AAPIMessage.Parsers;

import gbe.demoaapi.app.AAPIMessage.AAPIDataCache;
import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;
import gbe.demoaapi.app.TopicHierarchy.EExchangeInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EExchangeInfoParser implements RegexAAPIMessageParser {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(EExchangeInfoParser.class);

    private static final Pattern regexPattern = Pattern.compile("^.*/E/E_([0-9]*)/EEI$");

    private AAPIDataCache aapiDataCache;

    public EExchangeInfoParser(AAPIDataCache aapiDataCache) {
        this.aapiDataCache = aapiDataCache;
    }

    @Override
    public boolean parse(AAPIMessage message) throws APIException {
        Matcher matcher = regexPattern.matcher(message.getTopicName());
        if(!matcher.find())
            return false;

        int eventIdOfTopicReceived = Integer.parseInt(matcher.group(1));
        if(message.getNvps().size() > 0) {
            EExchangeInfo eExchangeInfo = EExchangeInfo.parse(message);
            if (message.isLoadMessage()) {
                aapiDataCache.getEventIdToEExchangeIngoMap().put(eventIdOfTopicReceived, eExchangeInfo);
                logger.logObject(EExchangeInfo.class, eExchangeInfo);
            } else {
                aapiDataCache.getEventIdToEExchangeIngoMap().get(eventIdOfTopicReceived).applyDelta(eExchangeInfo);
                aapiDataCache.addDeltaToCount();
            }
        }else{
            logger.info(String.format("The message received for EventId: %d have no body to be parsed.", eventIdOfTopicReceived));
        }
        return true;
    }
}
