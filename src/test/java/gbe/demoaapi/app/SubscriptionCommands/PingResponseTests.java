package gbe.demoaapi.app.SubscriptionCommands;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PingResponseTests {

    public static final String validAAPICommandResponsePing = "AAPI/6/D\u000220\u0002F\u0001" +
            "0\u00021984840034\u0001" +
            "1\u00020\u0001" +
            "3\u00022~3\u0001";


    @Test
    public void parseAAPIMessageContainingValidUnsubscribeResponse() throws APIException {


        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPICommandResponsePing);
        PingResponse pingResponse = PingResponse.parse(parsedAAPIMessage);

        assertEquals(1984840034 ,pingResponse.getCorrelationId());
        assertEquals("0" ,pingResponse.getResponseCode());
        assertEquals(0 ,pingResponse.getMessagesInQueue());

    }

}
