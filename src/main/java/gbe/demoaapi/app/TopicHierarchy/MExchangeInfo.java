package gbe.demoaapi.app.TopicHierarchy;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.AAPIMessageHelper;
import gbe.demoaapi.app.AAPIMessage.AAPIMessageParser;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;

import java.util.Date;

import static gbe.demoaapi.app.AAPIMessage.AAPIMessageParser.*;

public class MExchangeInfo {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(MExchangeInfo.class);

    private MessageAttribute<Long> marketId;
    private MessageAttribute<Integer> marketType;
    private MessageAttribute<Boolean> isPlayMarket;
    private MessageAttribute<Boolean> canBeInRunning;
    private MessageAttribute<Boolean> managedWhenInRunning;
    private MessageAttribute<Boolean> isVisibleAsTradingMarket;
    private MessageAttribute<Boolean> isVisibleAsPricedMarket;
    private MessageAttribute<Boolean> isEnabledForMultiples;
    private MessageAttribute<Boolean> isCurrentlyInRunning;
    private MessageAttribute<Integer> status;
    private MessageAttribute<Integer> withdrawAction;
    private MessageAttribute<Integer> balloutAction;
    private MessageAttribute<Boolean> canBeDeadHeated;
    private MessageAttribute<Date> startTime;
    private MessageAttribute<Integer> delayFactor;
    private MessageAttribute<Integer> numberOfWinningSelections;
    private MessageAttribute<Integer> withdrawalSequenceNumber;
    private MessageAttribute<String> resultString;
    private MessageAttribute<Integer> numberOfSelections;
    private MessageAttribute<String> placePayout; //not supported
    private MessageAttribute<Boolean> redboxSPAvailable;
    private MessageAttribute<Boolean> bOGAvailable;
    private MessageAttribute<Integer> numberWinningPlaces;
    private MessageAttribute<String> placeFraction;

    //all attributes are initialized in constructor
    public MExchangeInfo() {
        this.marketId = new MessageAttribute<Long>();
        this.marketType = new MessageAttribute<Integer>();
        this.isPlayMarket = new MessageAttribute<Boolean>();
        this.canBeInRunning = new MessageAttribute<Boolean>();
        this.managedWhenInRunning = new MessageAttribute<Boolean>();
        this.isVisibleAsTradingMarket = new MessageAttribute<Boolean>();
        this.isVisibleAsPricedMarket = new MessageAttribute<Boolean>();
        this.isEnabledForMultiples = new MessageAttribute<Boolean>();
        this.isCurrentlyInRunning = new MessageAttribute<Boolean>();
        this.status = new MessageAttribute<Integer>();
        this.withdrawAction = new MessageAttribute<Integer>();
        this.balloutAction = new MessageAttribute<Integer>();
        this.canBeDeadHeated = new MessageAttribute<Boolean>();
        this.startTime = new MessageAttribute<Date>();
        this.delayFactor = new MessageAttribute<Integer>();
        this.numberOfWinningSelections = new MessageAttribute<Integer>();
        this.withdrawalSequenceNumber = new MessageAttribute<Integer>();
        this.resultString = new MessageAttribute<String>();
        this.numberOfSelections = new MessageAttribute<Integer>();
        this.placePayout = new MessageAttribute<String>();
        this.redboxSPAvailable = new MessageAttribute<Boolean>();
        this.bOGAvailable = new MessageAttribute<Boolean>();
        this.numberWinningPlaces = new MessageAttribute<Integer>();
        this.placeFraction = new MessageAttribute<String>();
    }

    //region Setters

    public void setMarketId(Long marketId) {
        this.marketId.setValue(marketId);
    }

    public void setMarketType(Integer marketType) {
        this.marketType.setValue(marketType);
    }

    public void setIsPlayMarket(Boolean isPlayMarket) {
        this.isPlayMarket.setValue(isPlayMarket);
    }

    public void setCanBeInRunning(Boolean canBeInRunning) {
        this.canBeInRunning.setValue(canBeInRunning);
    }

    public void setManagedWhenInRunning(Boolean managedWhenInRunning) {
        this.managedWhenInRunning.setValue(managedWhenInRunning);
    }

    public void setIsVisibleAsTradingMarket(Boolean isVisibleAsTradingMarket) {
        this.isVisibleAsTradingMarket.setValue(isVisibleAsTradingMarket);
    }

    public void setIsVisibleAsPricedMarket(Boolean isVisibleAsPricedMarket) {
        this.isVisibleAsPricedMarket.setValue(isVisibleAsPricedMarket);
    }

    public void setIsEnabledForMultiples(Boolean isEnabledForMultiples) {
        this.isEnabledForMultiples.setValue(isEnabledForMultiples);
    }

    public void setIsCurrentlyInRunning(Boolean isCurrentlyInRunning) {
        this.isCurrentlyInRunning.setValue(isCurrentlyInRunning);
    }

    public void setStatus(Integer status) {
        this.status.setValue(status);
    }

    public void setWithdrawAction(Integer withdrawAction) {
        this.withdrawAction.setValue(withdrawAction);
    }

    public void setBalloutAction(Integer balloutAction) {
        this.balloutAction.setValue(balloutAction);
    }

    public void setCanBeDeadHeated(Boolean canBeDeadHeated) {
        this.canBeDeadHeated.setValue(canBeDeadHeated);
    }

    public void setStartTime(Date startTime) {
        this.startTime.setValue(startTime);
    }

    public void setDelayFactor(Integer delayFactor) {
        this.delayFactor.setValue(delayFactor);
    }

    public void setNumberOfWinningSelections(Integer numberOfWinningSelections) {
        this.numberOfWinningSelections.setValue(numberOfWinningSelections);
    }

    public void setWithdrawalSequenceNumber(Integer withdrawalSequenceNumber) {
        this.withdrawalSequenceNumber.setValue(withdrawalSequenceNumber);
    }

    public void setResultString(String resultString) {
        this.resultString.setValue(resultString);
    }

    public void setNumberOfSelections(Integer numberOfSelections) {
        this.numberOfSelections.setValue(numberOfSelections);
    }

    public void setPlacePayout(String placePayout) {
        this.placePayout.setValue(placePayout);
    }

    public void setRedboxSPAvailable(Boolean redboxSPAvailable) {
        this.redboxSPAvailable.setValue(redboxSPAvailable);
    }

    public void setbOGAvailable(Boolean bOGAvailable) {
        this.bOGAvailable.setValue(bOGAvailable);
    }

    public void setNumberWinningPlaces(Integer numberWinningPlaces) {
        this.numberWinningPlaces.setValue(numberWinningPlaces);
    }

    public void setPlaceFraction(String placeFraction) {
        this.placeFraction.setValue(placeFraction);
    }

    public static MExchangeInfo parse(AAPIMessage message) throws APIException {
        AAPIMessageParser messageParser = new AAPIMessageParser(message);
        return parseMessage(messageParser);
    }

    //endregion

    //region Getters

    public MessageAttribute<Long> getMarketId() {
        return marketId;
    }

    public MessageAttribute<Integer> getMarketType() {
        return marketType;
    }

    public MessageAttribute<Boolean> getIsPlayMarket() {
        return isPlayMarket;
    }

    public MessageAttribute<Boolean> getCanBeInRunning() {
        return canBeInRunning;
    }

    public MessageAttribute<Boolean> getManagedWhenInRunning() {
        return managedWhenInRunning;
    }

    public MessageAttribute<Boolean> getIsVisibleAsTradingMarket() {
        return isVisibleAsTradingMarket;
    }

    public MessageAttribute<Boolean> getIsVisibleAsPricedMarket() {
        return isVisibleAsPricedMarket;
    }

    public MessageAttribute<Boolean> getIsEnabledForMultiples() {
        return isEnabledForMultiples;
    }

    public MessageAttribute<Boolean> getIsCurrentlyInRunning() {
        return isCurrentlyInRunning;
    }

    public MessageAttribute<Integer> getStatus() {
        return status;
    }

    public MessageAttribute<Integer> getWithdrawAction() {
        return withdrawAction;
    }

    public MessageAttribute<Integer> getBalloutAction() {
        return balloutAction;
    }

    public MessageAttribute<Boolean> getCanBeDeadHeated() {
        return canBeDeadHeated;
    }

    public MessageAttribute<Date> getStartTime() {
        return startTime;
    }

    public MessageAttribute<Integer> getDelayFactor() {
        return delayFactor;
    }

    public MessageAttribute<Integer> getNumberOfWinningSelections() {
        return numberOfWinningSelections;
    }

    public MessageAttribute<Integer> getWithdrawalSequenceNumber() {
        return withdrawalSequenceNumber;
    }

    public MessageAttribute<String> getResultString() {
        return resultString;
    }

    public MessageAttribute<Integer> getNumberOfSelections() {
        return numberOfSelections;
    }

    public MessageAttribute<String> getPlacePayout() {
        return placePayout;
    }

    public MessageAttribute<Boolean> getRedboxSPAvailable() {
        return redboxSPAvailable;
    }

    public MessageAttribute<Boolean> getbOGAvailable() {
        return bOGAvailable;
    }

    public MessageAttribute<Integer> getNumberWinningPlaces() {
        return numberWinningPlaces;
    }

    public MessageAttribute<String> getPlaceFraction() {
        return placeFraction;
    }

    //endregion

    public static MExchangeInfo parseMessage(AAPIMessageParser messageParser) throws APIException {

        MExchangeInfo toReturnParsed = new MExchangeInfo();
        do {
            Marker currentMarker = messageParser.moveNextOrdinal();
            String fieldValue = messageParser.getFieldValue();

            switch (currentMarker.FieldOrdinal) {
                case 1:
                    toReturnParsed.setMarketId(AAPIMessageHelper.parseLongObject(fieldValue));
                    break;
                case 2:
                    toReturnParsed.setMarketType(AAPIMessageHelper.parseInteger(fieldValue));
                    break;
                case 3:
                    toReturnParsed.setIsPlayMarket(AAPIMessageHelper.parseBooleanObject(fieldValue));
                    break;
                case 4:
                    toReturnParsed.setCanBeInRunning(AAPIMessageHelper.parseBooleanObject(fieldValue));
                    break;
                case 5:
                    toReturnParsed.setManagedWhenInRunning(AAPIMessageHelper.parseBooleanObject(fieldValue));
                    break;
                case 6:
                    toReturnParsed.setIsVisibleAsTradingMarket(AAPIMessageHelper.parseBooleanObject(fieldValue));
                    break;
                case 7:
                    toReturnParsed.setIsVisibleAsPricedMarket(AAPIMessageHelper.parseBooleanObject(fieldValue));
                    break;
                case 8:
                    toReturnParsed.setIsEnabledForMultiples(AAPIMessageHelper.parseBooleanObject(fieldValue));
                    break;
                case 9:
                    toReturnParsed.setIsCurrentlyInRunning(AAPIMessageHelper.parseBooleanObject(fieldValue));
                    break;
                case 10:
                    toReturnParsed.setStatus(AAPIMessageHelper.parseInteger(fieldValue));
                    break;
                case 11:
                    toReturnParsed.setWithdrawAction(AAPIMessageHelper.parseInteger(fieldValue));
                    break;
                case 12:
                    toReturnParsed.setBalloutAction(AAPIMessageHelper.parseInteger(fieldValue));
                    break;
                case 13:
                    toReturnParsed.setCanBeDeadHeated(AAPIMessageHelper.parseBooleanObject(fieldValue));
                    break;
                case 14:
                    toReturnParsed.setStartTime(AAPIMessageHelper.parseDate(fieldValue));
                    break;
                case 15:
                    toReturnParsed.setDelayFactor(AAPIMessageHelper.parseInteger(fieldValue));
                    break;
                case 16:
                    toReturnParsed.setNumberOfWinningSelections(AAPIMessageHelper.parseInteger(fieldValue));
                    break;
                case 17:
                    toReturnParsed.setWithdrawalSequenceNumber(AAPIMessageHelper.parseInteger(fieldValue));
                    break;
                case 18:
                    toReturnParsed.setResultString(fieldValue);
                    break;
                case 19:
                    toReturnParsed.setNumberOfSelections(AAPIMessageHelper.parseInteger(fieldValue));
                    break;
                case 20:
                    toReturnParsed.setPlacePayout(fieldValue);
                    break;
                case 21:
                    toReturnParsed.setRedboxSPAvailable(AAPIMessageHelper.parseBooleanObject(fieldValue));
                    break;
                case 22:
                    toReturnParsed.setbOGAvailable(AAPIMessageHelper.parseBooleanObject(fieldValue));
                    break;
                case 23:
                    toReturnParsed.setNumberWinningPlaces(AAPIMessageHelper.parseInteger(fieldValue));
                    break;
                case 24:
                    toReturnParsed.setPlaceFraction(fieldValue);
                    break;
                default:
                    logger.info(String.format("Attempted to parse an undefined ordinal number [%d] with field name [%s] and value [%s]", currentMarker.FieldOrdinal,  messageParser.getFieldName(), messageParser.getFieldValue()));
                    break;
            }

        } while ( messageParser.readNextRecord() );

        return toReturnParsed;
    }

    public void applyDelta(MExchangeInfo deltaMessage) {
        if(deltaMessage.getMarketId().isSpecified())
            this.setMarketId(deltaMessage.getMarketId().getValue());
        if(deltaMessage.getMarketType().isSpecified())
            this.setMarketType(deltaMessage.getMarketType().getValue());
        if(deltaMessage.getIsPlayMarket().isSpecified())
            this.setIsPlayMarket(deltaMessage.getIsPlayMarket().getValue());
        if(deltaMessage.getCanBeInRunning().isSpecified())
            this.setCanBeInRunning(deltaMessage.getCanBeInRunning().getValue());
        if(deltaMessage.getManagedWhenInRunning().isSpecified())
            this.setManagedWhenInRunning(deltaMessage.getManagedWhenInRunning().getValue());
        if(deltaMessage.getIsVisibleAsTradingMarket().isSpecified())
            this.setIsVisibleAsTradingMarket(deltaMessage.getIsVisibleAsTradingMarket().getValue());
        if(deltaMessage.getIsVisibleAsPricedMarket().isSpecified())
            this.setIsVisibleAsPricedMarket(deltaMessage.getIsVisibleAsPricedMarket().getValue());
        if(deltaMessage.getIsEnabledForMultiples().isSpecified())
            this.setIsEnabledForMultiples(deltaMessage.getIsEnabledForMultiples().getValue());
        if(deltaMessage.getIsCurrentlyInRunning().isSpecified())
            this.setIsCurrentlyInRunning(deltaMessage.getIsCurrentlyInRunning().getValue());
        if(deltaMessage.getStatus().isSpecified())
            this.setStatus(deltaMessage.getStatus().getValue());
        if(deltaMessage.getWithdrawAction().isSpecified())
            this.setWithdrawAction(deltaMessage.getWithdrawAction().getValue());
        if(deltaMessage.getBalloutAction().isSpecified())
            this.setBalloutAction(deltaMessage.getBalloutAction().getValue());
        if(deltaMessage.getCanBeDeadHeated().isSpecified())
            this.setCanBeDeadHeated(deltaMessage.getCanBeDeadHeated().getValue());
        if(deltaMessage.getStartTime().isSpecified())
            this.setStartTime(deltaMessage.getStartTime().getValue());
        if(deltaMessage.getDelayFactor().isSpecified())
            this.setDelayFactor(deltaMessage.getDelayFactor().getValue());
        if(deltaMessage.getNumberOfWinningSelections().isSpecified())
            this.setNumberOfWinningSelections(deltaMessage.getNumberOfWinningSelections().getValue());
        if(deltaMessage.getWithdrawalSequenceNumber().isSpecified())
            this.setWithdrawalSequenceNumber(deltaMessage.getWithdrawalSequenceNumber().getValue());
        if(deltaMessage.getResultString().isSpecified())
            this.setResultString(deltaMessage.getResultString().getValue());
        if(deltaMessage.getNumberOfSelections().isSpecified())
            this.setNumberOfSelections(deltaMessage.getNumberOfSelections().getValue());
        if(deltaMessage.getPlacePayout().isSpecified())
            this.setPlacePayout(deltaMessage.getPlacePayout().getValue());
        if(deltaMessage.getRedboxSPAvailable().isSpecified())
            this.setRedboxSPAvailable(deltaMessage.getRedboxSPAvailable().getValue());
        if(deltaMessage.getbOGAvailable().isSpecified())
            this.setbOGAvailable(deltaMessage.getbOGAvailable().getValue());
        if(deltaMessage.getNumberWinningPlaces().isSpecified())
            this.setNumberWinningPlaces(deltaMessage.getNumberWinningPlaces().getValue());
        if(deltaMessage.getPlaceFraction().isSpecified())
            this.setPlaceFraction(deltaMessage.getPlaceFraction().getValue());
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MExchangeInfo{");
        sb.append("marketId=").append(marketId);
        sb.append(", marketType=").append(marketType);
        sb.append(", isPlayMarket=").append(isPlayMarket);
        sb.append(", canBeInRunning=").append(canBeInRunning);
        sb.append(", managedWhenInRunning=").append(managedWhenInRunning);
        sb.append(", isVisibleAsTradingMarket=").append(isVisibleAsTradingMarket);
        sb.append(", isVisibleAsPricedMarket=").append(isVisibleAsPricedMarket);
        sb.append(", isEnabledForMultiples=").append(isEnabledForMultiples);
        sb.append(", isCurrentlyInRunning=").append(isCurrentlyInRunning);
        sb.append(", status=").append(status);
        sb.append(", withdrawAction=").append(withdrawAction);
        sb.append(", balloutAction=").append(balloutAction);
        sb.append(", canBeDeadHeated=").append(canBeDeadHeated);
        sb.append(", startTime=").append(startTime);
        sb.append(", delayFactor=").append(delayFactor);
        sb.append(", numberOfWinningSelections=").append(numberOfWinningSelections);
        sb.append(", withdrawalSequenceNumber=").append(withdrawalSequenceNumber);
        sb.append(", resultString=").append(resultString);
        sb.append(", numberOfSelections=").append(numberOfSelections);
        sb.append(", placePayout=").append(placePayout);
        sb.append(", redboxSPAvailable=").append(redboxSPAvailable);
        sb.append(", bOGAvailable=").append(bOGAvailable);
        sb.append(", numberWinningPlaces=").append(numberWinningPlaces);
        sb.append(", placeFraction=").append(placeFraction);
        sb.append('}');
        return sb.toString();
    }
}
