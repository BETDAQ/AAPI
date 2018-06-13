package gbe.demoaapi.app.TopicHierarchyTests;

import gbe.demoaapi.app.AAPIMessage.AAPIMessage;
import gbe.demoaapi.app.AAPIMessage.APIException;
import gbe.demoaapi.app.TopicHierarchy.MExchangeInfo;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MExchangeInfoTests {

    public static final String validAAPIMExchangeInfoResponseFull = "AAPI/6/E/E_1/E/E_100003/E/E_190659/E/E_3969213/E/E_4088802/M/E_314610/MEI\u0002\u0002T\u0001" +
            "1\u0002314610\u0001" +
            "2\u00023\u0001" +
            "3\u0002F\u0001" +
            "4\u0002T\u0001" +
            "5\u0002T\u0001" +
            "6\u0002T\u0001" +
            "7\u0002F\u0001" +
            "8\u0002T\u0001" +
            "9\u0002F\u0001" +
            "10\u00022\u0001" +
            "11\u00023\u0001" +
            "12\u00024\u0001" +
            "13\u0002T\u0001" +
            "14\u00022018-06-20T18:00:00.000Z\u0001" +
            "15\u00020\u0001" +
            "16\u00021\u0001" +
            "17\u00020\u0001" +
            "18\u00021-3\u0001" +
            "19\u00023\u0001" +
            "20\u000225%\u0001" +
            "21\u0002T\u0001" +
            "22\u0002T\u0001" +
            "23\u000222\u0001" +
            "24\u0002fra\u0001";

    public static final String validAAPIMExchangeInfoResponsePartial = "AAPI/6/E/E_1/E/E_100003/E/E_190659/E/E_3969213/E/E_4088802/M/E_314610/MEI\u0002\u0002T\u0001" +
            "1\u0002314610\u0001" +
            "2\u00023\u0001" +
            "3\u0002F\u0001" +
            "4\u0002T\u0001" +
            "5\u0002T\u0001" +
            "6\u0002T\u0001" +
            "7\u0002F\u0001" +
            "8\u0002T\u0001" +
            "9\u0002F\u0001" +
            "10\u00022\u0001" +
            "14\u00022018-06-20T18:00:00.000Z\u0001" +
            "15\u00020\u0001" +
            "16\u00021\u0001" +
            "17\u00020\u0001" +
            "19\u00023\u0001";

    public static final String validAAPIMExchangeInfoResponseDelta = "AAPI/6/E/E_1/E/E_100003/E/E_190659/E/E_3969213/E/E_4088802/M/E_314610/MEI\u0002\u0002T\u0001" +
            "11\u00023\u0001" +
            "15\u00025\u0001";

    public static final String validAAPIMExchangeInfoResponseDeltaDelete = "AAPI/6/E/E_1/E/E_100003/E/E_190659/E/E_3969213/E/E_4088802/M/E_314610/MEI\u0002\u0002T\u0001" +
            "18\u0002\u0001";


    @Test
    public void parseAAPIMessageContainingValidMExchangeInfoFull() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPIMExchangeInfoResponseFull);
        MExchangeInfo market1 = MExchangeInfo.parse(parsedAAPIMessage);

        assertValidMExchangeInfoPartialFull(market1);
    }

    @Test
    public void parseAAPIMessageContainingValidMExchangeInfoPartial() throws APIException {

        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPIMExchangeInfoResponsePartial);
        MExchangeInfo market1 = MExchangeInfo.parse(parsedAAPIMessage);

        assertValidMExchangeInfoPartial(market1);
    }

    @Test
    public void applyDeltaWillApplyItCorrectly() throws APIException {
        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPIMExchangeInfoResponsePartial);
        MExchangeInfo mExchangeInfoValid = MExchangeInfo.parse(parsedAAPIMessage);

        parsedAAPIMessage = AAPIMessage.parseMessage(validAAPIMExchangeInfoResponseDelta);
        MExchangeInfo mExchangeInfoDelta = MExchangeInfo.parse(parsedAAPIMessage);

        mExchangeInfoValid.applyDelta(mExchangeInfoDelta);

        assertEquals(314610L, mExchangeInfoValid.getMarketId().getValue().longValue());
        assertEquals(3, mExchangeInfoValid.getMarketType().getValue().intValue());
        assertEquals(false, mExchangeInfoValid.getIsPlayMarket().getValue());
        assertEquals(true, mExchangeInfoValid.getCanBeInRunning().getValue());
        assertEquals(true, mExchangeInfoValid.getManagedWhenInRunning().getValue());
        assertEquals(true, mExchangeInfoValid.getIsVisibleAsTradingMarket().getValue());
        assertEquals(false, mExchangeInfoValid.getIsVisibleAsPricedMarket().getValue());
        assertEquals(true, mExchangeInfoValid.getIsEnabledForMultiples().getValue());
        assertEquals(false, mExchangeInfoValid.getIsCurrentlyInRunning().getValue());
        assertEquals(2, mExchangeInfoValid.getStatus().getValue().intValue());
        assertEquals(3, mExchangeInfoValid.getWithdrawAction().getValue().intValue());
        assertEquals(null, mExchangeInfoValid.getBalloutAction().getValue());
        assertEquals(null, mExchangeInfoValid.getCanBeDeadHeated().getValue());
        assertEquals("Wed Jun 20 19:00:00 BST 2018", mExchangeInfoValid.getStartTime().getValue().toString());
        assertEquals(5, mExchangeInfoValid.getDelayFactor().getValue().intValue());
        assertEquals(1, mExchangeInfoValid.getNumberOfWinningSelections().getValue().intValue());
        assertEquals(0, mExchangeInfoValid.getWithdrawalSequenceNumber().getValue().intValue());
        assertEquals(null, mExchangeInfoValid.getResultString().getValue());
        assertEquals(3, mExchangeInfoValid.getNumberOfSelections().getValue().intValue());
        assertEquals(null, mExchangeInfoValid.getPlacePayout().getValue());
        assertEquals(null, mExchangeInfoValid.getRedboxSPAvailable().getValue());
        assertEquals(null, mExchangeInfoValid.getbOGAvailable().getValue());
        assertEquals(null, mExchangeInfoValid.getNumberWinningPlaces().getValue());
        assertEquals(null, mExchangeInfoValid.getPlaceFraction().getValue());
    }

    @Test
    public void applyDeltaWillApplyItCorrectlyWithDelete() throws APIException {
        AAPIMessage parsedAAPIMessage = AAPIMessage.parseMessage(validAAPIMExchangeInfoResponseFull);
        MExchangeInfo mExchangeInfoValid = MExchangeInfo.parse(parsedAAPIMessage);

        parsedAAPIMessage = AAPIMessage.parseMessage(validAAPIMExchangeInfoResponseDeltaDelete);
        MExchangeInfo mExchangeInfoDelta = MExchangeInfo.parse(parsedAAPIMessage);

        mExchangeInfoValid.applyDelta(mExchangeInfoDelta);

        assertEquals(314610L, mExchangeInfoValid.getMarketId().getValue().longValue());
        assertEquals(3, mExchangeInfoValid.getMarketType().getValue().intValue());
        assertEquals(false, mExchangeInfoValid.getIsPlayMarket().getValue());
        assertEquals(true, mExchangeInfoValid.getCanBeInRunning().getValue());
        assertEquals(true, mExchangeInfoValid.getManagedWhenInRunning().getValue());
        assertEquals(true, mExchangeInfoValid.getIsVisibleAsTradingMarket().getValue());
        assertEquals(false, mExchangeInfoValid.getIsVisibleAsPricedMarket().getValue());
        assertEquals(true, mExchangeInfoValid.getIsEnabledForMultiples().getValue());
        assertEquals(false, mExchangeInfoValid.getIsCurrentlyInRunning().getValue());
        assertEquals(2, mExchangeInfoValid.getStatus().getValue().intValue());
        assertEquals(3, mExchangeInfoValid.getWithdrawAction().getValue().intValue());
        assertEquals(4, mExchangeInfoValid.getBalloutAction().getValue().intValue());
        assertEquals(true, mExchangeInfoValid.getCanBeDeadHeated().getValue());
        assertEquals("Wed Jun 20 19:00:00 BST 2018", mExchangeInfoValid.getStartTime().getValue().toString());
        assertEquals(0, mExchangeInfoValid.getDelayFactor().getValue().intValue());
        assertEquals(1, mExchangeInfoValid.getNumberOfWinningSelections().getValue().intValue());
        assertEquals(0, mExchangeInfoValid.getWithdrawalSequenceNumber().getValue().intValue());
        assertEquals("", mExchangeInfoValid.getResultString().getValue());//deleted property
        assertEquals(3, mExchangeInfoValid.getNumberOfSelections().getValue().intValue());
        assertEquals("25%", mExchangeInfoValid.getPlacePayout().getValue());
        assertEquals(true, mExchangeInfoValid.getRedboxSPAvailable().getValue());
        assertEquals(true, mExchangeInfoValid.getbOGAvailable().getValue());
        assertEquals(22, mExchangeInfoValid.getNumberWinningPlaces().getValue().intValue());
        assertEquals("fra", mExchangeInfoValid.getPlaceFraction().getValue());
    }

    public static void assertValidMExchangeInfoPartialFull(MExchangeInfo mExchangeInfo){
        assertEquals(314610L, mExchangeInfo.getMarketId().getValue().longValue());
        assertEquals(3, mExchangeInfo.getMarketType().getValue().intValue());
        assertEquals(false, mExchangeInfo.getIsPlayMarket().getValue());
        assertEquals(true, mExchangeInfo.getCanBeInRunning().getValue());
        assertEquals(true, mExchangeInfo.getManagedWhenInRunning().getValue());
        assertEquals(true, mExchangeInfo.getIsVisibleAsTradingMarket().getValue());
        assertEquals(false, mExchangeInfo.getIsVisibleAsPricedMarket().getValue());
        assertEquals(true, mExchangeInfo.getIsEnabledForMultiples().getValue());
        assertEquals(false, mExchangeInfo.getIsCurrentlyInRunning().getValue());
        assertEquals(2, mExchangeInfo.getStatus().getValue().intValue());
        assertEquals(3, mExchangeInfo.getWithdrawAction().getValue().intValue());
        assertEquals(4, mExchangeInfo.getBalloutAction().getValue().intValue());
        assertEquals(true, mExchangeInfo.getCanBeDeadHeated().getValue());
        assertEquals("Wed Jun 20 19:00:00 BST 2018", mExchangeInfo.getStartTime().getValue().toString());
        assertEquals(0, mExchangeInfo.getDelayFactor().getValue().intValue());
        assertEquals(1, mExchangeInfo.getNumberOfWinningSelections().getValue().intValue());
        assertEquals(0, mExchangeInfo.getWithdrawalSequenceNumber().getValue().intValue());
        assertEquals("1-3", mExchangeInfo.getResultString().getValue());
        assertEquals(3, mExchangeInfo.getNumberOfSelections().getValue().intValue());
        assertEquals("25%", mExchangeInfo.getPlacePayout().getValue());
        assertEquals(true, mExchangeInfo.getRedboxSPAvailable().getValue());
        assertEquals(true, mExchangeInfo.getbOGAvailable().getValue());
        assertEquals(22, mExchangeInfo.getNumberWinningPlaces().getValue().intValue());
        assertEquals("fra", mExchangeInfo.getPlaceFraction().getValue());

    }

    public static void assertValidMExchangeInfoPartial(MExchangeInfo mExchangeInfo){
        assertEquals(314610L, mExchangeInfo.getMarketId().getValue().longValue());
        assertEquals(3, mExchangeInfo.getMarketType().getValue().intValue());
        assertEquals(false, mExchangeInfo.getIsPlayMarket().getValue());
        assertEquals(true, mExchangeInfo.getCanBeInRunning().getValue());
        assertEquals(true, mExchangeInfo.getManagedWhenInRunning().getValue());
        assertEquals(true, mExchangeInfo.getIsVisibleAsTradingMarket().getValue());
        assertEquals(false, mExchangeInfo.getIsVisibleAsPricedMarket().getValue());
        assertEquals(true, mExchangeInfo.getIsEnabledForMultiples().getValue());
        assertEquals(false, mExchangeInfo.getIsCurrentlyInRunning().getValue());
        assertEquals(2, mExchangeInfo.getStatus().getValue().intValue());
        assertEquals(null, mExchangeInfo.getWithdrawAction().getValue());
        assertEquals(null, mExchangeInfo.getBalloutAction().getValue());
        assertEquals(null, mExchangeInfo.getCanBeDeadHeated().getValue());
        assertEquals("Wed Jun 20 19:00:00 BST 2018", mExchangeInfo.getStartTime().getValue().toString());
        assertEquals(0, mExchangeInfo.getDelayFactor().getValue().intValue());
        assertEquals(1, mExchangeInfo.getNumberOfWinningSelections().getValue().intValue());
        assertEquals(0, mExchangeInfo.getWithdrawalSequenceNumber().getValue().intValue());
        assertEquals(null, mExchangeInfo.getResultString().getValue());
        assertEquals(3, mExchangeInfo.getNumberOfSelections().getValue().intValue());
        assertEquals(null, mExchangeInfo.getPlacePayout().getValue());
        assertEquals(null, mExchangeInfo.getRedboxSPAvailable().getValue());
        assertEquals(null, mExchangeInfo.getbOGAvailable().getValue());
        assertEquals(null, mExchangeInfo.getNumberWinningPlaces().getValue());
        assertEquals(null, mExchangeInfo.getPlaceFraction().getValue());

    }



}
