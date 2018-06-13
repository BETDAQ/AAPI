package gbe.demoaapi.app.SubscriptionCommands;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SubscribeEventHierarchyResponseTests {

    public static final String validAAPICommandResponseSubscribeEventHierarchy = "AAPI/6/D\u000212\u0002F\u0001" +
            "0\u0002-780563568\u0001" +
            "1\u00020\u0001" +
            "2\u00021\u0001" +
            "4\u000284\u0001";


    @Test
    public void parseAAPIMessageContainingValidSubscribeDetailedMarketPricesResponse() throws APIException {


        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPICommandResponseSubscribeEventHierarchy);
        SubscribeEventHierarchyResponse subscribeEventHierarchyResponse = SubscribeEventHierarchyResponse.parse(parsedAAPIMessage);

        assertEquals(-780563568 ,subscribeEventHierarchyResponse.getCorrelationId());
        assertEquals("0" ,subscribeEventHierarchyResponse.getResponseCode());
        assertEquals("1" ,subscribeEventHierarchyResponse.getSubscriptionId().toString());
        assertEquals("84" ,subscribeEventHierarchyResponse.getAvailableMarketsCount().toString());

    }


}
