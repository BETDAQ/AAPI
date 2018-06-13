package gbe.demoaapi.app.TopicHierarchyTests;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;

import gbe.demoaapi.app.TopicHierarchy.Market1;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Market1Tests {

    public static final String validAAPIMarket1Response = "AAPI/6/E/E_1/E/E_100003/E/E_190659/E/E_3969213/E/E_4088802/M/E_314610\u0002\u0002T\u0001" +
            "1\u00021\u0001" +
            "2V1-1\u00022018-06-20T18:00:00.000Z\u0001" +
            "2V1-2\u00022-2\u0001";

    public static final String validAAPIMarket1Delta = "AAPI/6/E/E_1/E/E_100003/E/E_190659/E/E_3969213/E/E_4088802/M/E_314610\u0002\u0002T\u0001" +
            "1\u00022\u0001" +
            "2V1-2\u00022-3\u0001";

    public static final String validAAPIMarket1DeltaDelete = "AAPI/6/E/E_1/E/E_100003/E/E_190659/E/E_3969213/E/E_4088802/M/E_314610\u0002\u0002T\u0001" +
            "1\u00022\u0001" +
            "2V1-2\u00022-2\u0001";


    @Test
    public void parseAAPIMessageContainingValidMarket1() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPIMarket1Response);
        Market1 market1 = Market1.parse(parsedAAPIMessage);

        assertValidMarket1(market1);
    }

    @Test
    public void applyDeltaWillApplyItCorrectly() throws APIException {
        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPIMarket1Response);
        Market1 market1Valid = Market1.parse(parsedAAPIMessage);

        parsedAAPIMessage = AAPIMessage.parseMessage(validAAPIMarket1Delta);
        Market1 market1ValidDelta = Market1.parse(parsedAAPIMessage);

        market1Valid.applyDelta(market1ValidDelta);

        assertEquals("2", market1Valid.getDisplayOrder().getValue().toString());
        assertEquals("2-2", market1Valid.getMarketScores().get("2-2").getScore());
        assertEquals("Wed Jun 20 19:00:00 BST 2018", market1Valid.getMarketScores().get("2-2").getOccurredAt().toString());
        assertEquals("2-3", market1Valid.getMarketScores().get("2-3").getScore());
        assertEquals(null, market1Valid.getMarketScores().get("2-3").getOccurredAt());
    }

    @Test
    public void applyDeltaWillApplyItCorrectlyWithDelete() throws APIException {
        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPIMarket1Response);
        Market1 market1Valid = Market1.parse(parsedAAPIMessage);

        parsedAAPIMessage = AAPIMessage.parseMessage(validAAPIMarket1DeltaDelete);
        Market1 market1ValidDelta = Market1.parse(parsedAAPIMessage);

        market1Valid.applyDelta(market1ValidDelta);

        assertEquals("2", market1Valid.getDisplayOrder().getValue().toString());
        assertEquals("2-2", market1Valid.getMarketScores().get("2-2").getScore());
        assertEquals(null, market1Valid.getMarketScores().get("2-2").getOccurredAt());
    }



    public static void assertValidMarket1(Market1 market1){
        assertEquals("1", market1.getDisplayOrder().getValue().toString());
        assertEquals("2-2", market1.getMarketScores().get("2-2").getScore());
        assertEquals("Wed Jun 20 19:00:00 BST 2018", market1.getMarketScores().get("2-2").getOccurredAt().toString());
    }

}
