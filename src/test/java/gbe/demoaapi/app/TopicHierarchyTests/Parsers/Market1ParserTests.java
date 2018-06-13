package gbe.demoaapi.app.TopicHierarchyTests.Parsers;

import gbe.demoaapi.app.AAPIMessage.AAPIDataCache;
import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.AAPIMessage.Parsers.Market1Parser;
import gbe.demoaapi.app.TopicHierarchyTests.Market1Tests;
import org.junit.Before;
import org.junit.Test;

import static gbe.demoaapi.app.TopicHierarchyTests.Market1Tests.validAAPIMarket1Response;
import static gbe.demoaapi.app.SubscriptionCommands.SubscribeDetailedMarketPricesResponseTests.validAAPICommandResponseSubscribeDetailedMarketPrices;
import static org.junit.Assert.*;

public class Market1ParserTests {

    private AAPIDataCache parsedDataCache;
    private Market1Parser sut;

    @Before
    public void setUp() {
        parsedDataCache = new AAPIDataCache();
        sut = new Market1Parser(parsedDataCache);
    }


    @Test
    public void parseAAPIMessageContainingValidMarket1() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPIMarket1Response);
        boolean wasParsed = sut.parse(parsedAAPIMessage);

        assertTrue(wasParsed);
        assertEquals(1,parsedDataCache.getMarketIdToMarket1Map().size());
        Market1Tests.assertValidMarket1(parsedDataCache.getMarketIdToMarket1Map().get(314610));
    }

    @Test
    public void parseAAPIMessageNotContainingMarket1() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPICommandResponseSubscribeDetailedMarketPrices);
        boolean wasParsed = sut.parse(parsedAAPIMessage);

        assertFalse(wasParsed);
        assertEquals(0,parsedDataCache.getMarketIdToMarket1Map().size());
    }


}
