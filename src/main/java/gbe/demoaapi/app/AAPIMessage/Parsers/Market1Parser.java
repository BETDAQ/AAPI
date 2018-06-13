package gbe.demoaapi.app.AAPIMessage.Parsers;

import gbe.demoaapi.app.AAPIMessage.AAPIDataCache;
import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;
import gbe.demoaapi.app.TopicHierarchy.Market1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Market1Parser implements RegexAAPIMessageParser {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(BackLayVolumeCurrencyParser.class);

    private static final Pattern regexPattern = Pattern.compile("^.*/E/E_([0-9]*)/M/E_([0-9]*)$");

    private AAPIDataCache aapiDataCache;

    public Market1Parser(AAPIDataCache aapiDataCache) {
        this.aapiDataCache = aapiDataCache;
    }

    @Override
    public boolean parse(AAPIMessage message) throws APIException {
        Matcher matcher = regexPattern.matcher(message.getTopicName());
        if(!matcher.find())
            return false;

        int marketIdOfTopicReceived = Integer.parseInt(matcher.group(2));
        if(message.getNvps().size() > 0) {
            Market1 parsedMarket1 = Market1.parse(message);
            if (message.isLoadMessage()) {
                aapiDataCache.getMarketIdToMarket1Map().put(marketIdOfTopicReceived, parsedMarket1);
                logger.logObject(Market1.class, parsedMarket1);
            } else {
                aapiDataCache.getMarketIdToMarket1Map().get(marketIdOfTopicReceived).applyDelta(parsedMarket1);
                aapiDataCache.addDeltaToCount();
            }
        }else{
            logger.info(String.format("The message received for MarketId: %d have no body to be parsed.", marketIdOfTopicReceived));
        }
        return true;
    }

}
