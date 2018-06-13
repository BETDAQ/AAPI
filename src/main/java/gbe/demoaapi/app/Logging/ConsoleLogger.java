package gbe.demoaapi.app.Logging;

import static gbe.demoaapi.app.CommonFunctions.getAsJsonString;

public class ConsoleLogger {

    private static final boolean wantObjectsInJsonFormat = true;
    private String loggerName;

    public ConsoleLogger(String loggerName) {
        this.loggerName = loggerName;
    }

    public void warn(String toLog){
        logToConsole(toLog);
    }

    public void info(String toLog){
        logToConsole(toLog);
    }

    public void logObject(Class<?> clazz ,Object toLog){
        if(wantObjectsInJsonFormat){
            info(String.format("Parsed %s message as Json String to be easily read:\n %s" , clazz.getName(), getAsJsonString(toLog)));
        }else{
            info(String.format("Parsed %s message as String:\n %s" , clazz.getName(), toLog.toString()));
        }
    }

    public void logToConsole(String toLog){
        System.out.println(toLog);
    }
}
