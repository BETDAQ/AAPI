package gbe.demoaapi.app.TopicHierarchyTests.Parsers;

import gbe.demoaapi.app.AAPIMessage.AAPIDataCache;
import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.AAPIMessage.Parsers.MExchangeInfoParser;
import gbe.demoaapi.app.TopicHierarchyTests.MExchangeInfoTests;
import org.junit.Before;
import org.junit.Test;

import static gbe.demoaapi.app.SubscriptionCommands.SubscribeDetailedMarketPricesResponseTests.validAAPICommandResponseSubscribeDetailedMarketPrices;
import static gbe.demoaapi.app.TopicHierarchyTests.MExchangeInfoTests.validAAPIMExchangeInfoResponseFull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MExchangeInfoParserTests {

    private AAPIDataCache parsedDataCache;
    private MExchangeInfoParser sut;

    @Before
    public void setUp() {
        parsedDataCache = new AAPIDataCache();
        sut = new MExchangeInfoParser(parsedDataCache);
    }


    @Test
    public void parseAAPIMessageContainingValidMExchangeInfo() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPIMExchangeInfoResponseFull);
        boolean wasParsed = sut.parse(parsedAAPIMessage);

        assertTrue(wasParsed);
        assertEquals(1,parsedDataCache.getMarketIdToMExchangeInfoMap().size());
        MExchangeInfoTests.assertValidMExchangeInfoPartialFull(parsedDataCache.getMarketIdToMExchangeInfoMap().get(314610));
    }

    @Test
    public void parseAAPIMessageNotContainingMExchangeInfo() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPICommandResponseSubscribeDetailedMarketPrices);
        boolean wasParsed = sut.parse(parsedAAPIMessage);

        assertFalse(wasParsed);
        assertEquals(0,parsedDataCache.getMarketIdToMExchangeInfoMap().size());
    }

}
