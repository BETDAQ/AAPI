package gbe.demoaapi.app.AAPIMessage;

@SuppressWarnings("serial")
public class APIException extends Exception {

    public APIException() {
        super();
    }
    public APIException(String format, Exception e) {
        super(format,e);
    }

    public APIException(String format) {
        super(format);
    }

}
