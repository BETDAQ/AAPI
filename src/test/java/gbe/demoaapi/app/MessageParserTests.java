package gbe.demoaapi.app;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import org.junit.Test;


import static org.junit.Assert.assertEquals;


public class MessageParserTests {

    @Test
    public void parseValidAAPIMessage()
    {
        String validAAPIResponseSubscribeDetailedMarketprices = "AAPI/6/D\u000210\u0002F\u00010\u00021\u00011\u00020\u00012\u00021\u00014\u0002499\u0001";
        AAPIMessage parsedMessage = AAPIMessage.parseMessage(validAAPIResponseSubscribeDetailedMarketprices);
        assertEquals(3, parsedMessage.getHeaders().size());
        assertEquals("AAPI/6/D", parsedMessage.getHeaders().get(0));
        assertEquals("10", parsedMessage.getHeaders().get(1));
        assertEquals("F", parsedMessage.getHeaders().get(2));

        assertEquals(4, parsedMessage.getNvps().size());
        assertEquals("0", parsedMessage.getNvps().get(0).getNameField());
        assertEquals("1", parsedMessage.getNvps().get(0).getValueField());

        assertEquals("1", parsedMessage.getNvps().get(1).getNameField());
        assertEquals("0", parsedMessage.getNvps().get(1).getValueField());

        assertEquals("2", parsedMessage.getNvps().get(2).getNameField());
        assertEquals("1", parsedMessage.getNvps().get(2).getValueField());

        assertEquals("4", parsedMessage.getNvps().get(3).getNameField());
        assertEquals("499", parsedMessage.getNvps().get(3).getValueField());
    }

    @Test
    public void parseValidAAPIMessageSELLanguage()
    {
        String validAAPIResponseSEL = "AAPI/1/E/E_1/E/E_100003/E/E_45645645/M/E_151515/S/E_565656/SEI/SEL/en\u0002\u0002T\u00011\u0002en name\u00012\u0002test blurb\u0001";
        AAPIMessage parsedMessage = AAPIMessage.parseMessage(validAAPIResponseSEL);
        assertEquals(3, parsedMessage.getHeaders().size());
        assertEquals("AAPI/1/E/E_1/E/E_100003/E/E_45645645/M/E_151515/S/E_565656/SEI/SEL/en", parsedMessage.getHeaders().get(0));
        assertEquals("", parsedMessage.getHeaders().get(1));
        assertEquals("T", parsedMessage.getHeaders().get(2));

        assertEquals(2, parsedMessage.getNvps().size());
        assertEquals("1", parsedMessage.getNvps().get(0).getNameField());
        assertEquals("en name", parsedMessage.getNvps().get(0).getValueField());

        assertEquals("2", parsedMessage.getNvps().get(1).getNameField());
        assertEquals("test blurb", parsedMessage.getNvps().get(1).getValueField());
    }

}
