package gbe.demoaapi.app.SubscriptionCommands.Handlers;

import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;
import gbe.demoaapi.app.MainApp;

public class MenuHandler {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(MenuHandler.class);

    private UserInputHandler userInputHandler;
    private SubscribeDetailedMarketPricesRequestSender subscribeDetailedMarketPricesRequestSender;
    private SubscribeMarketInformationRequestSender subscribeMarketInformationRequestSender;
    private UnsubscribeRequestSender unsubscribeRequestSender;
    private PingRequestSender pingRequestSender;
    private SubscribeEventHierarchyRequestSender subscribeEventHierarchyRequestSender;
    private MainApp mainApp;

    public MenuHandler(UserInputHandler userInputHandler, SubscribeDetailedMarketPricesRequestSender subscribeDetailedMarketPricesRequestSender,
                       SubscribeMarketInformationRequestSender subscribeMarketInformationRequestSender, UnsubscribeRequestSender unsubscribeRequestSender,
                       PingRequestSender pingRequestSender, SubscribeEventHierarchyRequestSender subscribeEventHierarchyRequestSender, MainApp mainApp) {
        this.userInputHandler = userInputHandler;
        this.subscribeDetailedMarketPricesRequestSender = subscribeDetailedMarketPricesRequestSender;
        this.subscribeMarketInformationRequestSender = subscribeMarketInformationRequestSender;
        this.unsubscribeRequestSender = unsubscribeRequestSender;
        this.pingRequestSender = pingRequestSender;
        this.subscribeEventHierarchyRequestSender = subscribeEventHierarchyRequestSender;
        this.mainApp = mainApp;
    }

    public void processChoice(){
        try{
            processChoiceWithUserInput();
        }catch (Exception e){
            logger.warn(String.format("There was a exception while sending the message: %s", e));
        }
    }

    private void processChoiceWithUserInput() {
        int userChoice = userInputHandler.getValidUserChoiceFromMenu();
        switch (userChoice) {
            case 1:
                subscribeDetailedMarketPricesRequestSender.sendSubscribeDetailedMarketPricesRequest();
                break;
            case 2:
                subscribeMarketInformationRequestSender.sendSubscribeMarketInformationRequest();
                break;
            case 3:
                subscribeEventHierarchyRequestSender.sendSubscribeEventHierarchy();
                break;
            case 4:
                pingRequestSender.sendPingRequest();
                break;
            case 5:
                unsubscribeRequestSender.sendUnsubscribeRequest();
                break;
            case 6:
                mainApp.closeAndExit();
                break;
            default:
                logger.warn("Attempting to process user choice which does not exists.");
                break;
        }
    }

}
