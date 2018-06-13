package gbe.demoaapi.app.SubscriptionCommands;

import gbe.demoaapi.app.AAPIMessage.AAPIMessageHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

import static gbe.demoaapi.app.CommonFunctions.getListOfStringsAsAAPIString;
import static gbe.demoaapi.app.MessageConstructor.AddRecord;
import static gbe.demoaapi.app.MessageConstructor.Initialise;

public class PingRequest {

    private static final int commandId = 22;
    private static final int currentClientTimeOrdinal = 1;
    private static final int lastPingRoundtripMSOrdinal = 2;
    private static final int lastPingetAtOrdinal = 3;

    private Date currentClientTime;
    private long lastPingRoundtripMS;
    private Date lastPingetAt;

    public PingRequest(Date currentClientTime) {
        this.currentClientTime = currentClientTime;
    }

    public PingRequest(Date currentClientTime, long lastPingRoundtripMS, Date lastPingetAt) {
        this.currentClientTime = currentClientTime;
        this.lastPingRoundtripMS = lastPingRoundtripMS;
        this.lastPingetAt = lastPingetAt;
    }

    public String getAsAAPIMessageWithCorrelationId(int correlationId){
        String messageToSend = Initialise(commandId, correlationId);
        messageToSend = AddRecord(messageToSend, currentClientTimeOrdinal, new SimpleDateFormat(AAPIMessageHelper.DEFAULT_DATE_FORMAT).format(currentClientTime));
        return messageToSend;
    }
}
