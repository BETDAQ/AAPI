package gbe.demoaapi.app.TopicHierarchy;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.AAPIMessageHelper;
import gbe.demoaapi.app.AAPIMessage.AAPIMessageParser;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;

import static gbe.demoaapi.app.AAPIMessage.AAPIMessageParser.*;

public class SExchangeInfo {
    private final static ConsoleLogger logger = LoggerFactory.getLogger(SExchangeInfo.class);

    private MessageAttribute<Long> selectionId;
    private MessageAttribute<Integer> status;
    private MessageAttribute<Integer> selectionResetCount;
    private MessageAttribute<BigDecimal> withdrawalFactor;
    private MessageAttribute<Date> settledTime;
    private MessageAttribute<String> resultString;
    private MessageAttribute<BigDecimal> voidPercentage;
    private MessageAttribute<BigDecimal> leftSideFactor;
    private MessageAttribute<BigDecimal> rightSideFactor;

    //region Setters Getters Constructor

    public SExchangeInfo() {
        this.selectionId = new MessageAttribute<Long>();
        this.status = new MessageAttribute<Integer>();
        this.selectionResetCount = new MessageAttribute<Integer>();
        this.withdrawalFactor = new MessageAttribute<BigDecimal>();
        this.settledTime = new MessageAttribute<Date>();
        this.resultString = new MessageAttribute<String>();
        this.voidPercentage = new MessageAttribute<BigDecimal>();
        this.leftSideFactor = new MessageAttribute<BigDecimal>();
        this.rightSideFactor = new MessageAttribute<BigDecimal>();
    }

    public void setSelectionId(Long selectionId) {
        this.selectionId.setValue(selectionId);
    }

    public void setStatus(Integer status) {
        this.status.setValue(status);
    }

    public void setSelectionResetCount(Integer selectionResetCount) {
        this.selectionResetCount.setValue(selectionResetCount);
    }

    public void setWithdrawalFactor(BigDecimal withdrawalFactor) {
        this.withdrawalFactor.setValue(withdrawalFactor);
    }

    public void setSettledTime(Date settledTime) {
        this.settledTime.setValue(settledTime);
    }

    public void setResultString(String resultString) {
        this.resultString.setValue(resultString);
    }

    public void setVoidPercentage(BigDecimal voidPercentage) {
        this.voidPercentage.setValue(voidPercentage);
    }

    public void setLeftSideFactor(BigDecimal leftSideFactor) {
        this.leftSideFactor.setValue(leftSideFactor);
    }

    public void setRightSideFactor(BigDecimal rightSideFactor) {
        this.rightSideFactor.setValue(rightSideFactor);
    }

    public MessageAttribute<Long> getSelectionId() {
        return selectionId;
    }

    public MessageAttribute<Integer> getStatus() {
        return status;
    }

    public MessageAttribute<Integer> getSelectionResetCount() {
        return selectionResetCount;
    }

    public MessageAttribute<BigDecimal> getWithdrawalFactor() {
        return withdrawalFactor;
    }

    public MessageAttribute<Date> getSettledTime() {
        return settledTime;
    }

    public MessageAttribute<String> getResultString() {
        return resultString;
    }

    public MessageAttribute<BigDecimal> getVoidPercentage() {
        return voidPercentage;
    }

    public MessageAttribute<BigDecimal> getLeftSideFactor() {
        return leftSideFactor;
    }

    public MessageAttribute<BigDecimal> getRightSideFactor() {
        return rightSideFactor;
    }

    //endregion

    public static SExchangeInfo parse(AAPIMessage message) throws APIException {
        AAPIMessageParser messageParser = new AAPIMessageParser(message);
        return parseMessage(messageParser);
    }

    public static SExchangeInfo parseMessage(AAPIMessageParser messageParser) throws APIException {

        SExchangeInfo toReturnParsed = new SExchangeInfo();
        do {
            Marker currentMarker = messageParser.moveNextOrdinal();
            String fieldValue = messageParser.getFieldValue();

            switch (currentMarker.FieldOrdinal) {
                case 1:
                    toReturnParsed.setSelectionId(AAPIMessageHelper.parseLong(fieldValue));
                    break;
                case 2:
                    toReturnParsed.setStatus(AAPIMessageHelper.parseInteger(fieldValue));
                    break;
                case 3:
                    toReturnParsed.setSelectionResetCount(AAPIMessageHelper.parseInteger(fieldValue));
                    break;
                case 4:
                    toReturnParsed.setWithdrawalFactor(AAPIMessageHelper.parseBigDecimal(fieldValue));
                    break;
                case 5:
                    toReturnParsed.setSettledTime(AAPIMessageHelper.parseDate(fieldValue));
                    break;
                case 6:
                    toReturnParsed.setResultString(fieldValue);
                    break;
                case 7:
                    toReturnParsed.setVoidPercentage(AAPIMessageHelper.parseBigDecimal(fieldValue));
                    break;
                case 8:
                    toReturnParsed.setLeftSideFactor(AAPIMessageHelper.parseBigDecimal(fieldValue));
                    break;
                case 9:
                    toReturnParsed.setRightSideFactor(AAPIMessageHelper.parseBigDecimal(fieldValue));
                    break;
                default:
                    logger.info(String.format("Attempted to parse an undefined ordinal number [%d] with field name [%s] and value [%s]", currentMarker.FieldOrdinal,  messageParser.getFieldName(), messageParser.getFieldValue()));
                    break;
            }

        } while ( messageParser.readNextRecord() );

        return toReturnParsed;
    }

    public void applyDelta(SExchangeInfo deltaMessage) {
        if(deltaMessage.getSelectionId().isSpecified())
            this.setSelectionId(deltaMessage.getSelectionId().getValue());
        if(deltaMessage.getStatus().isSpecified())
            this.setStatus(deltaMessage.getStatus().getValue());
        if(deltaMessage.getSelectionResetCount().isSpecified())
            this.setSelectionResetCount(deltaMessage.getSelectionResetCount().getValue());
        if(deltaMessage.getWithdrawalFactor().isSpecified())
            this.setWithdrawalFactor(deltaMessage.getWithdrawalFactor().getValue());
        if(deltaMessage.getSettledTime().isSpecified())
            this.setSettledTime(deltaMessage.getSettledTime().getValue());
        if(deltaMessage.getResultString().isSpecified())
            this.setResultString(deltaMessage.getResultString().getValue());
        if(deltaMessage.getVoidPercentage().isSpecified())
            this.setVoidPercentage(deltaMessage.getVoidPercentage().getValue());
        if(deltaMessage.getLeftSideFactor().isSpecified())
            this.setLeftSideFactor(deltaMessage.getLeftSideFactor().getValue());
        if(deltaMessage.getRightSideFactor().isSpecified())
            this.setRightSideFactor(deltaMessage.getRightSideFactor().getValue());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SExchangeInfo{");
        sb.append("selectionId=").append(selectionId);
        sb.append(", status=").append(status);
        sb.append(", selectionResetCount=").append(selectionResetCount);
        sb.append(", withdrawalFactor=").append(withdrawalFactor);
        sb.append(", settledTime=").append(settledTime);
        sb.append(", resultString=").append(resultString);
        sb.append(", voidPercentage=").append(voidPercentage);
        sb.append(", leftSideFactor=").append(leftSideFactor);
        sb.append(", rightSideFactor=").append(rightSideFactor);
        sb.append('}');
        return sb.toString();
    }
}
