package gbe.demoaapi.app.TopicHierarchyTests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;


import gbe.demoaapi.app.TopicHierarchy.BackLayVolumeCurrencyFormat;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BackLayVolumeCurrencyFormatTests {

    public static final String validAAPIResponseSubscribeDetailedMarketPrices = "AAPI/6/E/E_1/E/E_100004/E/E_190538/E/E_4100115/E/E_4100118/M/E_333542/MEI/MDP/3_3_100_EUR_1\u0002\u0002T\u0001" +
            "1V1-1\u00022030974\u0001" +
            "1V1-2V1-1\u00022.72\u0001" +
            "1V1-2V1-2\u0002865.53\u0001" +
            "1V1-3V1-1\u00022.76\u0001" +
            "1V1-3V1-2\u0002600.60\u0001" +
            "1V2-1\u00022030975\u0001" +
            "1V2-2V1-1\u00021.98\u0001" +
            "1V2-2V1-2\u0002474.16\u0001" +
            "1V2-3V1-1\u00022\u0001" +
            "1V2-3V1-2\u0002643.50\u0001";

    private static final String validAAPIResponseSubscribeDetailedMarketPricesDelta = "AAPI/6/E/E_1/E/E_100004/E/E_190538/E/E_4100115/E/E_4100118/M/E_333542/MEI/MDP/3_3_100_EUR_1\u0002\u0002F\u0001" +
            "1V2-1\u00022030975\u0001" +
            "1V2-2V1-1\u00021.98\u0001" +
            "1V2-2V1-2\u00024744.16\u0001" +
            "1V2-3V1-1\u00021.88\u0001" +
            "1V2-3V1-2\u000263.50\u0001" +
            "1V3-1\u00022030999\u0001" +
            "1V3-2V1-1\u00023.33\u0001" +
            "1V3-2V1-2\u000288.53\u0001";

    private static final String validAAPIResponseSubscribeDetailedMarketPricesDeltaDelete = "AAPI/6/E/E_1/E/E_100004/E/E_190538/E/E_4100115/E/E_4100118/M/E_333542/MEI/MDP/3_3_100_EUR_1\u0002\u0002F\u0001" +
            "1V2-1\u00022030975\u0001" +
            "1V2-2V1-1\u00021.98\u0001" +
            "1V2-3V1-1\u00022\u0001" +
            "1V2-3V1-2\u00020\u0001";//zero means delete

    @Test
    public void parseAAPIMessageContainingValidBackLayVolumeCurrencyFormat() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPIResponseSubscribeDetailedMarketPrices);
        BackLayVolumeCurrencyFormat backLayVolumeCurrencyFormatValid = BackLayVolumeCurrencyFormat.parse(parsedAAPIMessage);

        assertValidBackLayVolumeCurrency(backLayVolumeCurrencyFormatValid);
    }


    @Test
    public void parseAAPIMessageContainingValid_DisplayAsString() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPIResponseSubscribeDetailedMarketPrices);
        BackLayVolumeCurrencyFormat backLayVolumeCurrencyFormatValid = BackLayVolumeCurrencyFormat.parse(parsedAAPIMessage);


        Gson g = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = g.toJson(backLayVolumeCurrencyFormatValid);
        //System.out.println(jsonString);
        assertEquals("{\n" +
                "  \"selectionDetailedPrices\": {\n" +
                "    \"2030974\": {\n" +
                "      \"selectionId\": 2030974,\n" +
                "      \"backedPrices\": {\n" +
                "        \"2.72\": {\n" +
                "          \"displayPrice\": \"2.72\",\n" +
                "          \"stake\": 865.53\n" +
                "        }\n" +
                "      },\n" +
                "      \"layedPrices\": {\n" +
                "        \"2.76\": {\n" +
                "          \"displayPrice\": \"2.76\",\n" +
                "          \"stake\": 600.60\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"2030975\": {\n" +
                "      \"selectionId\": 2030975,\n" +
                "      \"backedPrices\": {\n" +
                "        \"1.98\": {\n" +
                "          \"displayPrice\": \"1.98\",\n" +
                "          \"stake\": 474.16\n" +
                "        }\n" +
                "      },\n" +
                "      \"layedPrices\": {\n" +
                "        \"2\": {\n" +
                "          \"displayPrice\": \"2\",\n" +
                "          \"stake\": 643.50\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}",jsonString);
    }

    @Test
    public void applyDeltaWillApplyItCorrectly() throws APIException {


        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPIResponseSubscribeDetailedMarketPrices);
        BackLayVolumeCurrencyFormat backLayVolumeCurrencyFormatValid = BackLayVolumeCurrencyFormat.parse(parsedAAPIMessage);


        parsedAAPIMessage = AAPIMessage.parseMessage(validAAPIResponseSubscribeDetailedMarketPricesDelta);
        BackLayVolumeCurrencyFormat backLayVolumeCurrencyFormatValidDelta = BackLayVolumeCurrencyFormat.parse(parsedAAPIMessage);

        backLayVolumeCurrencyFormatValid.applyDelta(backLayVolumeCurrencyFormatValidDelta);

        assertEquals(2030974 ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030974L).getSelectionId());
        assertEquals("2.72" ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030974L).getBackedPriceWithKey("2.72").getDisplayPrice());
        assertEquals("865.53" ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030974L).getBackedPriceWithKey("2.72").getStake().toString());
        assertEquals("2.76" ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030974L).getLayedPriceWithKey("2.76").getDisplayPrice());
        assertEquals("600.60" ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030974L).getLayedPriceWithKey("2.76").getStake().toString());
        assertEquals(2030975 ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030975L).getSelectionId());
        assertEquals("1.98" ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030975L).getBackedPriceWithKey("1.98").getDisplayPrice());
        assertEquals("4744.16" ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030975L).getBackedPriceWithKey("1.98").getStake().toString());
        assertEquals("2" ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030975L).getLayedPriceWithKey("2").getDisplayPrice());
        assertEquals("643.50" ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030975L).getLayedPriceWithKey("2").getStake().toString());
        assertEquals("1.88" ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030975L).getLayedPriceWithKey("1.88").getDisplayPrice());
        assertEquals("63.50" ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030975L).getLayedPriceWithKey("1.88").getStake().toString());
        assertEquals(2030999 ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030999L).getSelectionId());
        assertEquals("3.33" ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030999L).getBackedPriceWithKey("3.33").getDisplayPrice());
        assertEquals("88.53" ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030999L).getBackedPriceWithKey("3.33").getStake().toString());
    }

    @Test
    public void applyDeltaWillApplyItCorrectlyWithDelete() throws APIException {


        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPIResponseSubscribeDetailedMarketPrices);
        BackLayVolumeCurrencyFormat backLayVolumeCurrencyFormatValid = BackLayVolumeCurrencyFormat.parse(parsedAAPIMessage);


        parsedAAPIMessage = AAPIMessage.parseMessage(validAAPIResponseSubscribeDetailedMarketPricesDeltaDelete);
        BackLayVolumeCurrencyFormat backLayVolumeCurrencyFormatValidDelta = BackLayVolumeCurrencyFormat.parse(parsedAAPIMessage);

        backLayVolumeCurrencyFormatValid.applyDelta(backLayVolumeCurrencyFormatValidDelta);

        assertEquals(2030974 ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030974L).getSelectionId());
        assertEquals("2.72" ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030974L).getBackedPriceWithKey("2.72").getDisplayPrice());
        assertEquals("865.53" ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030974L).getBackedPriceWithKey("2.72").getStake().toString());
        assertEquals("2.76" ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030974L).getLayedPriceWithKey("2.76").getDisplayPrice());
        assertEquals("600.60" ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030974L).getLayedPriceWithKey("2.76").getStake().toString());
        assertEquals(2030975 ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030975L).getSelectionId());
        assertEquals(false ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030975L).getBackedPrices().containsKey("1.98"));
        assertEquals(false ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030975L).getLayedPrices().containsKey("2"));

    }


    public static void assertValidBackLayVolumeCurrency(BackLayVolumeCurrencyFormat backLayVolumeCurrencyFormatValid){
        assertEquals(2030974 ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030974L).getSelectionId());
        assertEquals("2.72" ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030974L).getBackedPriceWithKey("2.72").getDisplayPrice());
        assertEquals("865.53" ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030974L).getBackedPriceWithKey("2.72").getStake().toString());
        assertEquals("2.76" ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030974L).getLayedPriceWithKey("2.76").getDisplayPrice());
        assertEquals("600.60" ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030974L).getLayedPriceWithKey("2.76").getStake().toString());
        assertEquals(2030975 ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030975L).getSelectionId());
        assertEquals("1.98" ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030975L).getBackedPriceWithKey("1.98").getDisplayPrice());
        assertEquals("474.16" ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030975L).getBackedPriceWithKey("1.98").getStake().toString());
        assertEquals("2" ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030975L).getLayedPriceWithKey("2").getDisplayPrice());
        assertEquals("643.50" ,backLayVolumeCurrencyFormatValid.getSelectionDetailedPriceWithKey(2030975L).getLayedPriceWithKey("2").getStake().toString());
    }

}
