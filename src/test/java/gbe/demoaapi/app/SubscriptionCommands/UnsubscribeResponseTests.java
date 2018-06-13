package gbe.demoaapi.app.SubscriptionCommands;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnsubscribeResponseTests {

    public static final String validAAPICommandResponseUnsubscribe = "AAPI/6/D\u000220\u0002F\u0001" +
            "0\u00021984840034\u0001" +
            "1\u00020\u0001" +
            "3\u00022~3\u0001";


    @Test
    public void parseAAPIMessageContainingValidUnsubscribeResponse() throws APIException {


        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPICommandResponseUnsubscribe);
        UnsubscribeResponse unsubscribeResponse = UnsubscribeResponse.parse(parsedAAPIMessage);

        assertEquals(1984840034 ,unsubscribeResponse.getCorrelationId());
        assertEquals("0" ,unsubscribeResponse.getResponseCode());
        assertEquals("2" ,unsubscribeResponse.getNotActiveSubscriptionIds()[0]);
        assertEquals("3" ,unsubscribeResponse.getNotActiveSubscriptionIds()[1]);

    }

}
