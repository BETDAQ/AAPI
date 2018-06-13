package gbe.demoaapi.app.AAPIMessage.Parsers;

import gbe.demoaapi.app.AAPIMessage.AAPIDataCache;
import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;
import gbe.demoaapi.app.TopicHierarchy.Selection1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Selection1Parser implements RegexAAPIMessageParser{

    private final static ConsoleLogger logger = LoggerFactory.getLogger(Selection1Parser.class);

    private static final Pattern regexPattern = Pattern.compile("^.*/M/E_([0-9]+)/S/E_([0-9]+)$");

    private AAPIDataCache aapiDataCache;

    public Selection1Parser(AAPIDataCache aapiDataCache) {
        this.aapiDataCache = aapiDataCache;
    }

    @Override
    public boolean parse(AAPIMessage message) throws APIException {
        Matcher matcher = regexPattern.matcher(message.getTopicName());
        if(!matcher.find())
            return false;

        int selectionIdOfTopicReceived = Integer.parseInt(matcher.group(2));
        if(message.getNvps().size() > 0) {
            Selection1 parsedSelection1 = Selection1.parse(message);
            if (message.isLoadMessage()) {
                aapiDataCache.getSelectionIdToSelection1Map().put(selectionIdOfTopicReceived, parsedSelection1);
                logger.logObject(Selection1.class, parsedSelection1);
            } else {
                aapiDataCache.getSelectionIdToSelection1Map().get(selectionIdOfTopicReceived).applyDelta(parsedSelection1);
                aapiDataCache.addDeltaToCount();
            }
        }else{
            logger.info(String.format("The message received for MarketId: %d have no body to be parsed.", selectionIdOfTopicReceived));
        }
        return true;
    }

}
