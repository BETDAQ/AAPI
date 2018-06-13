package gbe.demoaapi.app.Configuration;

import gbe.demoaapi.app.CommonFunctions;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Properties;

public class ConfigurationLoader {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(ConfigurationLoader.class);
    private static final String appConfigPath = "config/demoapp.properties";

    public static Settings loadSettingsFromConfiguration(){
        try{
            Properties appProps = new Properties();
            appProps.load(new FileInputStream(appConfigPath));

            Settings toReturn = new Settings(
                    appProps.getProperty("url"),
                    appProps.getProperty("proxy"),
                    CommonFunctions.isNullOrEmpty(appProps.getProperty("proxyPort")) ? 0 : Integer.parseInt(appProps.getProperty("proxyPort")),
                    appProps.getProperty("aAPIVersion"),
                    Integer.parseInt(appProps.getProperty("deltaCounterPrintTimeInMs")));
            if(toReturn.areValidWithoutProxy()){
                return toReturn;
            }
        }catch (Exception e){
            logger.warn(String.format("Exception in loadSettingsFromConfiguration method: %s", Arrays.asList(e.getStackTrace()).toString()));
            return null;
        }
        return null;
    }



}
