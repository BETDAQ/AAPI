package gbe.demoaapi.app.TopicHierarchyTests.Parsers;

import gbe.demoaapi.app.AAPIMessage.AAPIDataCache;
import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.AAPIMessage.Parsers.Language3Parser;
import gbe.demoaapi.app.TopicHierarchyTests.Language3Tests;
import org.junit.Before;
import org.junit.Test;

import static gbe.demoaapi.app.SubscriptionCommands.SubscribeDetailedMarketPricesResponseTests.validAAPICommandResponseSubscribeDetailedMarketPrices;
import static gbe.demoaapi.app.TopicHierarchyTests.Language3Tests.validAAPILanguage3Response;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Language3ParserTests {

    private AAPIDataCache parsedDataCache;
    private Language3Parser sut;

    @Before
    public void setUp() {
        parsedDataCache = new AAPIDataCache();
        sut = new Language3Parser(parsedDataCache);
    }


    @Test
    public void parseAAPIMessageContainingValidLanguage3() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPILanguage3Response);
        boolean wasParsed = sut.parse(parsedAAPIMessage);

        assertTrue(wasParsed);
        assertEquals(1,parsedDataCache.getMarketIdToLanguage3Map().size());
        Language3Tests.assertValidLanguage3(parsedDataCache.getMarketIdToLanguage3Map().get(12456387));
    }

    @Test
    public void parseAAPIMessageNotContainingLanguage3() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPICommandResponseSubscribeDetailedMarketPrices);
        boolean wasParsed = sut.parse(parsedAAPIMessage);

        assertFalse(wasParsed);
        assertEquals(0,parsedDataCache.getMarketIdToLanguage3Map().size());
    }

}
