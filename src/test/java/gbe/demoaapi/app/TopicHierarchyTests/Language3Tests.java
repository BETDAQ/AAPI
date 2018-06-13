package gbe.demoaapi.app.TopicHierarchyTests;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.TopicHierarchy.Language3;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Language3Tests {

    public static final String validAAPILanguage3Response = "AAPI/3/E/E_1/E/E_100004/E/E_190538/E/E_4960933/E/E_4960934/M/E_12456387/MEI/MEL/en\u0002\u0002T\u0001" +
            "1\u0002Win Market\u0001" +
            "2\u00022m 47y~ Perth 6500 GBP Added, 2m 0f 47y\u0001";

    public static final String validAAPILanguage3Delta = "AAPI/3/E/E_1/E/E_100004/E/E_190538/E/E_4960933/E/E_4960934/M/E_12456387/MEI/MEL/en\u0002\u0002T\u0001" +
            "1\u0002Match Odds Market\u0001" +
            "2\u0002Test change\u0001";

    public static final String validAAPILanguage3DeltaDelete = "AAPI/3/E/E_1/E/E_100004/E/E_190538/E/E_4960933/E/E_4960934/M/E_12456387/MEI/MEL/en\u0002\u0002T\u0001" +
            "1\u0002Match Odds Market\u0001" +
            "2\u0002\u0001";


    @Test
    public void parseAAPIMessageContainingValidMarket1() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPILanguage3Response);
        Language3 language3 = Language3.parse(parsedAAPIMessage);

        assertValidLanguage3(language3);
    }

    @Test
    public void applyDeltaWillApplyItCorrectly() throws APIException {
        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPILanguage3Response);
        Language3 language3Valid = Language3.parse(parsedAAPIMessage);

        parsedAAPIMessage = AAPIMessage.parseMessage(validAAPILanguage3Delta);
        Language3 language3DeltaValid = Language3.parse(parsedAAPIMessage);

        language3Valid.applyDelta(language3DeltaValid);

        assertEquals("Match Odds Market", language3Valid.getName().getValue());
        assertEquals("Test change", language3Valid.getDescription().getValue());
    }

    @Test
    public void applyDeltaWillApplyItCorrectlyWithDelete() throws APIException {
        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPILanguage3Response);
        Language3 language3Valid = Language3.parse(parsedAAPIMessage);

        parsedAAPIMessage = AAPIMessage.parseMessage(validAAPILanguage3DeltaDelete);
        Language3 language3DeltaValid = Language3.parse(parsedAAPIMessage);

        language3Valid.applyDelta(language3DeltaValid);

        assertEquals("Match Odds Market", language3Valid.getName().getValue());
        assertEquals("", language3Valid.getDescription().getValue());
    }

    public static void assertValidLanguage3(Language3 language3) {
        assertEquals("Win Market", language3.getName().getValue());
        assertEquals("2m 47y~ Perth 6500 GBP Added, 2m 0f 47y", language3.getDescription().getValue());
    }

}
