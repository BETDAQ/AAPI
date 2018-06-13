package gbe.demoaapi.app;

import gbe.demoaapi.app.Configuration.ConfigurationLoader;
import gbe.demoaapi.app.Configuration.Settings;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;

public class App {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        Settings loadedSetting = ConfigurationLoader.loadSettingsFromConfiguration();
        if(loadedSetting != null){
            MainApp theApp = new MainApp(loadedSetting);
            theApp.runApp();
        }else{
            logger.warn("Please check the configuration file: demoapp.properties as some required properties are null or empty.");
        }
    }

}

