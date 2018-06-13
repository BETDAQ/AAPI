package gbe.demoaapi.app.SubscriptionCommands.Handlers;

import gbe.demoaapi.app.Configuration.Settings;
import gbe.demoaapi.app.MainApp;
import gbe.demoaapi.app.SubscriptionCommands.LogonPunterRequest;

import java.util.UUID;

public class LogonPunterRequestSender {

    private Settings settings;
    private UserInputHandler userInputHandler;
    public LogonPunterRequestSender(Settings settings, UserInputHandler userInputHandler) {
        this.settings = settings;
        this.userInputHandler = userInputHandler;
    }

    public String getLogonRequest(){
        return logonPunterWithUserInput().getAsAAPIMessageWithCorrelationId(MainApp.correlationId++);
    }

    private LogonPunterRequest logonPunterWithUserInput(){
        String userName = userInputHandler.getUsernameFromInput();
        String password = userInputHandler.getPasswordFromInput();
        return new LogonPunterRequest(
                userName,
                password,
                "USD",
                settings.getaAPIVersion(),
                UUID.randomUUID(),
                "9");
    }

}
