package gbe.demoaapi.app.AAPIMessage.Parsers;

import gbe.demoaapi.app.AAPIMessage.AAPIDataCache;
import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;
import gbe.demoaapi.app.TopicHierarchy.SExchangeInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SExchangeInfoParser implements RegexAAPIMessageParser{

    private final static ConsoleLogger logger = LoggerFactory.getLogger(SExchangeInfoParser.class);

    private static final Pattern regexPattern = Pattern.compile("^.*/S/E_([0-9]+)/SEI$");

    private AAPIDataCache aapiDataCache;

    public SExchangeInfoParser(AAPIDataCache aapiDataCache) {
        this.aapiDataCache = aapiDataCache;
    }

    @Override
    public boolean parse(AAPIMessage message) throws APIException {
        Matcher matcher = regexPattern.matcher(message.getTopicName());
        if(!matcher.find())
            return false;

        int selectionIdOfTopicReceived = Integer.parseInt(matcher.group(1));
        if(message.getNvps().size() > 0) {
            SExchangeInfo parsedSExchangeInfo = SExchangeInfo.parse(message);
            if (message.isLoadMessage()) {
                aapiDataCache.getSelectionIdToSExchangeInfoMap().put(selectionIdOfTopicReceived, parsedSExchangeInfo);
                logger.logObject(SExchangeInfo.class, parsedSExchangeInfo);
            } else {
                aapiDataCache.getSelectionIdToSExchangeInfoMap().get(selectionIdOfTopicReceived).applyDelta(parsedSExchangeInfo);
                aapiDataCache.addDeltaToCount();
            }
        }else{
            logger.info(String.format("The message received for MarketId: %d have no body to be parsed.", selectionIdOfTopicReceived));
        }
        return true;
    }

}
