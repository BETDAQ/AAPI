package gbe.demoaapi.app.SubscriptionCommands;

import java.math.BigDecimal;
import java.util.List;

import static gbe.demoaapi.app.CommonFunctions.getBoolAsAAPIString;
import static gbe.demoaapi.app.CommonFunctions.getListOfStringsAsAAPIString;
import static gbe.demoaapi.app.MessageConstructor.AddRecord;
import static gbe.demoaapi.app.MessageConstructor.Initialise;

public class SubscribeDetailedMarketPricesRequest {

    private static final int commandId = 10;
    private static final int marketIdsOrdinal = 5;
    private static final int numberBackPricesOrdinal = 6;
    private static final int numberLayPricesOrdinal = 7;
    private static final int filterByVolumeOrdinal = 8;
    private static final int fetchOnlyOrdinal = 11;

    private List<String> marketIds;
    private int numberBackPrices;
    private int numberLayPrices;
    private BigDecimal filterByVolume;
    private boolean fetchOnly;

    public SubscribeDetailedMarketPricesRequest(List<String> marketIds, int numberBackPrices, int numberLayPrices, BigDecimal filterByVolume, boolean fetchOnly) {
        this.marketIds = marketIds;
        this.numberBackPrices = numberBackPrices;
        this.numberLayPrices = numberLayPrices;
        this.filterByVolume = filterByVolume;
        this.fetchOnly = fetchOnly;
    }

    public String getAsAAPIMessageWithCorrelationId(int correlationId){
        String messageToSend;
        messageToSend = Initialise(commandId, correlationId);
        messageToSend = AddRecord(messageToSend, marketIdsOrdinal, getListOfStringsAsAAPIString(marketIds));
        messageToSend = AddRecord(messageToSend, numberBackPricesOrdinal, Integer.toString(numberBackPrices));
        messageToSend = AddRecord(messageToSend, numberLayPricesOrdinal, Integer.toString(numberLayPrices));
        messageToSend = AddRecord(messageToSend, filterByVolumeOrdinal, filterByVolume.toString());
        messageToSend = AddRecord(messageToSend, fetchOnlyOrdinal, getBoolAsAAPIString(fetchOnly));
        return messageToSend;
    }



}
