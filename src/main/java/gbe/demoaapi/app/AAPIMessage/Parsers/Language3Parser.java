package gbe.demoaapi.app.AAPIMessage.Parsers;

import gbe.demoaapi.app.AAPIMessage.AAPIDataCache;
import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;
import gbe.demoaapi.app.TopicHierarchy.Language3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Language3Parser implements RegexAAPIMessageParser {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(Language3Parser.class);

    private static final Pattern regexPattern = Pattern.compile("^.*/M/E_([0-9]+)/MEI/MEL/([a-z\\-\\-]{2})$");

    private AAPIDataCache aapiDataCache;

    public Language3Parser(AAPIDataCache aapiDataCache) {
        this.aapiDataCache = aapiDataCache;
    }

    @Override
    public boolean parse(AAPIMessage message) throws APIException {
        Matcher matcher = regexPattern.matcher(message.getTopicName());
        if(!matcher.find())
            return false;

        int marketIdOfTopicReceived = Integer.parseInt(matcher.group(1));
        if(message.getNvps().size() > 0) {
            Language3 parsedLanguage3 = Language3.parse(message);
            if (message.isLoadMessage()) {
                aapiDataCache.getMarketIdToLanguage3Map().put(marketIdOfTopicReceived, parsedLanguage3);
                logger.logObject(Language3.class, parsedLanguage3);
            } else {
                aapiDataCache.getMarketIdToLanguage3Map().get(marketIdOfTopicReceived).applyDelta(parsedLanguage3);
                aapiDataCache.addDeltaToCount();
            }
        }else{
            logger.info(String.format("The message received for MarketId: %d have no body to be parsed.", marketIdOfTopicReceived));
        }
        return true;
    }


}
