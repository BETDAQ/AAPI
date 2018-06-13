package gbe.demoaapi.app.SubscriptionCommands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.SubscriptionCommands.LogonPunterResponse;

import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class LogonPunterResponseTests {

    private static final String validLogonPunterResponse = "AAPI/6/D\u00022\u0002F\u0001" +
            "0\u0002705\u0001" +
            "1\u00020\u0001" +
            "2\u0002F\u0001" +
            "3\u0002F\u0001" +
            "4\u0002-1\u0001" +
            "5\u0002T\u0001" +
            "6\u0002T\u0001" +
            "7\u0002F\u0001" +
            "8\u0002EUR\u0001" +
            "9\u0002--\u0001" +
            "10\u00021\u0001" +
            "11\u00020.00\u0001" +
            "13\u000210.163.0.4|123|STMTEuroPunter|f880feec-f152-44a0-876c-c1e2b5389b8d|2018-05-15T13:40:05.785Z|XDO5pzgDN12Yzq0WgzIV4hGU7bCUf17JyCcOSgkLG95VKRCtW/CaCHcqxrsypwUt+NY2DalEelwTunNJ0Hlyq97DEEb9OgCwIrETc2y+FKvTF85iNXg6/kbF00jKEgFMp0cpFcxXiK9nCMa7mpf880XqwhYppTaqBSjqjdWi2OHDpQjypZCRr+1BXPu4lXKaSmTOTm5ZOriBHk3dzRvsZMoRaVnrDw7g6uTV57X05CxohoZmwtK71bHO00qhTWebrwuTrbHbR6RKpGU6ShVOhzaKkM0J/IYUyl1R+PLFpykDefS0SKK1JkK2u6Lmir/7AuRI15Bxe0iLxk6qWQ7yBLxxwI+tb4wkkO0lVrDQZ3V3E6IYW7BK+x2qx0Hq7BsUgQQvraAPRhc5b9cgkftZklMMksQskGNCSKDiCxTXJmXu0QpfVwk6n2h1OVIRVFETz53lhCdYLom2yl7A4HtRZqfxbOYPmdgKsOkLYAjWm1nCRevjNQoqoi/BAzneEj/DZDsWRwDucYUytZiNA3z16AH2XrLvlL+kF03EryaRxhpA8bqh29anzGDG0GjjAF7ggyUAyWkvZgHocmUwD0LvPO2at5Xf6tCHZjmnTFI9g0dkg8XmbLR4rh6HIUpYDzuXZ9Gxx3rIAAnEHHYZR/+I0kcZSDLe38DTQrKkAVl3BMo=\u0001" +
            "15\u0002400\u0001" +
            "16\u0002500\u0001" +
            "17\u0002500\u0001" +
            "18\u0002500\u0001";


    @Test
    public void parseValidLogonPunterResponse() throws APIException {


        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validLogonPunterResponse);
        LogonPunterResponse logonPunterResponse = LogonPunterResponse.parse(parsedAAPIMessage);

        assertEquals(705 ,logonPunterResponse.getCorrelationId());
        assertEquals("0" ,logonPunterResponse.getResponseCode());
        assertEquals(false ,logonPunterResponse.isDebitSportsbookStake());
        assertEquals(false ,logonPunterResponse.isDebitExchangeStake());
        assertEquals("-1" ,logonPunterResponse.getPurseIntegrationMode());
        assertEquals(true ,logonPunterResponse.isCanPlaceForSideOrders());
        assertEquals(true ,logonPunterResponse.isCanPlaceAgainstSideOrders());
        assertEquals(false ,logonPunterResponse.isRestrictedToFillKillOrders());
        assertEquals("EUR" ,logonPunterResponse.getCurrency());
        assertEquals("--" ,logonPunterResponse.getLanguage());
        assertEquals("1" ,logonPunterResponse.getPriceFormat());
        assertEquals("0.00",logonPunterResponse.getMarketByVolumeAmount().toString());
        assertEquals("10.163.0.4|123|STMTEuroPunter|f880feec-f152-44a0-876c-c1e2b5389b8d|2018-05-15T13:40:05.785Z|XDO5pzgDN12Yzq0WgzIV4hGU7bCUf17JyCcOSgkLG95VKRCtW/CaCHcqxrsypwUt+NY2DalEelwTunNJ0Hlyq97DEEb9OgCwIrETc2y+FKvTF85iNXg6/kbF00jKEgFMp0cpFcxXiK9nCMa7mpf880XqwhYppTaqBSjqjdWi2OHDpQjypZCRr+1BXPu4lXKaSmTOTm5ZOriBHk3dzRvsZMoRaVnrDw7g6uTV57X05CxohoZmwtK71bHO00qhTWebrwuTrbHbR6RKpGU6ShVOhzaKkM0J/IYUyl1R+PLFpykDefS0SKK1JkK2u6Lmir/7AuRI15Bxe0iLxk6qWQ7yBLxxwI+tb4wkkO0lVrDQZ3V3E6IYW7BK+x2qx0Hq7BsUgQQvraAPRhc5b9cgkftZklMMksQskGNCSKDiCxTXJmXu0QpfVwk6n2h1OVIRVFETz53lhCdYLom2yl7A4HtRZqfxbOYPmdgKsOkLYAjWm1nCRevjNQoqoi/BAzneEj/DZDsWRwDucYUytZiNA3z16AH2XrLvlL+kF03EryaRxhpA8bqh29anzGDG0GjjAF7ggyUAyWkvZgHocmUwD0LvPO2at5Xf6tCHZjmnTFI9g0dkg8XmbLR4rh6HIUpYDzuXZ9Gxx3rIAAnEHHYZR/+I0kcZSDLe38DTQrKkAVl3BMo=" ,logonPunterResponse.getaAPISessionToken());
        assertEquals(0L ,logonPunterResponse.getMaximumMessageSize());
        assertEquals("400" ,logonPunterResponse.getMaximumMarketInformationMarketsCount().toString());
        assertEquals("500" ,logonPunterResponse.getMaximumMarketPricesMarketsCount().toString());
        assertEquals("500" ,logonPunterResponse.getMaximumMarketMatchedAmountsMarketsCount().toString());
        assertEquals("500" ,logonPunterResponse.getMaximumMarketFixedOddsPricesMarketsCount().toString());

    }

    @Test
    public void parseValidLogonPunterResponse_DisplayAsString() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validLogonPunterResponse);
        LogonPunterResponse subscribeDetailedMarketPricesResponse = LogonPunterResponse.parse(parsedAAPIMessage);


        Gson g = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = g.toJson(subscribeDetailedMarketPricesResponse);
        //System.out.println(jsonString);
        assertEquals("{\n" +
                "  \"correlationId\": 705,\n" +
                "  \"responseCode\": \"0\",\n" +
                "  \"debitSportsbookStake\": false,\n" +
                "  \"debitExchangeStake\": false,\n" +
                "  \"purseIntegrationMode\": \"-1\",\n" +
                "  \"canPlaceForSideOrders\": true,\n" +
                "  \"canPlaceAgainstSideOrders\": true,\n" +
                "  \"restrictedToFillKillOrders\": false,\n" +
                "  \"currency\": \"EUR\",\n" +
                "  \"language\": \"--\",\n" +
                "  \"priceFormat\": \"1\",\n" +
                "  \"marketByVolumeAmount\": 0.00,\n" +
                "  \"aAPISessionToken\": \"10.163.0.4|123|STMTEuroPunter|f880feec-f152-44a0-876c-c1e2b5389b8d|2018-05-15T13:40:05.785Z|XDO5pzgDN12Yzq0WgzIV4hGU7bCUf17JyCcOSgkLG95VKRCtW/CaCHcqxrsypwUt+NY2DalEelwTunNJ0Hlyq97DEEb9OgCwIrETc2y+FKvTF85iNXg6/kbF00jKEgFMp0cpFcxXiK9nCMa7mpf880XqwhYppTaqBSjqjdWi2OHDpQjypZCRr+1BXPu4lXKaSmTOTm5ZOriBHk3dzRvsZMoRaVnrDw7g6uTV57X05CxohoZmwtK71bHO00qhTWebrwuTrbHbR6RKpGU6ShVOhzaKkM0J/IYUyl1R+PLFpykDefS0SKK1JkK2u6Lmir/7AuRI15Bxe0iLxk6qWQ7yBLxxwI+tb4wkkO0lVrDQZ3V3E6IYW7BK+x2qx0Hq7BsUgQQvraAPRhc5b9cgkftZklMMksQskGNCSKDiCxTXJmXu0QpfVwk6n2h1OVIRVFETz53lhCdYLom2yl7A4HtRZqfxbOYPmdgKsOkLYAjWm1nCRevjNQoqoi/BAzneEj/DZDsWRwDucYUytZiNA3z16AH2XrLvlL+kF03EryaRxhpA8bqh29anzGDG0GjjAF7ggyUAyWkvZgHocmUwD0LvPO2at5Xf6tCHZjmnTFI9g0dkg8XmbLR4rh6HIUpYDzuXZ9Gxx3rIAAnEHHYZR/+I0kcZSDLe38DTQrKkAVl3BMo\\u003d\",\n" +
                "  \"maximumMessageSize\": 0,\n" +
                "  \"maximumMarketInformationMarketsCount\": 400,\n" +
                "  \"maximumMarketPricesMarketsCount\": 500,\n" +
                "  \"maximumMarketMatchedAmountsMarketsCount\": 500,\n" +
                "  \"maximumMarketFixedOddsPricesMarketsCount\": 500\n" +
                "}",jsonString);

    }

}
