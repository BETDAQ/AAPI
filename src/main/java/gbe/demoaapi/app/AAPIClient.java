package gbe.demoaapi.app;

import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;
import gbe.demoaapi.app.SubscriptionCommands.Handlers.LogonPunterRequestSender;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Arrays;

/** This example demonstrates how to create a websocket connection to a server. Only the most important callbacks are overloaded. */
public class AAPIClient extends WebSocketClient {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(AAPIClient.class);

    private AAPIMessageProcessor messageProcessor;
    private LogonPunterRequestSender logonPunterRequestSender;

    public AAPIClient(URI serverURI, AAPIMessageProcessor messageProcessor, LogonPunterRequestSender logonPunterRequestSender) {
        super( serverURI );
        this.messageProcessor = messageProcessor;
        this.logonPunterRequestSender = logonPunterRequestSender;
    }

    @Override
    public void onOpen( ServerHandshake handshakedata ) {
        logger.info( "Opened connection." );
        this.send(logonPunterRequestSender.getLogonRequest());
    }

    @Override
    public void onMessage( String message ) {
        messageProcessor.parseMessageAndPutToCorrectQueue(message);
    }

    @Override
    public void onClose( int code, String reason, boolean remote ) {
        // The codecodes are documented in class org.java_websocket.framing.CloseFrame
        logger.info( "Connection closed by " + ( remote ? "remote peer" : "the app" ) + " Code: " + code + " Reason: " + reason );
        System.exit(0);
    }

    @Override
    public void onError( Exception ex ) {
        logger.warn(String.format("Exception in AAPIClient onError() method: %s", Arrays.asList(ex.getStackTrace()).toString()));
        // if the error is fatal then onClose will be called additionally
    }

}