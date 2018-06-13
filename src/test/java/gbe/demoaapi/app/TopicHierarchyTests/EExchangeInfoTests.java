package gbe.demoaapi.app.TopicHierarchyTests;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.TopicHierarchy.EExchangeInfo;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EExchangeInfoTests {

    public static final String validAAPIEExchangeInfoResponse = "AAPI/6/E/E_1/E/E_100003/E/E_4111480/EEI\u0002\u0002T\u0001" +
            "1\u00024111480\u0001" +
            "2\u0002F\u0001" +
            "3\u00022018-05-16T11:00:00.000Z\u0001";

    public static final String validAAPIEExchangeInfoDelta = "AAPI/6/E/E_1/E/E_100003/E/E_4111480/EEI\u0002\u0002T\u0001" +
            "2\u0002T\u0001";


    public static void assertValidEExchangeInfo(EExchangeInfo eExchangeInfo){
        assertEquals("4111480", eExchangeInfo.getEventClassifierId().getValue().toString());
        assertEquals(false, eExchangeInfo.getIsEnabledForMultiples().getValue());
        assertEquals("Wed May 16 12:00:00 BST 2018", eExchangeInfo.getStartTime().getValue().toString());

    }

    @Test
    public void parseAAPIMessageContainingValidEExchangeInfo() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPIEExchangeInfoResponse);
        EExchangeInfo eExchangeInfo = EExchangeInfo.parse(parsedAAPIMessage);

        assertValidEExchangeInfo(eExchangeInfo);
    }


    @Test
    public void applyDeltaWillApplyItCorrectly() throws APIException {
        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPIEExchangeInfoResponse);
        EExchangeInfo eExchangeInfoValid = EExchangeInfo.parse(parsedAAPIMessage);

        parsedAAPIMessage = AAPIMessage.parseMessage(validAAPIEExchangeInfoDelta);
        EExchangeInfo eExchangeInfoDelta = EExchangeInfo.parse(parsedAAPIMessage);

        eExchangeInfoValid.applyDelta(eExchangeInfoDelta);

        assertEquals("4111480", eExchangeInfoValid.getEventClassifierId().getValue().toString());
        assertEquals(true, eExchangeInfoValid.getIsEnabledForMultiples().getValue());
        assertEquals("Wed May 16 12:00:00 BST 2018", eExchangeInfoValid.getStartTime().getValue().toString());
    }
}
