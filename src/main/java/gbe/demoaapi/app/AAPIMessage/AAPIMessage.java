package gbe.demoaapi.app.AAPIMessage;
import gbe.demoaapi.app.CommonFunctions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AAPIMessage {

    public static final char FIELD_DELIMITER = (char)0x02;// '\u0002';
    public static final char RECORD_DELIMITER = (char)0x01;//'\u0001';

    private String topicName;
    private int messageIdentifier;
    private List<String> headers = new ArrayList<String>();
    private List<NameValuePair> nvps = new ArrayList<NameValuePair>();
    private int position = 0;
    private int size = 0;
    private AAPIMessageType messageType;
    private String rawMessage;

    public AAPIMessage(){
    }

    public int getMessageIdentifier() {
        return messageIdentifier;
    }

    public String getTopicName() {
        return topicName;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public List<NameValuePair> getNvps() {
        return nvps;
    }

    public String getRawMessage() {
        return rawMessage;
    }

    public AAPIMessage(int messageIdentifier, String payload){
        this(AAPIMessageType.DELTA, null, messageIdentifier, payload);
    }

    public AAPIMessage(AAPIMessageType messageType, String topicName, int messageIdentifier){
        this(messageType, topicName, messageIdentifier, null);
    }

    public AAPIMessage(AAPIMessageType messageType, String topicName, int messageIdentifier, String payload){

        this.topicName = topicName;
        this.messageType = messageType;
        if(topicName != null)
            headers.add(topicName);
        else
            headers.add("");
        this.messageIdentifier = messageIdentifier;

        if(messageIdentifier>0)
            headers.add(""+messageIdentifier);
        else
            headers.add("");
        switch (messageType) {
            case INITIAL_TOPIC_LOAD:
                headers.add("T");
                break;
            case DELETE:
                headers.add("X");
                break;

            default:
                headers.add("F");
                break;
        }

        if(payload != null){
            this.parseBody(payload);
        }
    }



    public AAPIMessage(AAPIMessageType messageType, String topicName){
        this(messageType, topicName, 0, null);
    }

    public static AAPIMessage parseMessage(String payload){

        AAPIMessage msg;

        String[] parts = payload.split(""+RECORD_DELIMITER);

        List<String> headers = Arrays.asList(parts[0].split(""+FIELD_DELIMITER));
        if(headers.size() == 3){
            AAPIMessageType t = null;
            switch (headers.get(2)) {
                case "T":
                    t = AAPIMessageType.INITIAL_TOPIC_LOAD;
                    break;
                case "X":
                    t = AAPIMessageType.DELETE;
                    break;

                default:
                    t = AAPIMessageType.DELTA;
                    break;
            }

            String topicName = headers.get(0);
            int msgId = CommonFunctions.parseMessageId(headers.get(1));
            msg  = new AAPIMessage(t, topicName, msgId);
        }
        else{
            msg = new AAPIMessage();
            msg.headers = headers;
        }

        msg.size = payload.length();
        msg.rawMessage = payload;

        if(parts.length>1){
            for (int i = 1; i < parts.length; i++) {

                String[] pair = parts[i].split(""+FIELD_DELIMITER);
                NameValuePair nvp  = new NameValuePair(pair[0], pair.length>1 ? pair[1]:"");
                msg.nvps.add(nvp);
            }
        }
        return msg;
    }

    private void parseBody(String payload){

        this.size = payload.length();

        String[] parts = payload.split(""+RECORD_DELIMITER);
        String fieldDelim = ""+FIELD_DELIMITER;

        for (int i = 0; i < parts.length; i++) {

            String[] pair = parts[i].split(fieldDelim);
            NameValuePair nvp  = new NameValuePair(pair[0], pair.length>1 ? pair[1]:null);
            this.nvps.add(nvp);
        }

    }

    public boolean isLoadMessage() {
        return messageType== AAPIMessageType.INITIAL_TOPIC_LOAD;
    }

    public boolean hasRemaining() {
        return position<(nvps.size());
    }

    public void rewind() {
        position = 0;

    }

    public NameValuePair nextRecord() {
        if(!hasRemaining())
            return null;

        NameValuePair item = nvps.get(position);
        position++;
        return item;
    }


}
