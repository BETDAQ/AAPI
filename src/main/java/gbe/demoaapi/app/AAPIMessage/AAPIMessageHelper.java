package gbe.demoaapi.app.AAPIMessage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;

public class AAPIMessageHelper {

    public static final String DEFAULT_TIMEZONE = "UTC";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final TimeZone UTC_TIMEZONE = TimeZone.getTimeZone(DEFAULT_TIMEZONE);

    public static final String DEFAULT_DECIMAL_FORMAT = "0.00";
    public static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.FLOOR;

    public static final String LIST_DELIMITER = "~";

    private static Pattern booleanPattern = Pattern.compile("[fFtT]");

    public static boolean parseBoolean(String value) throws APIException{

        if ( null == value || !booleanPattern.matcher(value).matches() )
            throw new APIException(String.format("Unable to parse value [%s] as primitive boolean.", value));

        return value.equalsIgnoreCase("T");
    }

    public static Boolean parseBooleanObject(String value) throws APIException{

        if(value == null)
            return null;

        return Boolean.valueOf( parseBoolean(value) );
    }

    public static java.util.Date parseDate(String value) throws APIException {

        return parseDate(getGBEDateFormatter(), value);
    }

    public static java.util.Date parseDate(DateFormat dateFormatter, String value) throws APIException{

        if ( null == value )
            return null;

        try{

            return dateFormatter.parse(value);
        } catch(ParseException pEx){
            throw new APIException(String.format("Unable to parse value [%s] as Date.", value), pEx);
        }
    }

    public static long parseLong(String value) throws APIException {

        try {
            return Long.parseLong(value);
        } catch (NumberFormatException nfe) {
            throw new APIException(String.format("Unable to parse value [%s] as primitive long.", value), nfe);
        }
    }

    public static Long parseLongObject(String value) throws APIException {

        if ( null == value )
            return null;

        return Long.valueOf(parseLong(value));
    }

    public static BigDecimal parseBigDecimal(String value) throws APIException {

        if ( null == value )
            return null;

        try {
            return new BigDecimal(value);
        } catch (NumberFormatException nfe) {
            throw new APIException(String.format("Unable to parse value [%s] as BigDecimal.", value), nfe);

        }
    }

    public static int parseInt(String value) throws APIException {

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException nfe) {
            throw new APIException(String.format("Unable to parse value [%s] as primitive int.", value), nfe);
        }
    }

    public static Integer parseInteger(String value) throws APIException {

        if ( null == value )
            return null;

        return Integer.valueOf( parseInt(value) );
    }

    public static UUID parseUUID(String value) throws APIException {

        if ( null == value )
            return null;

        try {
            return UUID.fromString(value);
        } catch (IllegalArgumentException iae) {
            throw new APIException(String.format("Unable to parse value [%s] as UUID.", value), iae);
        }
    }

    public static double parseDouble(String value) throws APIException {

        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException nfe) {
            throw new APIException(String.format("Unable to parse value [%s] as primitive double.", value), nfe);
        }
    }

    public static Double parseDoubleObject(String value) throws APIException {

        if ( null == value )
            return null;

        return Double.valueOf( parseDouble(value) );
    }

    public static int[] parseIntArray(String value) throws APIException {

        if ( null == value || value.length() == 0)
            return null;

        try {
            String[] strArr = value.split(LIST_DELIMITER);
            int[] ret = new int[strArr.length];
            for(int i=0;i<strArr.length;i++){
                ret[i] = Integer.parseInt(strArr[i]);
            }
            return ret;

        } catch (NumberFormatException nfe) {
            throw new APIException(String.format("Unable to parse value [%s] to int array.", value), nfe);

        }
    }

    public static long[] parseLongArray(String value) throws APIException {

        if ( null == value || value.length() == 0)
            return null;

        try {
            String[] strArr = value.split(LIST_DELIMITER);
            long[] ret = new long[strArr.length];
            for(int i=0;i<strArr.length;i++){
                ret[i] = Long.parseLong(strArr[i]);
            }
            return ret;

        } catch (NumberFormatException nfe) {
            throw new APIException(String.format("Unable to parse value [%s] to long array.", value), nfe);

        }
    }

    public static String[] parseStringArray(String value) throws APIException {

        if ( null == value || value.length() == 0)
            return null;

        try {
            String[] strArr = value.split(LIST_DELIMITER);

            return strArr;

        } catch (NumberFormatException nfe) {
            throw new APIException(String.format("Unable to parse value [%s] to String array.", value), nfe);

        }
    }

    public static String swapString(String oldValue, String newValue ) {

        if ( null == oldValue  || !oldValue.equals(newValue) )
            return newValue;

        return oldValue;
    }

    public static UUID swapUUID(UUID oldValue, UUID newValue ) {

        if ( null == oldValue  || !oldValue.equals(newValue) )
            return newValue;

        return oldValue;
    }

    public static Date swapDate(Date oldValue, Date newValue ) {

        if ( null == oldValue  || !oldValue.equals(newValue) )
            return newValue;

        return oldValue;
    }

    public static int swapInt(int oldValue, int newValue) {

        if ( oldValue != newValue )
            return newValue;

        return oldValue;
    }

    public static long swapLong(long oldValue, long newValue) {

        if ( oldValue != newValue )
            return newValue;

        return oldValue;
    }

    public static boolean swapBoolean(boolean oldValue, boolean newValue) {

        if ( oldValue != newValue )
            return newValue;

        return oldValue;
    }



    public static String extractTokenFromTopicNameReverse(String topicName, int tokenIndexFromEnd) throws APIException {

        try {
            String[] tokens = topicName.split("/");
            return tokens[tokens.length- (tokenIndexFromEnd+1)];


        } catch (Exception e) {
            throw new APIException(String.format("Unable to extract String token (tokenIndexFromEnd:[%d])from Topic Name [%s].", tokenIndexFromEnd, topicName), e);
        }


    }

    public static SimpleDateFormat getGBEDateFormatter() {

        return threadDateFormatter.get();
    }

    private static ThreadLocal<SimpleDateFormat> threadDateFormatter = new ThreadLocal<SimpleDateFormat>() {
        protected synchronized SimpleDateFormat initialValue() {
            return getGBEDateFormatter(DEFAULT_DATE_FORMAT, UTC_TIMEZONE);
        }
    };

    public static SimpleDateFormat getGBEDateFormatter(String dateFormat, TimeZone timeZone) {

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setTimeZone(timeZone);
        return sdf;
    }

    public static DecimalFormat getGBEDecimalFormatter() {

        return threadDecimalFormatter.get();
    }

    private static ThreadLocal<DecimalFormat> threadDecimalFormatter = new ThreadLocal<DecimalFormat>() {
        protected synchronized DecimalFormat initialValue() {
            return getGBEDecimalFormatter(DEFAULT_DECIMAL_FORMAT, DEFAULT_ROUNDING_MODE);
        }
    };

    public static DecimalFormat getGBEDecimalFormatter(String decimalFormat, RoundingMode roundingMode ) {

        DecimalFormat moneyFormatter = new DecimalFormat(decimalFormat);
        moneyFormatter.setRoundingMode(roundingMode);

        return moneyFormatter;
    }

}
