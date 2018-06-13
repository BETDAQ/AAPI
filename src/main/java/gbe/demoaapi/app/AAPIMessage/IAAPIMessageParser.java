package gbe.demoaapi.app.AAPIMessage;


import static gbe.demoaapi.app.AAPIMessage.AAPIMessageParser.Marker;

public interface IAAPIMessageParser {

    boolean readNextRecord() throws APIException;

    Marker moveNextOrdinal();
    String getFieldName();
    String getFieldValue();

    boolean isLoadMessage();
}
