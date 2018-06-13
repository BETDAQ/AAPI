package gbe.demoaapi.app.TopicHierarchyTests.Parsers;

import gbe.demoaapi.app.AAPIMessage.AAPIDataCache;
import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.AAPIMessage.Parsers.Selection1Parser;
import gbe.demoaapi.app.TopicHierarchyTests.Selection1Tests;
import org.junit.Before;
import org.junit.Test;

import static gbe.demoaapi.app.SubscriptionCommands.SubscribeDetailedMarketPricesResponseTests.validAAPICommandResponseSubscribeDetailedMarketPrices;
import static gbe.demoaapi.app.TopicHierarchyTests.Selection1Tests.validAAPISelection1Response;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Selection1ParserTests {

    private AAPIDataCache parsedDataCache;
    private Selection1Parser sut;

    @Before
    public void setUp() {
        parsedDataCache = new AAPIDataCache();
        sut = new Selection1Parser(parsedDataCache);
    }


    @Test
    public void parseAAPIMessageContainingValidSelection1() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPISelection1Response);
        boolean wasParsed = sut.parse(parsedAAPIMessage);

        assertTrue(wasParsed);
        assertEquals(1,parsedDataCache.getSelectionIdToSelection1Map().size());
        Selection1Tests.assertValidSelection1(parsedDataCache.getSelectionIdToSelection1Map().get(79248529));
    }

    @Test
    public void parseAAPIMessageNotContainingSelection1() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPICommandResponseSubscribeDetailedMarketPrices);
        boolean wasParsed = sut.parse(parsedAAPIMessage);

        assertFalse(wasParsed);
        assertEquals(0,parsedDataCache.getSelectionIdToSelection1Map().size());
    }

}
