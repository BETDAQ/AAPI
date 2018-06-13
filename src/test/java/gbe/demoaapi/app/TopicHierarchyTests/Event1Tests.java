package gbe.demoaapi.app.TopicHierarchyTests;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.TopicHierarchy.Event1;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Event1Tests {

    public static final String validAAPIEvent1ResponsePartial = "AAPI/6/E/E_1/E/E_100003\u0002\u0002T\u0001" +
            "1\u00021\u0001";

    public static final String validAAPIEvent1ResponseFull = "AAPI/6/E/E_1/E/E_100003\u0002\u0002T\u0001" +
            "1\u00021\u0001" +
            "2V1-1\u00022018-06-20T18:00:00.000Z\u0001" +
            "2V1-2\u00022-3\u0001" +
            "3V1-1\u0002MatchStarted\u0001" +
            "3V1-2\u00022018-06-20T18:00:00.000Z\u0001";

    public static final String validAAPIEvent1Delta = "AAPI/6/E/E_1/E/E_100003\u0002\u0002T\u0001" +
            "1\u00022\u0001" +
            "3V1-1\u0002MatchStarted\u0001" +
            "3V1-3\u00022018-06-20T18:00:00.000Z\u0001";


    public static void assertValidEvent1Full(Event1 event1){
        assertEquals("1", event1.getDisplayOrder().getValue().toString());
        assertEquals("Wed Jun 20 19:00:00 BST 2018", event1.getEventScores().get("2-3").getOccurredAt().toString());
        assertEquals("2-3", event1.getEventScores().get("2-3").getScore());
        assertEquals("MatchStarted", event1.getEventOccurrences().get("MatchStarted").getOccurrenceType());
        assertEquals("Wed Jun 20 19:00:00 BST 2018", event1.getEventOccurrences().get("MatchStarted").getPredictedTime().toString());
        assertEquals(null, event1.getEventOccurrences().get("MatchStarted").getActualTime());
    }

    public static void assertValidEvent1Partial(Event1 event1){
        assertEquals("1", event1.getDisplayOrder().getValue().toString());
    }

    @Test
    public void parseAAPIMessageContainingValidEvent1() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPIEvent1ResponsePartial);
        Event1 event1 = Event1.parse(parsedAAPIMessage);

        assertValidEvent1Partial(event1);
    }

    @Test
    public void parseAAPIMessageContainingValidFullEvent1() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPIEvent1ResponseFull);
        Event1 event1 = Event1.parse(parsedAAPIMessage);

        assertValidEvent1Full(event1);
    }

    @Test
    public void applyDeltaWillApplyItCorrectly() throws APIException {
        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPIEvent1ResponseFull);
        Event1 event1Valid = Event1.parse(parsedAAPIMessage);

        parsedAAPIMessage = AAPIMessage.parseMessage(validAAPIEvent1Delta);
        Event1 event1ValidDelta = Event1.parse(parsedAAPIMessage);

        event1Valid.applyDelta(event1ValidDelta);

        assertEquals("2", event1Valid.getDisplayOrder().getValue().toString());
        assertEquals("Wed Jun 20 19:00:00 BST 2018", event1Valid.getEventScores().get("2-3").getOccurredAt().toString());
        assertEquals("2-3", event1Valid.getEventScores().get("2-3").getScore());
        assertEquals("MatchStarted", event1Valid.getEventOccurrences().get("MatchStarted").getOccurrenceType());
        assertEquals(null, event1Valid.getEventOccurrences().get("MatchStarted").getPredictedTime());
        assertEquals("Wed Jun 20 19:00:00 BST 2018", event1Valid.getEventOccurrences().get("MatchStarted").getActualTime().toString());
    }


}
