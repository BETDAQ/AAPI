package gbe.demoaapi.app.SubscriptionCommands;

import java.util.UUID;

import static gbe.demoaapi.app.MessageConstructor.AddRecord;
import static gbe.demoaapi.app.MessageConstructor.Initialise;

public class LogonPunterRequest {

    private static final int commandId = 2;
    private static final int userNameOrdinal = 4;
    private static final int passwordOrdinal = 5;
    private static final int currencyOrdinal = 6;
    private static final int aAPIVersionOrdinal = 8;
    private static final int clientSpecifiedGuidOrdinal = 9;
    private static final int granularChannelTypeOrdinal = 10;

    private String userName;
    private String password;
    private String currency;
    private String aAPIVersion;
    private UUID clientSpecifiedGuid;
    private String granularChannelType;

    public LogonPunterRequest(String userName, String password, String currency, String aAPIVersion, UUID clientSpecifiedGuid, String granularChannelType) {
        this.userName = userName;
        this.password = password;
        this.currency = currency;
        this.aAPIVersion = aAPIVersion;
        this.clientSpecifiedGuid = clientSpecifiedGuid;
        this.granularChannelType = granularChannelType;
    }

    public String getAsAAPIMessageWithCorrelationId(int correlationId){
        String messageToSend = Initialise(commandId, correlationId);
        messageToSend = AddRecord(messageToSend, userNameOrdinal, userName);
        messageToSend = AddRecord(messageToSend, passwordOrdinal, password);
        messageToSend = AddRecord(messageToSend, currencyOrdinal, currency);
        messageToSend = AddRecord(messageToSend, aAPIVersionOrdinal, aAPIVersion);
        messageToSend = AddRecord(messageToSend, clientSpecifiedGuidOrdinal, clientSpecifiedGuid.toString());
        messageToSend = AddRecord(messageToSend, granularChannelTypeOrdinal, granularChannelType);

        return messageToSend;
    }
}
