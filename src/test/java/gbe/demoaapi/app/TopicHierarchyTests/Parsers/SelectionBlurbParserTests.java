package gbe.demoaapi.app.TopicHierarchyTests.Parsers;

import gbe.demoaapi.app.AAPIMessage.AAPIDataCache;
import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.AAPIMessage.Parsers.SelectionBlurbParser;
import gbe.demoaapi.app.TopicHierarchyTests.SelectionBlurbTests;
import org.junit.Before;
import org.junit.Test;

import static gbe.demoaapi.app.SubscriptionCommands.SubscribeDetailedMarketPricesResponseTests.validAAPICommandResponseSubscribeDetailedMarketPrices;
import static gbe.demoaapi.app.TopicHierarchyTests.SelectionBlurbTests.validAAPISelectionBlurbResponse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SelectionBlurbParserTests {

    private AAPIDataCache parsedDataCache;
    private SelectionBlurbParser sut;

    @Before
    public void setUp() {
        parsedDataCache = new AAPIDataCache();
        sut = new SelectionBlurbParser(parsedDataCache);
    }


    @Test
    public void parseAAPIMessageContainingValidSelectionBlurb() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPISelectionBlurbResponse);
        boolean wasParsed = sut.parse(parsedAAPIMessage);

        assertTrue(wasParsed);
        assertEquals(1,parsedDataCache.getSelectionIdToSelectionBlurbMap().size());
        SelectionBlurbTests.assertValidBlurb(parsedDataCache.getSelectionIdToSelectionBlurbMap().get(2061914));
    }

    @Test
    public void parseAAPIMessageNotContainingSelectionBlurb() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPICommandResponseSubscribeDetailedMarketPrices);
        boolean wasParsed = sut.parse(parsedAAPIMessage);

        assertFalse(wasParsed);
        assertEquals(0,parsedDataCache.getSelectionIdToSelectionBlurbMap().size());
    }

}
