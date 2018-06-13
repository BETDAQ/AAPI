package gbe.demoaapi.app.AAPIMessage.Parsers;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;

public interface RegexAAPIMessageParser {
    boolean parse(AAPIMessage message) throws APIException;
}
