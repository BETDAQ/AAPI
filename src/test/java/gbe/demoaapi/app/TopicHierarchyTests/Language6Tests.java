package gbe.demoaapi.app.TopicHierarchyTests;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.TopicHierarchy.Language6;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Language6Tests {

    public static final String validAAPILanguage6ResponsePrior22 = "AAPI/3/E/E_1/E/E_100004/E/E_190538/E/E_4960933/E/E_4960934/M/E_12456387/S/E_79248533/SEI/SEL/en\u0002\u0002T\u0001" +
            "1\u0002 4 Mr Sandgate\u0001" +
            "2\u0002JockeyColours=Red and yellow diabolo, yellow sleeves, red armlets, red cap~StallNumber\u0001";

    public static final String validAAPILanguage6Delta = "AAPI/3/E/E_1/E/E_100004/E/E_190538/E/E_4960933/E/E_4960934/M/E_12456387/S/E_79248533/SEI/SEL/en\u0002\u0002T\u0001" +
            "1\u0002 2 Sandgate\u0001" +
            "2\u0002Test Blurb\u0001";

    public static final String validAAPILanguage6DeltaDelete = "AAPI/3/E/E_1/E/E_100004/E/E_190538/E/E_4960933/E/E_4960934/M/E_12456387/S/E_79248533/SEI/SEL/en\u0002\u0002T\u0001" +
            "2\u0002\u0001";


    @Test
    public void parseAAPIMessageContainingValidLanguage6() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPILanguage6ResponsePrior22);
        Language6 language6 = Language6.parse(parsedAAPIMessage);

        assertValidLanguage6(language6);
    }

    @Test
    public void applyDeltaWillApplyItCorrectly() throws APIException {
        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPILanguage6ResponsePrior22);
        Language6 language6Valid = Language6.parse(parsedAAPIMessage);

        parsedAAPIMessage = AAPIMessage.parseMessage(validAAPILanguage6Delta);
        Language6 language6ValidDelta = Language6.parse(parsedAAPIMessage);

        language6Valid.applyDelta(language6ValidDelta);

        assertEquals(" 2 Sandgate", language6Valid.getName().getValue());
        assertEquals("Test Blurb", language6Valid.getSelectionBlurb().getValue());
    }

    @Test
    public void applyDeltaWillApplyItCorrectlyWithDelete() throws APIException {
        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPILanguage6ResponsePrior22);
        Language6 language6Valid = Language6.parse(parsedAAPIMessage);

        parsedAAPIMessage = AAPIMessage.parseMessage(validAAPILanguage6DeltaDelete);
        Language6 language6ValidDelta = Language6.parse(parsedAAPIMessage);

        language6Valid.applyDelta(language6ValidDelta);

        assertEquals(" 4 Mr Sandgate", language6Valid.getName().getValue());
        assertEquals("", language6Valid.getSelectionBlurb().getValue());
    }

    public static void assertValidLanguage6(Language6 language6){
        assertEquals(" 4 Mr Sandgate", language6.getName().getValue());
        assertEquals("JockeyColours=Red and yellow diabolo, yellow sleeves, red armlets, red cap~StallNumber", language6.getSelectionBlurb().getValue());
    }

}
