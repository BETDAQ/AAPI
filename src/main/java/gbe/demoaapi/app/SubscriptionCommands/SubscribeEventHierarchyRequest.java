package gbe.demoaapi.app.SubscriptionCommands;

import java.util.List;

import static gbe.demoaapi.app.CommonFunctions.getBoolAsAAPIString;
import static gbe.demoaapi.app.CommonFunctions.getListOfStringsAsAAPIString;
import static gbe.demoaapi.app.MessageConstructor.AddRecord;
import static gbe.demoaapi.app.MessageConstructor.Initialise;

public class SubscribeEventHierarchyRequest {

    private static final int commandId = 12;
    private static final int eventClassifierIdOrdinal = 2;
    private static final int wantDirectDescendantsOnlyOrdinal = 3;
    private static final int wantSelectionInformationOrdinal = 4;
    private static final int fetchOnlyOrdinal = 5;
    private static final int marketTypesToExcludeOrdinal = 6;
    private static final int marketTypesToIncludeOrdinal = 7;
    private static final int wantExchangeLanguageInformationOnlyOrdinal = 8;
    private static final int eventTaggedValueTopicNamesOrdinal = 9;
    private static final int marketTaggedValueTopicNamesOrdinal = 10;
    private static final int excludeMarketInformationOrdinal = 11;
    private static final int wantTabInformationOrdinal = 12;
    private static final int excludeLanguageTopicsOrdinal = 13;
    private static final int wantSelectionBlurbOrdinal = 14;

    private long eventClassifierId;
    private boolean wantDirectDescendantsOnly;
    private boolean wantSelectionInformation;
    private boolean fetchOnly;
    private List<String> marketTypesToExclude;
    private List<String> marketTypesToInclude;
    private boolean wantExchangeLanguageInformationOnly;
    private List<String> eventTaggedValueTopicNames;
    private List<String> marketTaggedValueTopicNames;
    private boolean excludeMarketInformation;
    private boolean wantTabInformation;
    private boolean excludeLanguageTopics;
    private boolean wantSelectionBlurb;

    public SubscribeEventHierarchyRequest(long eventClassifierId, boolean wantDirectDescendantsOnly, boolean wantSelectionInformation,
                                          boolean fetchOnly, List<String> marketTypesToExclude, List<String> marketTypesToInclude,
                                          boolean wantExchangeLanguageInformationOnly, List<String> eventTaggedValueTopicNames, List<String> marketTaggedValueTopicNames,
                                          boolean excludeMarketInformation, boolean wantTabInformation, boolean excludeLanguageTopics, boolean wantSelectionBlurb) {
        this.eventClassifierId = eventClassifierId;
        this.wantDirectDescendantsOnly = wantDirectDescendantsOnly;
        this.wantSelectionInformation = wantSelectionInformation;
        this.fetchOnly = fetchOnly;
        this.marketTypesToExclude = marketTypesToExclude;
        this.marketTypesToInclude = marketTypesToInclude;
        this.wantExchangeLanguageInformationOnly = wantExchangeLanguageInformationOnly;
        this.eventTaggedValueTopicNames = eventTaggedValueTopicNames;
        this.marketTaggedValueTopicNames = marketTaggedValueTopicNames;
        this.excludeMarketInformation = excludeMarketInformation;
        this.wantTabInformation = wantTabInformation;
        this.excludeLanguageTopics = excludeLanguageTopics;
        this.wantSelectionBlurb = wantSelectionBlurb;
    }



    public String getAsAAPIMessageWithCorrelationId(int correlationId){
        String messageToSend;
        messageToSend = Initialise(commandId, correlationId);
        messageToSend = AddRecord(messageToSend, eventClassifierIdOrdinal, Long.toString(eventClassifierId));
        messageToSend = AddRecord(messageToSend, wantDirectDescendantsOnlyOrdinal, getBoolAsAAPIString(wantDirectDescendantsOnly));
        messageToSend = AddRecord(messageToSend, wantSelectionInformationOrdinal, getBoolAsAAPIString(wantSelectionInformation));
        messageToSend = AddRecord(messageToSend, fetchOnlyOrdinal, getBoolAsAAPIString(fetchOnly));
        messageToSend = AddRecord(messageToSend, wantExchangeLanguageInformationOnlyOrdinal, getBoolAsAAPIString(wantExchangeLanguageInformationOnly));
        messageToSend = AddRecord(messageToSend, excludeMarketInformationOrdinal, getBoolAsAAPIString(excludeMarketInformation));
        messageToSend = AddRecord(messageToSend, wantTabInformationOrdinal, getBoolAsAAPIString(wantTabInformation));
        messageToSend = AddRecord(messageToSend, excludeLanguageTopicsOrdinal, getBoolAsAAPIString(excludeLanguageTopics));
        messageToSend = AddRecord(messageToSend, wantSelectionBlurbOrdinal, getBoolAsAAPIString(wantSelectionBlurb));

        return messageToSend;
    }

}
