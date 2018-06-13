package gbe.demoaapi.app;

public class MessageConstructor {

    static final char FD = '\u0002';
    static final char RD = '\u0001';


    public static String Initialise(int commandId, int correlationId)
    {
        // all command messages have a correlation id at position zero
        String toReturn = String.format("%s%s", FD, commandId);
        return AddRecord(toReturn, 0,""+correlationId);
    }

    public static String AddRecord(String messageToAddRecordTo, int ordinal, String value)
    {
        return String.format("%s%s%s%s%s", messageToAddRecordTo, RD, ordinal, FD, value);
    }
}
