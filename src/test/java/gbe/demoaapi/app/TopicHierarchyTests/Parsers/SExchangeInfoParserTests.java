package gbe.demoaapi.app.TopicHierarchyTests.Parsers;

import gbe.demoaapi.app.AAPIMessage.AAPIDataCache;
import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.AAPIMessage.Parsers.SExchangeInfoParser;
import gbe.demoaapi.app.TopicHierarchyTests.SExchangeInfoTests;
import org.junit.Before;
import org.junit.Test;

import static gbe.demoaapi.app.SubscriptionCommands.SubscribeDetailedMarketPricesResponseTests.validAAPICommandResponseSubscribeDetailedMarketPrices;
import static gbe.demoaapi.app.TopicHierarchyTests.SExchangeInfoTests.validAAPISExchangeInfoResponseFull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SExchangeInfoParserTests {

    private AAPIDataCache parsedDataCache;
    private SExchangeInfoParser sut;

    @Before
    public void setUp() {
        parsedDataCache = new AAPIDataCache();
        sut = new SExchangeInfoParser(parsedDataCache);
    }


    @Test
    public void parseAAPIMessageContainingValidSExchangeInfo() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPISExchangeInfoResponseFull);
        boolean wasParsed = sut.parse(parsedAAPIMessage);

        assertTrue(wasParsed);
        assertEquals(1,parsedDataCache.getSelectionIdToSExchangeInfoMap().size());
        SExchangeInfoTests.assertValidSExchangeInfo(parsedDataCache.getSelectionIdToSExchangeInfoMap().get(79248529));
    }

    @Test
    public void parseAAPIMessageNotContainingSExchangeInfo() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPICommandResponseSubscribeDetailedMarketPrices);
        boolean wasParsed = sut.parse(parsedAAPIMessage);

        assertFalse(wasParsed);
        assertEquals(0,parsedDataCache.getSelectionIdToSExchangeInfoMap().size());
    }


}
