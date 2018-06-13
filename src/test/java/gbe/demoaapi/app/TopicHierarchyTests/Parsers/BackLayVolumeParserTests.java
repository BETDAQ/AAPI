package gbe.demoaapi.app.TopicHierarchyTests.Parsers;

import gbe.demoaapi.app.AAPIMessage.AAPIDataCache;
import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.AAPIMessage.Parsers.BackLayVolumeCurrencyParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static gbe.demoaapi.app.TopicHierarchyTests.BackLayVolumeCurrencyFormatTests.assertValidBackLayVolumeCurrency;
import static gbe.demoaapi.app.TopicHierarchyTests.BackLayVolumeCurrencyFormatTests.validAAPIResponseSubscribeDetailedMarketPrices;
import static gbe.demoaapi.app.SubscriptionCommands.SubscribeDetailedMarketPricesResponseTests.validAAPICommandResponseSubscribeDetailedMarketPrices;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BackLayVolumeParserTests {

    private AAPIDataCache parsedDataCache;
    private BackLayVolumeCurrencyParser sut;

    @Before
    public void setUp() {
        parsedDataCache = new AAPIDataCache();
        sut = new BackLayVolumeCurrencyParser(parsedDataCache);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void parseAAPIMessageContainingValidBackLayVolumeCurrencyFormat() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPIResponseSubscribeDetailedMarketPrices);
        boolean wasParsed = sut.parse(parsedAAPIMessage);

        assertTrue(wasParsed);
        assertEquals(1,parsedDataCache.getMarketIdToBackLayVolumeCurrencyFormatMap().size());
        assertValidBackLayVolumeCurrency(parsedDataCache.getMarketIdToBackLayVolumeCurrencyFormatMap().get(333542));
    }

    @Test
    public void parseAAPIMessageContainingNotAnBackLayVolumeCurrencyFormat() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPICommandResponseSubscribeDetailedMarketPrices);
        boolean wasParsed = sut.parse(parsedAAPIMessage);

        assertFalse(wasParsed);
        assertEquals(0,parsedDataCache.getMarketIdToBackLayVolumeCurrencyFormatMap().size());
    }

}
