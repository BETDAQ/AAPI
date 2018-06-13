package gbe.demoaapi.app.TopicHierarchyTests;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.TopicHierarchy.Selection1;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class Selection1Tests {

    public static final String validAAPISelection1Response = "AAPI/3/E/E_1/E/E_100004/E/E_190538/E/E_4960933/E/E_4960934/M/E_12456387/S/E_79248529\u0002\u0002T\u0001" +
            "1\u000210\u0001" +
            "2\u000220180517_805691_Win.png?index=6\u0001";

    public static final String validAAPISelection1Delta = "AAPI/3/E/E_1/E/E_100004/E/E_190538/E/E_4960933/E/E_4960934/M/E_12456387/S/E_79248529\u0002\u0002T\u0001" +
            "1\u000211\u0001" +
            "2\u0002Test png\u0001";

    public static final String validAAPISelection1DeltaDelete = "AAPI/3/E/E_1/E/E_100004/E/E_190538/E/E_4960933/E/E_4960934/M/E_12456387/S/E_79248529\u0002\u0002T\u0001" +
            "2\u0002\u0001";

    public static void assertValidSelection1(Selection1 selection1){
        assertEquals("10", selection1.getDisplayOrder().getValue().toString());
        assertEquals("20180517_805691_Win.png?index=6", selection1.getIconReference().getValue());
    }

    @Test
    public void parseAAPIMessageContainingValidMarket1() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPISelection1Response);
        Selection1 selection1 = Selection1.parse(parsedAAPIMessage);

        assertValidSelection1(selection1);
    }

    @Test
    public void applyDeltaWillApplyItCorrectly() throws APIException {
        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPISelection1Response);
        Selection1 selection1Valid = Selection1.parse(parsedAAPIMessage);

        parsedAAPIMessage = AAPIMessage.parseMessage(validAAPISelection1Delta);
        Selection1 selection1ValidDelta = Selection1.parse(parsedAAPIMessage);

        selection1Valid.applyDelta(selection1ValidDelta);

        assertEquals("11", selection1Valid.getDisplayOrder().getValue().toString());
        assertEquals("Test png", selection1Valid.getIconReference().getValue());
    }

    @Test
    public void applyDeltaWillApplyItCorrectlyWithDelete() throws APIException {
        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPISelection1Response);
        Selection1 selection1Valid = Selection1.parse(parsedAAPIMessage);

        parsedAAPIMessage = AAPIMessage.parseMessage(validAAPISelection1DeltaDelete);
        Selection1 selection1ValidDelta = Selection1.parse(parsedAAPIMessage);

        selection1Valid.applyDelta(selection1ValidDelta);

        assertEquals("10", selection1Valid.getDisplayOrder().getValue().toString());
        assertEquals("", selection1Valid.getIconReference().getValue());
    }

}
