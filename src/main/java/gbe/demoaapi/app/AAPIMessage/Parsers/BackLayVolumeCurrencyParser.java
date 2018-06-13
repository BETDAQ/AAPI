package gbe.demoaapi.app.AAPIMessage.Parsers;


import gbe.demoaapi.app.AAPIMessage.AAPIDataCache;
import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.TopicHierarchy.BackLayVolumeCurrencyFormat;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BackLayVolumeCurrencyParser implements RegexAAPIMessageParser {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(BackLayVolumeCurrencyParser.class);

    private static final Pattern regexPattern = Pattern.compile("^.*/M/E_([0-9]*)/MEI/MDP/(\\d\\d?)_(\\d\\d?)_(\\d*)_([A-Z]{3})_([1-3])$");

    private AAPIDataCache aapiDataCache;

    public BackLayVolumeCurrencyParser(AAPIDataCache aapiDataCache) {
        this.aapiDataCache = aapiDataCache;
    }

    @Override
    public boolean parse(AAPIMessage message) throws APIException {
        Matcher matcher = regexPattern.matcher(message.getTopicName());
        if(!matcher.find())
            return false;

        int marketIdOfTopicReceived = Integer.parseInt(matcher.group(1));
        if(message.getNvps().size() > 0) {
            BackLayVolumeCurrencyFormat parsedMessage = BackLayVolumeCurrencyFormat.parse(message);
            if (message.isLoadMessage()) {
                aapiDataCache.getMarketIdToBackLayVolumeCurrencyFormatMap().put(marketIdOfTopicReceived, parsedMessage);
                logger.logObject(BackLayVolumeCurrencyFormat.class, parsedMessage);
            } else {
                aapiDataCache.getMarketIdToBackLayVolumeCurrencyFormatMap().get(marketIdOfTopicReceived).applyDelta(parsedMessage);
                aapiDataCache.addDeltaToCount();
            }
        }else{
            logger.info(String.format("The message received for MarketId: %d have no body to be parsed.", marketIdOfTopicReceived));
        }
        return true;
    }



}
