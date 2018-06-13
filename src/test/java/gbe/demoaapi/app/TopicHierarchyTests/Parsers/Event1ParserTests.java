package gbe.demoaapi.app.TopicHierarchyTests.Parsers;

import gbe.demoaapi.app.AAPIMessage.AAPIDataCache;
import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.AAPIMessage.Parsers.Event1Parser;
import gbe.demoaapi.app.AAPIMessage.Parsers.Market1Parser;
import gbe.demoaapi.app.TopicHierarchyTests.Event1Tests;
import gbe.demoaapi.app.TopicHierarchyTests.Market1Tests;
import org.junit.Before;
import org.junit.Test;

import static gbe.demoaapi.app.SubscriptionCommands.SubscribeDetailedMarketPricesResponseTests.validAAPICommandResponseSubscribeDetailedMarketPrices;
import static gbe.demoaapi.app.TopicHierarchyTests.Event1Tests.validAAPIEvent1ResponseFull;
import static gbe.demoaapi.app.TopicHierarchyTests.Market1Tests.validAAPIMarket1Response;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Event1ParserTests {

    private AAPIDataCache parsedDataCache;
    private Event1Parser sut;

    @Before
    public void setUp() {
        parsedDataCache = new AAPIDataCache();
        sut = new Event1Parser(parsedDataCache);
    }

    @Test
    public void parseAAPIMessageContainingValidEvent1() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPIEvent1ResponseFull);
        boolean wasParsed = sut.parse(parsedAAPIMessage);

        assertTrue(wasParsed);
        assertEquals(1,parsedDataCache.getEventIdToEvent1Map().size());
        Event1Tests.assertValidEvent1Full(parsedDataCache.getEventIdToEvent1Map().get(100003));
    }

    @Test
    public void parseAAPIMessageNotContainingEvent1() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPICommandResponseSubscribeDetailedMarketPrices);
        boolean wasParsed = sut.parse(parsedAAPIMessage);

        assertFalse(wasParsed);
        assertEquals(0,parsedDataCache.getEventIdToEvent1Map().size());
    }
}
