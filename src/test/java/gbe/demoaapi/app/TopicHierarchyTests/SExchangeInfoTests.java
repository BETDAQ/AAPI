package gbe.demoaapi.app.TopicHierarchyTests;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.TopicHierarchy.SExchangeInfo;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SExchangeInfoTests {

    public static final String validAAPISExchangeInfoResponseFull = "AAPI/3/E/E_1/E/E_100004/E/E_190538/E/E_4960933/E/E_4960934/M/E_12456387/S/E_79248529/SEI\u0002\u0002T\u0001" +
            "1\u000279248529\u0001" +
            "2\u00022\u0001" +
            "3\u00020\u0001" +
            "4\u00022.02\u0001" +
            "5\u00022018-06-20T18:00:00.000Z\u0001" +
            "6\u00025-2\u0001" +
            "7\u00021.25\u0001" +
            "8\u00023.55\u0001" +
            "9\u00022.68\u0001";

    public static final String validAAPISExchangeInfoResponsePartial = "AAPI/3/E/E_1/E/E_100004/E/E_190538/E/E_4960933/E/E_4960934/M/E_12456387/S/E_79248529/SEI\u0002\u0002T\u0001" +
            "1\u000279248529\u0001" +
            "2\u00022\u0001" +
            "3\u00020\u0001" +
            "4\u00022.02\u0001";

    public static final String validAAPISExchangeInfoDelta = "AAPI/3/E/E_1/E/E_100004/E/E_190538/E/E_4960933/E/E_4960934/M/E_12456387/S/E_79248529/SEI\u0002\u0002T\u0001" +
            "1\u000279248529\u0001" +
            "2\u00025\u0001" +
            "3\u00021\u0001" +
            "4\u00021.1\u0001" +
            "5\u00022018-06-20T18:00:00.000Z\u0001";


    public static void assertValidSExchangeInfo(SExchangeInfo sExchangeInfo){
        assertEquals(79248529L, sExchangeInfo.getSelectionId().getValue().longValue());
        assertEquals(2, sExchangeInfo.getStatus().getValue().intValue());
        assertEquals(0, sExchangeInfo.getSelectionResetCount().getValue().intValue());
        assertEquals("2.02", sExchangeInfo.getWithdrawalFactor().getValue().toString());
        assertEquals("Wed Jun 20 19:00:00 BST 2018", sExchangeInfo.getSettledTime().getValue().toString());
        assertEquals("5-2", sExchangeInfo.getResultString().getValue());
        assertEquals("1.25", sExchangeInfo.getVoidPercentage().getValue().toString());
        assertEquals("3.55", sExchangeInfo.getLeftSideFactor().getValue().toString());
        assertEquals("2.68", sExchangeInfo.getRightSideFactor().getValue().toString());
    }

    public static void assertValidSExchangeInfoPartial(SExchangeInfo sExchangeInfo){
        assertEquals(79248529L, sExchangeInfo.getSelectionId().getValue().longValue());
        assertEquals(2, sExchangeInfo.getStatus().getValue().intValue());
        assertEquals(0, sExchangeInfo.getSelectionResetCount().getValue().intValue());
        assertEquals("2.02", sExchangeInfo.getWithdrawalFactor().getValue().toString());
        assertEquals(null, sExchangeInfo.getSettledTime().getValue());
        assertEquals(null, sExchangeInfo.getResultString().getValue());
        assertEquals(null, sExchangeInfo.getVoidPercentage().getValue());
        assertEquals(null, sExchangeInfo.getLeftSideFactor().getValue());
        assertEquals(null, sExchangeInfo.getRightSideFactor().getValue());
    }

    @Test
    public void parseAAPIMessageContainingValidSExchangeInfo() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPISExchangeInfoResponseFull);
        SExchangeInfo sExchangeInfo = SExchangeInfo.parse(parsedAAPIMessage);

        assertValidSExchangeInfo(sExchangeInfo);
    }

    @Test
    public void parseAAPIMessageContainingValidSExchangeInfoPartial() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPISExchangeInfoResponsePartial);
        SExchangeInfo sExchangeInfo = SExchangeInfo.parse(parsedAAPIMessage);

        assertValidSExchangeInfoPartial(sExchangeInfo);
    }

    @Test
    public void applyDeltaWillApplyItCorrectly() throws APIException {
        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPISExchangeInfoResponsePartial);
        SExchangeInfo sExchangeInfoValid = SExchangeInfo.parse(parsedAAPIMessage);

        parsedAAPIMessage = AAPIMessage.parseMessage(validAAPISExchangeInfoDelta);
        SExchangeInfo sExchangeInfoValidDelta = SExchangeInfo.parse(parsedAAPIMessage);

        sExchangeInfoValid.applyDelta(sExchangeInfoValidDelta);

        assertEquals(79248529L, sExchangeInfoValid.getSelectionId().getValue().longValue());
        assertEquals(5, sExchangeInfoValid.getStatus().getValue().intValue());
        assertEquals(1, sExchangeInfoValid.getSelectionResetCount().getValue().intValue());
        assertEquals("1.1", sExchangeInfoValid.getWithdrawalFactor().getValue().toString());
        assertEquals("Wed Jun 20 19:00:00 BST 2018", sExchangeInfoValid.getSettledTime().getValue().toString());
        assertEquals(null, sExchangeInfoValid.getResultString().getValue());
        assertEquals(null, sExchangeInfoValid.getVoidPercentage().getValue());
        assertEquals(null, sExchangeInfoValid.getLeftSideFactor().getValue());
        assertEquals(null, sExchangeInfoValid.getRightSideFactor().getValue());
    }

}
