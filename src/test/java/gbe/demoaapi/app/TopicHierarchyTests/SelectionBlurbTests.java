package gbe.demoaapi.app.TopicHierarchyTests;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.TopicHierarchy.SelectionBlurb;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SelectionBlurbTests {

    public static final String validAAPISelectionBlurbResponse = "AAPI/6/E/E_1/E/E_100003/E/E_190659/E/E_3969213/E/E_4102615/M/E_337545/S/E_2061914/SEI/SB\u0002\u0002T\u0001" +
            "1\u0002display=nothing\u0001";

    public static final String validAAPISelectionBlurbDelta = "AAPI/6/E/E_1/E/E_100003/E/E_190659/E/E_3969213/E/E_4102615/M/E_337545/S/E_2061914/SEI/SB\u0002\u0002T\u0001" +
            "1\u0002test\u0001";

    public static void assertValidBlurb(SelectionBlurb selectionBlurb){
        assertEquals("display=nothing", selectionBlurb.getBlurb().getValue());
    }

    @Test
    public void parseAAPIMessageContainingValidSelectionBlurb() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPISelectionBlurbResponse);
        SelectionBlurb selectionBlurb = SelectionBlurb.parse(parsedAAPIMessage);

        assertValidBlurb(selectionBlurb);
    }

    @Test
    public void applyDeltaWillApplyItCorrectly() throws APIException {
        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPISelectionBlurbResponse);
        SelectionBlurb selectionBlurbValid = SelectionBlurb.parse(parsedAAPIMessage);

        parsedAAPIMessage = AAPIMessage.parseMessage(validAAPISelectionBlurbDelta);
        SelectionBlurb selectionBlurbValidDelta = SelectionBlurb.parse(parsedAAPIMessage);

        selectionBlurbValid.applyDelta(selectionBlurbValidDelta);

        assertEquals("test", selectionBlurbValid.getBlurb().getValue());
    }

}
