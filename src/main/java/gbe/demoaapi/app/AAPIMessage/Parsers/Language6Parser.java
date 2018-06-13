package gbe.demoaapi.app.AAPIMessage.Parsers;

import gbe.demoaapi.app.AAPIMessage.AAPIDataCache;
import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;
import gbe.demoaapi.app.TopicHierarchy.Language6;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Language6Parser implements RegexAAPIMessageParser{

    private final static ConsoleLogger logger = LoggerFactory.getLogger(Language6Parser.class);

    private static final Pattern regexPattern = Pattern.compile("^.*/M/E_([0-9]+)/S/E_([0-9]*)/SEI/SEL/([a-z\\-\\-]{2})$");

    private AAPIDataCache aapiDataCache;

    public Language6Parser(AAPIDataCache aapiDataCache) {
        this.aapiDataCache = aapiDataCache;
    }

    @Override
    public boolean parse(AAPIMessage message) throws APIException {
        Matcher matcher = regexPattern.matcher(message.getTopicName());
        if(!matcher.find())
            return false;

        int selectionIdOfTopicReceived = Integer.parseInt(matcher.group(2));
        if(message.getNvps().size() > 0) {
            Language6 parsedLanguage6 = Language6.parse(message);
            if (message.isLoadMessage()) {
                aapiDataCache.getSelectionIdToLanguage6Map().put(selectionIdOfTopicReceived, parsedLanguage6);
                logger.logObject(Language6.class, parsedLanguage6);
            } else {
                aapiDataCache.getSelectionIdToLanguage6Map().get(selectionIdOfTopicReceived).applyDelta(parsedLanguage6);
                aapiDataCache.addDeltaToCount();
            }
        }else{
            logger.info(String.format("The message received for MarketId: %d have no body to be parsed.", selectionIdOfTopicReceived));
        }
        return true;
    }

}
