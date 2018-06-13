package gbe.demoaapi.app.SubscriptionCommands;

import java.util.List;

import static gbe.demoaapi.app.CommonFunctions.getBoolAsAAPIString;
import static gbe.demoaapi.app.CommonFunctions.getListOfStringsAsAAPIString;
import static gbe.demoaapi.app.MessageConstructor.AddRecord;
import static gbe.demoaapi.app.MessageConstructor.Initialise;

public class SubscribeMarketInformationRequest {

    private static final int commandId = 9;
    private static final int marketIdsOrdinal = 6;
    private static final int fetchOnlyOrdinal = 7;
    private static final int wantSelectionInformationOrdinal = 8;
    private static final int wantExchangeLanguageInformationOnlyOrdinal = 9;
    private static final int marketTaggedValueTopicNamesOrdinal = 10;
    private static final int excludeLanguageTopicsOrdinal = 11;
    private static final int wantSelectionBlurbOrdinal = 12;

    private List<String> marketIds;
    private boolean fetchOnly;
    private boolean wantSelectionInformation;
    private boolean wantExchangeLanguageInformationOnly;
    private List<String> marketTaggedValueTopicNames;
    private boolean excludeLanguageTopics;
    private boolean wantSelectionBlurb;


    public SubscribeMarketInformationRequest(List<String> marketIds, boolean fetchOnly, boolean wantSelectionInformation,
                                             boolean wantExchangeLanguageInformationOnly, List<String> marketTaggedValueTopicNames,
                                             boolean excludeLanguageTopics, boolean wantSelectionBlurb) {
        this.marketIds = marketIds;
        this.fetchOnly = fetchOnly;
        this.wantSelectionInformation = wantSelectionInformation;
        this.wantExchangeLanguageInformationOnly = wantExchangeLanguageInformationOnly;
        this.marketTaggedValueTopicNames = marketTaggedValueTopicNames;
        this.excludeLanguageTopics = excludeLanguageTopics;
        this.wantSelectionBlurb = wantSelectionBlurb;
    }

    public String getAsAAPIMessageWithCorrelationId(int correlationId){
        String messageToSend;
        messageToSend = Initialise(commandId, correlationId);
        messageToSend = AddRecord(messageToSend, marketIdsOrdinal, getListOfStringsAsAAPIString(marketIds));
        messageToSend = AddRecord(messageToSend, fetchOnlyOrdinal, getBoolAsAAPIString(fetchOnly));
        messageToSend = AddRecord(messageToSend, wantSelectionInformationOrdinal, getBoolAsAAPIString(wantSelectionInformation));
        messageToSend = AddRecord(messageToSend, wantExchangeLanguageInformationOnlyOrdinal, getBoolAsAAPIString(wantExchangeLanguageInformationOnly));

        if(marketTaggedValueTopicNames != null)
            messageToSend = AddRecord(messageToSend, marketTaggedValueTopicNamesOrdinal, getListOfStringsAsAAPIString(marketTaggedValueTopicNames));

        messageToSend = AddRecord(messageToSend, excludeLanguageTopicsOrdinal, getBoolAsAAPIString(excludeLanguageTopics));
        messageToSend = AddRecord(messageToSend, wantSelectionBlurbOrdinal, getBoolAsAAPIString(wantSelectionBlurb));
        return messageToSend;
    }

}
