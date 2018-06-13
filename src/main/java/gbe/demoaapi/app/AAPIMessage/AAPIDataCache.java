package gbe.demoaapi.app.AAPIMessage;

import gbe.demoaapi.app.SubscriptionCommands.*;
import gbe.demoaapi.app.SubscriptionCommands.Handlers.PingResponseHandler;
import gbe.demoaapi.app.TopicHierarchy.*;

import java.util.HashMap;
import java.util.Map;

//Class to hold all of the parsed information currently not used as the information is printed on the screen
public class AAPIDataCache {

    private Map<Integer, Event1> eventIdToEvent1Map;
    private Map<Integer, EExchangeInfo> eventIdToEExchangeIngoMap;

    private Map<Integer, BackLayVolumeCurrencyFormat> marketIdToBackLayVolumeCurrencyFormatMap;
    private Map<Integer, Market1> marketIdToMarket1Map;
    private Map<Integer, MExchangeInfo> marketIdToMExchangeInfoMap;
    private Map<Integer, Language3> marketIdToLanguage3Map;

    private Map<Integer, Selection1> selectionIdToSelection1Map;
    private Map<Integer, SExchangeInfo> selectionIdToSExchangeInfoMap;
    private Map<Integer, Language6> selectionIdToLanguage6Map;
    private Map<Integer, SelectionBlurb> selectionIdToSelectionBlurbMap;

    private Map<Integer, LogonPunterResponse> correlationIdToLogonPunterResponse;
    private Map<Integer, SubscribeDetailedMarketPricesResponse> correlationIdToSubscribeDetailedMarketPricesResponse;
    private Map<Integer, SubscribeMarketInformationResponse> correlationIdToSubscribeMarketInformationResponse;
    private Map<Integer, SubscribeEventHierarchyResponse> correlationIdToSubscribeEventHierarchyResponse;
    private Map<Integer, UnsubscribeResponse> correlationIdToUnsubscribeResponse;
    private Map<Integer, PingResponse> correlationIdToPingResponse;

    private int applyDeltaCounter;

    public AAPIDataCache() {
        this.marketIdToBackLayVolumeCurrencyFormatMap = new HashMap<Integer, BackLayVolumeCurrencyFormat>();
        this.correlationIdToSubscribeDetailedMarketPricesResponse = new HashMap<Integer, SubscribeDetailedMarketPricesResponse>();
        this.correlationIdToLogonPunterResponse = new HashMap<Integer, LogonPunterResponse>();
        this.correlationIdToSubscribeMarketInformationResponse = new HashMap<Integer, SubscribeMarketInformationResponse>();
        this.marketIdToMarket1Map = new HashMap<Integer, Market1>();
        this.marketIdToMExchangeInfoMap = new HashMap<Integer, MExchangeInfo>();
        this.marketIdToLanguage3Map = new HashMap<Integer, Language3>();
        this.selectionIdToSelection1Map = new HashMap<Integer, Selection1>();
        this.selectionIdToSExchangeInfoMap = new HashMap<Integer, SExchangeInfo>();
        this.selectionIdToLanguage6Map = new HashMap<Integer, Language6>();
        this.selectionIdToSelectionBlurbMap = new HashMap<Integer, SelectionBlurb>();
        this.correlationIdToSubscribeEventHierarchyResponse = new HashMap<Integer, SubscribeEventHierarchyResponse>();
        this.eventIdToEvent1Map = new HashMap<Integer, Event1>();
        this.correlationIdToUnsubscribeResponse = new HashMap<Integer, UnsubscribeResponse>();
        this.correlationIdToPingResponse = new HashMap<Integer, PingResponse>();
        this.eventIdToEExchangeIngoMap = new HashMap<Integer, EExchangeInfo>();
    }

    public Map<Integer, BackLayVolumeCurrencyFormat> getMarketIdToBackLayVolumeCurrencyFormatMap() {
        return marketIdToBackLayVolumeCurrencyFormatMap;
    }

    public Map<Integer, SubscribeDetailedMarketPricesResponse> getCorrelationIdToSubscribeDetailedMarketPricesResponse() {
        return correlationIdToSubscribeDetailedMarketPricesResponse;
    }

    public Map<Integer, LogonPunterResponse> getCorrelationIdToLogonPunterResponse() {
        return correlationIdToLogonPunterResponse;
    }

    public Map<Integer, SubscribeMarketInformationResponse> getCorrelationIdToSubscribeMarketInformationResponse() {
        return correlationIdToSubscribeMarketInformationResponse;
    }

    public Map<Integer, Market1> getMarketIdToMarket1Map() {
        return marketIdToMarket1Map;
    }

    public Map<Integer, MExchangeInfo> getMarketIdToMExchangeInfoMap() {
        return marketIdToMExchangeInfoMap;
    }

    public Map<Integer, Language3> getMarketIdToLanguage3Map() {
        return marketIdToLanguage3Map;
    }

    public Map<Integer, Selection1> getSelectionIdToSelection1Map() {
        return selectionIdToSelection1Map;
    }

    public Map<Integer, SExchangeInfo> getSelectionIdToSExchangeInfoMap() {
        return selectionIdToSExchangeInfoMap;
    }

    public Map<Integer, Language6> getSelectionIdToLanguage6Map() {
        return selectionIdToLanguage6Map;
    }

    public Map<Integer, SelectionBlurb> getSelectionIdToSelectionBlurbMap() {
        return selectionIdToSelectionBlurbMap;
    }

    public Map<Integer, SubscribeEventHierarchyResponse> getCorrelationIdToSubscribeEventHierarchyResponse() {
        return correlationIdToSubscribeEventHierarchyResponse;
    }

    public Map<Integer, Event1> getEventIdToEvent1Map() {
        return eventIdToEvent1Map;
    }

    public Map<Integer, UnsubscribeResponse> getCorrelationIdToUnsubscribeResponse() {
        return correlationIdToUnsubscribeResponse;
    }

    public Map<Integer, PingResponse> getCorrelationIdToPingResponse() {
        return correlationIdToPingResponse;
    }

    public Map<Integer, EExchangeInfo> getEventIdToEExchangeIngoMap() {
        return eventIdToEExchangeIngoMap;
    }

    public int getApplyDeltaCounter() {
        return applyDeltaCounter;
    }

    public void resetDeltaCounter() {
        this.applyDeltaCounter = 0;
    }
    public void addDeltaToCount(){
        this.applyDeltaCounter++;
    }

}
