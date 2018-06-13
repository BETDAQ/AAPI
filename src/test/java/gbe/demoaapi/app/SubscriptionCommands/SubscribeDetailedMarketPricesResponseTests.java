package gbe.demoaapi.app.SubscriptionCommands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.SubscriptionCommands.SubscribeDetailedMarketPricesResponse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SubscribeDetailedMarketPricesResponseTests {

    public static final String validAAPICommandResponseSubscribeDetailedMarketPrices = "AAPI/6/D\u000210\u0002F\u00010\u0002122\u00011\u00020\u00012\u00021\u00014\u0002499\u0001";


    @Test
    public void parseAAPIMessageContainingValidSubscribeDetailedMarketPricesResponse() throws APIException {


        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPICommandResponseSubscribeDetailedMarketPrices);
        SubscribeDetailedMarketPricesResponse subscribeDetailedMarketPricesResponse = SubscribeDetailedMarketPricesResponse.parse(parsedAAPIMessage);

        assertEquals(122 ,subscribeDetailedMarketPricesResponse.getCorrelationId());
        assertEquals("0" ,subscribeDetailedMarketPricesResponse.getResponseCode());
        assertEquals("1" ,subscribeDetailedMarketPricesResponse.getSubscriptionId().toString());
        assertEquals(null ,subscribeDetailedMarketPricesResponse.getNotActiveMarketIdRequested());
        assertEquals("499" ,subscribeDetailedMarketPricesResponse.getAvailableMarketsCount().toString());

    }

    @Test
    public void parseAAPIMessageContainingValid_DisplayAsString() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPICommandResponseSubscribeDetailedMarketPrices);
        SubscribeDetailedMarketPricesResponse subscribeDetailedMarketPricesResponse = SubscribeDetailedMarketPricesResponse.parse(parsedAAPIMessage);


        Gson g = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = g.toJson(subscribeDetailedMarketPricesResponse);
        //System.out.println(jsonString);
        assertEquals("{\n" +
                "  \"correlationId\": 122,\n" +
                "  \"responseCode\": \"0\",\n" +
                "  \"subscriptionId\": 1,\n" +
                "  \"availableMarketsCount\": 499\n" +
                "}",jsonString);

    }

}
