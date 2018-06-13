package gbe.demoaapi.app.AAPIMessage.Parsers;

import gbe.demoaapi.app.AAPIMessage.AAPIDataCache;
import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;
import gbe.demoaapi.app.TopicHierarchy.SelectionBlurb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectionBlurbParser implements RegexAAPIMessageParser{

    private final static ConsoleLogger logger = LoggerFactory.getLogger(SelectionBlurbParser.class);

    private static final Pattern regexPattern = Pattern.compile("^.*/M/E_([0-9]+)/S/E_([0-9]+)/SEI/SB$");

    private AAPIDataCache aapiDataCache;

    public SelectionBlurbParser(AAPIDataCache aapiDataCache) {
        this.aapiDataCache = aapiDataCache;
    }

    @Override
    public boolean parse(AAPIMessage message) throws APIException {
        Matcher matcher = regexPattern.matcher(message.getTopicName());
        if(!matcher.find())
            return false;

        int selectionIdOfTopicReceived = Integer.parseInt(matcher.group(2));
        if(message.getNvps().size() > 0) {
            SelectionBlurb parsedSelectionBlurb = SelectionBlurb.parse(message);
            if (message.isLoadMessage()) {
                aapiDataCache.getSelectionIdToSelectionBlurbMap().put(selectionIdOfTopicReceived, parsedSelectionBlurb);
                logger.logObject(SelectionBlurb.class, parsedSelectionBlurb);
            } else {
                aapiDataCache.getSelectionIdToSelectionBlurbMap().get(selectionIdOfTopicReceived).applyDelta(parsedSelectionBlurb);
                aapiDataCache.addDeltaToCount();
            }
        }else{
            logger.info(String.format("The message received for MarketId: %d have no body to be parsed.", selectionIdOfTopicReceived));
        }
        return true;
    }

}
