package gbe.demoaapi.app;

import gbe.demoaapi.app.AAPIMessage.AAPIDataCache;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.Configuration.Settings;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;
import gbe.demoaapi.app.SubscriptionCommands.Handlers.*;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class MainApp {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(MainApp.class);

    public static int correlationId = 0;

    private Settings settings;
    private AAPIDataCache parsedDataCache;
    private AAPIClient client;
    private AAPICommandQueueConsumer aapiCommandQueueConsumer;
    private AAPITopicMessageQueueConsumer aapiTopicMessageQueueConsumer;

    public MainApp(Settings settings) {
        this.settings = settings;
        this.parsedDataCache = new AAPIDataCache();
    }

    public void runApp(){

        logger.info(String.format("AAPI Attempt to connect to: %s", settings.getUrl()));

        try {
            UserInputHandler userInputHandler = new UserInputHandler(new Scanner(System.in), this);

            LogonPunterRequestSender logonPunterRequestSender = new LogonPunterRequestSender(settings, userInputHandler);
            BlockingQueue<AAPIMessage> commandAAPIQueue = new LinkedBlockingQueue<AAPIMessage>();
            BlockingQueue<AAPIMessage> topicAAPIMessageQueue = new LinkedBlockingQueue<AAPIMessage>();
            aapiCommandQueueConsumer = new AAPICommandQueueConsumer(commandAAPIQueue);
            aapiTopicMessageQueueConsumer = new AAPITopicMessageQueueConsumer(topicAAPIMessageQueue);
            AAPIMessageProcessor aapiMessageProcessor = new AAPIMessageProcessor(commandAAPIQueue, topicAAPIMessageQueue);
            client = new AAPIClient( new URI(settings.getUrl()), aapiMessageProcessor, logonPunterRequestSender);

            SubscribeDetailedMarketPricesRequestSender subscribeDetailedMarketPricesRequestSender = new SubscribeDetailedMarketPricesRequestSender(client, userInputHandler);
            SubscribeMarketInformationRequestSender subscribeMarketInformationResponseHandler = new SubscribeMarketInformationRequestSender(client, userInputHandler);
            SubscribeEventHierarchyRequestSender subscribeEventHierarchyRequestSender = new SubscribeEventHierarchyRequestSender(client, userInputHandler);
            UnsubscribeRequestSender unsubscribeRequestSender = new UnsubscribeRequestSender(client, userInputHandler);
            PingRequestSender pingRequestSender = new PingRequestSender(client);
            MenuHandler menuHandler = new MenuHandler(userInputHandler, subscribeDetailedMarketPricesRequestSender, subscribeMarketInformationResponseHandler, unsubscribeRequestSender, pingRequestSender, subscribeEventHierarchyRequestSender, this);

            aapiTopicMessageQueueConsumer.initializeAvailableParsers(parsedDataCache);
            aapiCommandQueueConsumer.initializeAvailableCommandParsers(parsedDataCache, menuHandler);

            if(!CommonFunctions.isNullOrEmpty(settings.getProxy()))
                client.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(settings.getProxy(), settings.getProxyPort())));

            client.connect();

            aapiTopicMessageQueueConsumer.start();
            aapiCommandQueueConsumer.start();


            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    logger.info(String.format("There was %d deltas applied in the last: %s seconds", parsedDataCache.getApplyDeltaCounter(), TimeUnit.MILLISECONDS.toSeconds(settings.getDeltaCounterPrintTimeInMs())));
                    parsedDataCache.resetDeltaCounter();
                }
            };
            timer.scheduleAtFixedRate(timerTask, settings.getDeltaCounterPrintTimeInMs(), settings.getDeltaCounterPrintTimeInMs());

        }catch (Exception e){
            logger.warn(String.format("Exception in MainApp run() method: %s", Arrays.asList(e.getStackTrace()).toString()));
        }
    }

    public void closeAndExit(){
        aapiTopicMessageQueueConsumer.finishProcessingMessages();
        aapiCommandQueueConsumer.finishProcessingMessages();
        client.close();
    }

}
