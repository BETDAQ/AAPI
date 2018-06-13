package gbe.demoaapi.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class CommonFunctions {

    public static String getBoolAsAAPIString(boolean value){
        return value ? "T" : "F";
    }

    public static String getListOfStringsAsAAPIString(List<String> toConcatenate){

        StringBuilder stringBuilder = new StringBuilder();

        for (String toAdd : toConcatenate) {
            stringBuilder.append(toAdd).append("~");
        }
        String toReturn = stringBuilder.toString();
        toReturn = toReturn.substring(0 , toReturn.length() - 1);//remove the extra ~ at the end
        return toReturn;

    }


    public static int parseMessageId(String msgId){
        try{
            return Integer.parseInt(msgId);
        }catch(NumberFormatException e){
        }
        return 0;
    }

    public static String getAsJsonString(Object toConvert){
        Gson g = new GsonBuilder().setPrettyPrinting().create();
        return g.toJson(toConvert);
    }

    public static boolean isNullOrEmpty(String value)
    {
        if (value != null)
            return value.length() == 0;
        else
            return true;
    }

}
