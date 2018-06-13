package gbe.demoaapi.app.TopicHierarchyTests.Parsers;

import gbe.demoaapi.app.AAPIMessage.AAPIDataCache;
import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.AAPIMessage.Parsers.Language6Parser;
import gbe.demoaapi.app.TopicHierarchyTests.Language6Tests;
import org.junit.Before;
import org.junit.Test;

import static gbe.demoaapi.app.SubscriptionCommands.SubscribeDetailedMarketPricesResponseTests.validAAPICommandResponseSubscribeDetailedMarketPrices;
import static gbe.demoaapi.app.TopicHierarchyTests.Language6Tests.validAAPILanguage6ResponsePrior22;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Language6ParserTests {

    private AAPIDataCache parsedDataCache;
    private Language6Parser sut;

    @Before
    public void setUp() {
        parsedDataCache = new AAPIDataCache();
        sut = new Language6Parser(parsedDataCache);
    }


    @Test
    public void parseAAPIMessageContainingValidLanguage6() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPILanguage6ResponsePrior22);
        boolean wasParsed = sut.parse(parsedAAPIMessage);

        assertTrue(wasParsed);
        assertEquals(1,parsedDataCache.getSelectionIdToLanguage6Map().size());
        Language6Tests.assertValidLanguage6(parsedDataCache.getSelectionIdToLanguage6Map().get(79248533));
    }

    @Test
    public void parseAAPIMessageNotContainingLanguage6() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPICommandResponseSubscribeDetailedMarketPrices);
        boolean wasParsed = sut.parse(parsedAAPIMessage);

        assertFalse(wasParsed);
        assertEquals(0,parsedDataCache.getSelectionIdToLanguage6Map().size());
    }

}
