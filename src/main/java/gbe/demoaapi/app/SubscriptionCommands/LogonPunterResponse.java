package gbe.demoaapi.app.SubscriptionCommands;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.AAPIMessageHelper;
import gbe.demoaapi.app.AAPIMessage.AAPIMessageParser;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.TopicHierarchy.BackLayVolumeCurrencyFormat;
import gbe.demoaapi.app.Logging.ConsoleLogger;
import gbe.demoaapi.app.Logging.LoggerFactory;

import java.math.BigDecimal;

import static gbe.demoaapi.app.AAPIMessage.AAPIMessageParser.*;

public class LogonPunterResponse {

    private final static ConsoleLogger logger = LoggerFactory.getLogger(LogonPunterResponse.class);

    private int correlationId;
    private String responseCode;

    private boolean debitSportsbookStake;
    private boolean debitExchangeStake;
    private String purseIntegrationMode;
    private boolean canPlaceForSideOrders;
    private boolean canPlaceAgainstSideOrders;
    private boolean restrictedToFillKillOrders;
    private String currency;
    private String language;
    private String priceFormat;
    private BigDecimal marketByVolumeAmount;
    private String aAPISessionToken;
    private long maximumMessageSize;
    private Integer maximumMarketInformationMarketsCount;
    private Integer maximumMarketPricesMarketsCount;
    private Integer maximumMarketMatchedAmountsMarketsCount;
    private Integer maximumMarketFixedOddsPricesMarketsCount;


    public void setCorrelationId(int correlationId) {
        this.correlationId = correlationId;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public void setDebitSportsbookStake(boolean debitSportsbookStake) {
        this.debitSportsbookStake = debitSportsbookStake;
    }

    public void setDebitExchangeStake(boolean debitExchangeStake) {
        this.debitExchangeStake = debitExchangeStake;
    }

    public void setPurseIntegrationMode(String purseIntegrationMode) {
        this.purseIntegrationMode = purseIntegrationMode;
    }

    public void setCanPlaceForSideOrders(boolean canPlaceForSideOrders) {
        this.canPlaceForSideOrders = canPlaceForSideOrders;
    }

    public void setCanPlaceAgainstSideOrders(boolean canPlaceAgainstSideOrders) {
        this.canPlaceAgainstSideOrders = canPlaceAgainstSideOrders;
    }

    public void setRestrictedToFillKillOrders(boolean restrictedToFillKillOrders) {
        this.restrictedToFillKillOrders = restrictedToFillKillOrders;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setPriceFormat(String priceFormat) {
        this.priceFormat = priceFormat;
    }

    public void setMarketByVolumeAmount(BigDecimal marketByVolumeAmount) {
        this.marketByVolumeAmount = marketByVolumeAmount;
    }

    public void setaAPISessionToken(String aAPISessionToken) {
        this.aAPISessionToken = aAPISessionToken;
    }

    public void setMaximumMessageSize(long maximumMessageSize) {
        this.maximumMessageSize = maximumMessageSize;
    }

    public void setMaximumMarketInformationMarketsCount(Integer maximumMarketInformationMarketsCount) {
        this.maximumMarketInformationMarketsCount = maximumMarketInformationMarketsCount;
    }

    public void setMaximumMarketPricesMarketsCount(Integer maximumMarketPricesMarketsCount) {
        this.maximumMarketPricesMarketsCount = maximumMarketPricesMarketsCount;
    }

    public void setMaximumMarketMatchedAmountsMarketsCount(Integer maximumMarketMatchedAmountsMarketsCount) {
        this.maximumMarketMatchedAmountsMarketsCount = maximumMarketMatchedAmountsMarketsCount;
    }

    public void setMaximumMarketFixedOddsPricesMarketsCount(Integer maximumMarketFixedOddsPricesMarketsCount) {
        this.maximumMarketFixedOddsPricesMarketsCount = maximumMarketFixedOddsPricesMarketsCount;
    }

    public static LogonPunterResponse parse(AAPIMessage message) throws APIException {
        AAPIMessageParser messageParser = new AAPIMessageParser(message);
        return LogonPunterResponse.parseMessage(messageParser);
    }

    public int getCorrelationId() {
        return correlationId;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public boolean isDebitSportsbookStake() {
        return debitSportsbookStake;
    }

    public boolean isDebitExchangeStake() {
        return debitExchangeStake;
    }

    public String getPurseIntegrationMode() {
        return purseIntegrationMode;
    }

    public boolean isCanPlaceForSideOrders() {
        return canPlaceForSideOrders;
    }

    public boolean isCanPlaceAgainstSideOrders() {
        return canPlaceAgainstSideOrders;
    }

    public boolean isRestrictedToFillKillOrders() {
        return restrictedToFillKillOrders;
    }

    public String getCurrency() {
        return currency;
    }

    public String getLanguage() {
        return language;
    }

    public String getPriceFormat() {
        return priceFormat;
    }

    public BigDecimal getMarketByVolumeAmount() {
        return marketByVolumeAmount;
    }

    public String getaAPISessionToken() {
        return aAPISessionToken;
    }

    public long getMaximumMessageSize() {
        return maximumMessageSize;
    }

    public Integer getMaximumMarketInformationMarketsCount() {
        return maximumMarketInformationMarketsCount;
    }

    public Integer getMaximumMarketPricesMarketsCount() {
        return maximumMarketPricesMarketsCount;
    }

    public Integer getMaximumMarketMatchedAmountsMarketsCount() {
        return maximumMarketMatchedAmountsMarketsCount;
    }

    public Integer getMaximumMarketFixedOddsPricesMarketsCount() {
        return maximumMarketFixedOddsPricesMarketsCount;
    }

    public static LogonPunterResponse parseMessage(AAPIMessageParser messageParser) throws APIException {

        LogonPunterResponse toReturnParsed = new LogonPunterResponse();
        do {
            Marker currentMarker = messageParser.moveNextOrdinal();
            String fieldValue = messageParser.getFieldValue();

            switch (currentMarker.FieldOrdinal) {
                case 0:
                    toReturnParsed.setCorrelationId(AAPIMessageHelper.parseInt(fieldValue));
                    break;
                case 1:
                    toReturnParsed.setResponseCode(fieldValue);
                    break;
                case 2:
                    toReturnParsed.setDebitSportsbookStake(AAPIMessageHelper.parseBoolean(fieldValue));
                    break;
                case 3:
                    toReturnParsed.setDebitExchangeStake(AAPIMessageHelper.parseBoolean(fieldValue));
                    break;
                case 4:
                    toReturnParsed.setPurseIntegrationMode(fieldValue);
                    break;
                case 5:
                    toReturnParsed.setCanPlaceForSideOrders(AAPIMessageHelper.parseBoolean(fieldValue));
                    break;
                case 6:
                    toReturnParsed.setCanPlaceAgainstSideOrders(AAPIMessageHelper.parseBoolean(fieldValue));
                    break;
                case 7:
                    toReturnParsed.setRestrictedToFillKillOrders(AAPIMessageHelper.parseBoolean(fieldValue));
                    break;
                case 8:
                    toReturnParsed.setCurrency(fieldValue);
                    break;
                case 9:
                    toReturnParsed.setLanguage(fieldValue);
                    break;
                case 10:
                    toReturnParsed.setPriceFormat(fieldValue);
                    break;
                case 11:
                    toReturnParsed.setMarketByVolumeAmount(AAPIMessageHelper.parseBigDecimal(fieldValue));
                    break;
                case 13:
                    toReturnParsed.setaAPISessionToken(fieldValue);
                    break;
                case 14:
                    toReturnParsed.setMaximumMessageSize(AAPIMessageHelper.parseLong(fieldValue));
                    break;
                case 15:
                    toReturnParsed.setMaximumMarketInformationMarketsCount(AAPIMessageHelper.parseInteger(fieldValue));
                    break;
                case 16:
                    toReturnParsed.setMaximumMarketPricesMarketsCount(AAPIMessageHelper.parseInteger(fieldValue));
                    break;
                case 17:
                    toReturnParsed.setMaximumMarketMatchedAmountsMarketsCount(AAPIMessageHelper.parseInteger(fieldValue));
                    break;
                case 18:
                    toReturnParsed.setMaximumMarketFixedOddsPricesMarketsCount(AAPIMessageHelper.parseInteger(fieldValue));
                    break;
                default:
                    logger.info(String.format("Attempted to parse an undefined ordinal number [%d] with field name [%s] and value [%s]", currentMarker.FieldOrdinal,  messageParser.getFieldName(), messageParser.getFieldValue()));
                    break;
            }

        } while ( messageParser.readNextRecord() );

        return toReturnParsed;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LogonPunterResponse{");
        sb.append("correlationId=").append(correlationId);
        sb.append(", responseCode='").append(responseCode).append('\'');
        sb.append(", debitSportsbookStake=").append(debitSportsbookStake);
        sb.append(", debitExchangeStake=").append(debitExchangeStake);
        sb.append(", purseIntegrationMode='").append(purseIntegrationMode).append('\'');
        sb.append(", canPlaceForSideOrders=").append(canPlaceForSideOrders);
        sb.append(", canPlaceAgainstSideOrders=").append(canPlaceAgainstSideOrders);
        sb.append(", restrictedToFillKillOrders=").append(restrictedToFillKillOrders);
        sb.append(", currency='").append(currency).append('\'');
        sb.append(", language='").append(language).append('\'');
        sb.append(", priceFormat='").append(priceFormat).append('\'');
        sb.append(", marketByVolumeAmount=").append(marketByVolumeAmount);
        sb.append(", aAPISessionToken='").append(aAPISessionToken).append('\'');
        sb.append(", maximumMessageSize=").append(maximumMessageSize);
        sb.append(", maximumMarketInformationMarketsCount=").append(maximumMarketInformationMarketsCount);
        sb.append(", maximumMarketPricesMarketsCount=").append(maximumMarketPricesMarketsCount);
        sb.append(", maximumMarketMatchedAmountsMarketsCount=").append(maximumMarketMatchedAmountsMarketsCount);
        sb.append(", maximumMarketFixedOddsPricesMarketsCount=").append(maximumMarketFixedOddsPricesMarketsCount);
        sb.append('}');
        return sb.toString();
    }
}
