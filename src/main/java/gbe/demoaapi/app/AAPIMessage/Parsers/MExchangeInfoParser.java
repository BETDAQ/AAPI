package gbe.demoaapi.app.AAPIMessage.Parsers;

import gbe.demoaapi.app.AAPIMessage.AAPIDataCache;
import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;
import gbe.demoaapi.app.TopicHierarchy.MExchangeInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MExchangeInfoParser implements RegexAAPIMessageParser{

    private final static ConsoleLogger logger = LoggerFactory.getLogger(MExchangeInfoParser.class);

    private static final Pattern regexPattern = Pattern.compile("^.*/M/E_([0-9]*)/MEI$");

    private AAPIDataCache aapiDataCache;

    public MExchangeInfoParser(AAPIDataCache aapiDataCache) {
        this.aapiDataCache = aapiDataCache;
    }

    @Override
    public boolean parse(AAPIMessage message) throws APIException {
        Matcher matcher = regexPattern.matcher(message.getTopicName());
        if(!matcher.find())
            return false;

        int marketIdOfTopicReceived = Integer.parseInt(matcher.group(1));
        if(message.getNvps().size() > 0) {
            MExchangeInfo parsedMExchangeInfo = MExchangeInfo.parse(message);
            if (message.isLoadMessage()) {
                aapiDataCache.getMarketIdToMExchangeInfoMap().put(marketIdOfTopicReceived, parsedMExchangeInfo);
                logger.logObject(MExchangeInfo.class, parsedMExchangeInfo);
            } else {
                aapiDataCache.getMarketIdToMExchangeInfoMap().get(marketIdOfTopicReceived).applyDelta(parsedMExchangeInfo);
                aapiDataCache.addDeltaToCount();
            }
        }else{
            logger.info(String.format("The message received for MarketId: %d have no body to be parsed.", marketIdOfTopicReceived));
        }
        return true;
    }
}
