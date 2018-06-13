package gbe.demoaapi.app.SubscriptionCommands.Handlers;

import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;
import gbe.demoaapi.app.MainApp;

import java.util.Scanner;

public class UserInputHandler {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(UserInputHandler.class);

    private Scanner scanner;
    private MainApp mainApp;

    public UserInputHandler(Scanner scanner, MainApp mainApp) {
        this.scanner = scanner;
        this.mainApp = mainApp;
    }

    public String getUsernameFromInput(){
        logger.info("Please enter the username for punter you want to logon.");
        return scanner.nextLine();
    }

    public String getPasswordFromInput(){
        logger.info("Please enter the password for punter you want to logon.");
        return scanner.nextLine();
    }

    public String[] getValidSubscriptionIdsToUnsubscribe(){
        logger.info("Please enter the subscription ids you want to unsubscribe from delimited by , or exit to quit");
        String userInput = scanner.nextLine();
        if(userInput.matches("[0-9]+(,[0-9]+)*")){
            return userInput.split(",");
        }else if(userInput.equalsIgnoreCase("exit")){
            mainApp.closeAndExit();
        }
        logger.info("Invalid input please try again");
        return getValidMarketIdsToSubscribeTo();
    }

    public String[] getValidMarketIdsToSubscribeTo(){
        logger.info("Please enter the marketIds you want to subscribe to delimited by , or exit to quit");
        String userInput = scanner.nextLine();
        if(userInput.matches("[0-9]+(,[0-9]+)*")){
            return userInput.split(",");
        }else if(userInput.equalsIgnoreCase("exit")){
            mainApp.closeAndExit();
        }
        logger.info("Invalid input please try again");
        return getValidMarketIdsToSubscribeTo();
    }

    public long getValidEventClassifierToSubscribeTo(){
        logger.info("Please enter the EventClassifier you want to subscribe to or exit to quit");
        String userInput = scanner.nextLine();
        if(userInput.matches("[0-9]+")){
            return Long.parseLong(userInput);
        }else if(userInput.equalsIgnoreCase("exit")){
            mainApp.closeAndExit();
        }
        logger.info("Invalid input please try again");
        return getValidEventClassifierToSubscribeTo();
    }

    private void showMenu(){
        logger.info("Enter number of action you want to perform and press Enter.");
        logger.info("1. SubscribeDetailedMarketPrices");
        logger.info("2. SubscribeMarketInformation");
        logger.info("3. SubscribeEventHierarchy");
        logger.info("4. Ping");
        logger.info("5. Unsubscribe");
        logger.info("6. Close and Exit");
    }

    public int getValidUserChoiceFromMenu(){
        showMenu();
        String userInput = scanner.nextLine();
        if(userInput.matches("[1-6]")){
            return Integer.parseInt(userInput);
        }else{
            logger.info("Invalid input please try again");
            return getValidUserChoiceFromMenu();
        }
    }

}
