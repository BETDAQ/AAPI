package gbe.demoaapi.app.Logging;


public class LoggerFactory {

    public static ConsoleLogger getLogger(Class<?> clazz){

        return  new ConsoleLogger(clazz.getName());

    }
}
