package gbe.demoaapi.app.AAPIMessage;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Pattern;

public class AAPIMessageParser implements IAAPIMessageParser {


    private AAPIMessage aapiMessage = null;

    private RecordNVP nextNVP = null;

    /*
     * Current parsing level (levels based on field name token depth)
     */
    private int level = 0; //parser level

    /*
     * Indicates the number of Ancestors(s) (RepeatingGroup(s)) pushed onto the marker stack.
     * Works in conjunction with the parsing level to indicate that the FieldOrdinal be taken from
     * the RepeatingGroup until the this value is zero. Zero indicates that the parser is at the leaf node.
     */
    private int pushedCount = 0;

    /*
     * Indicates the number of Ancestors(s) (RepeatingGroup(s)) popped off the marker stack.
     * Works in conjunction with the parsing level to indicate that the FieldOrdinal be taken from
     * the RepeatingGroup until the this value is zero. Zero indicates that the parser is at the root node.
     */
    private int poppedCount = 0;


    /*
     * Maintains the stack for pushing/popping Ancestors to/from
     */
    private Stack<Marker> markers = new Stack<AAPIMessageParser.Marker>();

    /*
     * Current Marker stack persisted in field name format
     */
    private String markersAsFieldName = null;

    /*
     * Signal endOfMarket and prevents double field read after break (see readNextRecord)
     */
    boolean endOfMarkerReached = false; //


    public AAPIMessageParser(AAPIMessage message) throws APIException {

        if ( null == message )
            throw new APIException("Can not initialise a AAPIMessageParser with a NULL AAPIMessage.");

        this.aapiMessage = message;

        if ( !readNextRecord() )
            throw new APIException("Can not initialise a AAPIMessageParser for a AAPIMessage with no records.");
    }


    /**
     * <b>Reads next record from TopicMessage
     *  If 'endOfMarkerReached' true then skips the read as lookahead has already read.
     *  If 'endOfMarkerReached' false then read record, if record has Ancestors (RepeatingGroup(s))
     *  then handle them as follows {@link #applyAncestors()}
     *
     * @return continueReading flag to control parsing loop condition. True if continue to read, false otherwise
     *
     * @throws APIException
     */
    @Override
    public synchronized boolean readNextRecord() throws APIException {

        boolean continueReading = true;

        if ( !endOfMarkerReached ) {
            if ( null == nextNVP )
                (this.new RecordNVP()).readNextRecord();
            else
                nextNVP.readNextRecord();

            continueReading = applyAncestors();

        } else {

            if ( --poppedCount == 0 )
                endOfMarkerReached = false;
            else
                continueReading = false;
        }


        return null == nextNVP ? false : continueReading;
    }

    /**
     * <b>Applies Ancestors to the {@link #markers marker stack} as follows ...</b>
     * <br>1. If current parsing level is greater than zero (parsing on branch) then determine
     * if the current record is related to the current stack members. If not, then <b><i>POP</i></b>
     * Ancestors off the stack until they are related or the parsing level is returned to the root (level 0).
     * <br>2. If current parsing level is less than the depth (number of RepeatingGroups) on current record
     * then <b><i>PUSH</i></b> Ancestors onto the stack until the parsing level and depth match.
     * <br>
     * <b>This method also increments the {@link #pushedCount pushed counter}</b>. This counter informs the
     * parser of the number of Ancestors pushed on the last read. Subsequent calls to {@link #nextMarket()}
     * will decrement this counter until zero. While greater than zero {@link #nextMarket()} will return the
     * RepeatingGroup {@link GBETopicMessageParser.Marker} details. On zero the parser will return the
     * FieldOrdinal information of the leaf node.
     *
     * @return continueReading flag to control parsing loop condition, false if any Ancestor was popped.
     */
    private boolean applyAncestors() {

        boolean continueReading = true;

        if ( null != nextNVP ) {

            //if end of marker then pop ancestor(s) back to common ancestor or level 0
            while ( level > 0 && !isRelated() ) {

                popAncestor();
                poppedCount++;
                continueReading = false;
            }

            int tokenDepth = nextNVP.getFieldNameTokens().getDepth();
            while ( level <  tokenDepth ) {

                pushAncestor();
                pushedCount++;
            }
        }

        return continueReading;
    }

    /**
     * <b>Returns the RepeatingGroup Iteration integer value for the specified level</br>
     *
     * @param specifiedLevel, specified parsing level
     * @return RepeatingGroup Iteration value
     */
    private int getIteration(int specifiedLevel) {

        return null == nextNVP ? 0 : nextNVP.getIteration(specifiedLevel);
    }

    /**
     * <b>Returns the FieldOrdinal integer value for the current parsing level</br>
     *
     * @return FieldOrdinal value
     */
    private int getFieldOrdinal() {

        int ordinal = 0;

        if ( null != nextNVP )
            ordinal = nextNVP.getFieldOrdinal(level);

        return ordinal;
    }

    /**
     * <b>Create {@link Marker} object, increment parsing level, push Marker onto stack and set the current
     * {@link #markersAsFieldName marker ancestral string} (used to determine if the current record is related
     * to the currently pushed Ancestors.
     * </b>
     */
    private void pushAncestor() {

        Marker marker = new Marker();
        marker.FieldOrdinal = getFieldOrdinal();
        marker.Iteration = getIteration(level);

        level++;
        markers.push(marker);
        markersAsFieldName = getMarkerAsFieldName();
    }

    /**
     * <b>Decrements parsing level, pops a Marker off stack and sets the current
     * {@link #markersAsFieldName marker ancestral string} (used to determine if the current record is related
     * to the currently pushed Ancestors).
     * </b>
     */
    private void popAncestor() {

        level--;
        markers.pop();
        markersAsFieldName = getMarkerAsFieldName();
    }

    /**
     * <b>Determines if the currently read Record is related to the Markers on the stack</br>
     * If not, then the {@link #endOfMarkerReached} flag is set to true, indicating that the next
     * call to {@link #readNextRecord()} should simply reset the {@link #endOfMarkerReached} flag to
     * false and return.
     *
     * @return isRelated; true if is related else false
     */
    private boolean isRelated() {

        boolean isRelated = false;

        if ( markers.size() > 0 ) {

            //verify nextNVP is a child of the markers
            isRelated = nextNVP.fieldNameTokens.toString().startsWith( markersAsFieldName, 0);

            //set endOfMarkerReached to avoid double read
            if ( !endOfMarkerReached && !isRelated )
                endOfMarkerReached = true;
        }

        return isRelated;
    }

    /**
     * <b>Gets Ancestral marker string for Ancestors currently held on the stack.</b>

     * @return ancestral marker string, a concatenation of RepeatingGroup(s)
     */
    private String getMarkerAsFieldName() {

        StringBuffer buffer = new StringBuffer();

        for (Marker element: markers) {
            buffer.append(element.toString());
        }

        return buffer.toString();
    }

    /**
     * <b>Moves parser navigation to next FieldOrdinal</b>
     * <br>
     * If {@link #pushedCount} greater than zero then parser returns the Marker
     * to the next RepeatingGroup to be processed. Else the current read record's
     * FieldOrdinal is returned (the leaf node field ordinal).
     *
     * @return marker object; FieldOrdinal value (and Iteration, only for RepeatingGroup(s))
     * of the next Field to be processed.
     */
    @Override
    public Marker moveNextOrdinal() {

        Marker marker = null;

        if ( null != nextNVP && pushedCount > 0 )
            marker = markers.get(level - pushedCount--);

        if ( null == marker ) {

            marker = new Marker();
            marker.FieldOrdinal = getFieldOrdinal();
        }

        return marker;
    }

    /**
     * <b>Get the FieldName of the current read Record</b>
     *
     * @return FieldName of current read Record
     */
    @Override
    public String getFieldName() {

        return null == nextNVP ? null : nextNVP.nameValuePair.getNameField();
    }

    /**
     * <b>Get the FieldValue of the current read Record</b>
     *
     * @return FieldValue of current read Record
     */
    @Override
    public String getFieldValue() {

        return null == nextNVP ? null : nextNVP.nameValuePair.getValueField();
    }

    @Override
    public boolean isLoadMessage() {

        return aapiMessage.isLoadMessage();
    }

    //---------------------------------------------------------------------------
    //---------------------------------------------------------------------------
    //---------------------------------------------------------------------------

    public static class Marker {

        public int FieldOrdinal = 0;
        public int Iteration = 0;

        @Override
        public String toString() {

            return String.format("%dV%d-", FieldOrdinal, Iteration);
        }
    }

    //---------------------------------------------------------------------------
    //---------------------------------------------------------------------------
    //---------------------------------------------------------------------------

    private static class FieldNameToken {

        private static final String pattern = "-";
        private static final Pattern p = Pattern.compile(pattern);

        private ArrayList<FieldNameTokenItem> fieldNameTokens = null;

        private FieldNameToken() {

            fieldNameTokens =  new ArrayList<FieldNameTokenItem>();
        }

        public static FieldNameToken parse(String fieldName) {

            FieldNameToken token = null;
            String[] tokens = p.split(fieldName);

            if ( 0 == tokens.length )
                return null;

            token = new FieldNameToken();
            if ( tokens.length > 1 ) {

                for (int i = 0; i < tokens.length - 1; i++) {
                    token.add(FieldNameTokenItem.parseRepeatingGroup(tokens[i]));
                }
            }

            token.add( new FieldNameTokenItem(Integer.parseInt(tokens[tokens.length - 1])));

            return token;
        }

        private void add(FieldNameTokenItem item) {

            fieldNameTokens.add(item);
        }

        public int getFieldOrdinal(int level) {

            return level >= 0 && fieldNameTokens.size() > level ? fieldNameTokens.get(level).FieldOrdinal : 0;
        }

        public int getDepth() {

            return fieldNameTokens.size() - 1;
        }

        public int getIteration(int level) {

            return level >= 0 && fieldNameTokens.size() > level ? fieldNameTokens.get(level).Iteration : 0;
        }


        public String toString() {
            StringBuffer buffer = new StringBuffer();

            if ( null != fieldNameTokens ) {
                for (FieldNameTokenItem element : fieldNameTokens) {
                    buffer.append(element.toString());
                }
            }

            return buffer.toString();
        }

        //---------------------------------------------------------------------------
        //---------------------------------------------------------------------------
        //---------------------------------------------------------------------------

        private static class FieldNameTokenItem {

            private static final String RegExPattern = "V";
            private static final Pattern RepeatingGroupPattern = Pattern.compile(RegExPattern);

            public int FieldOrdinal;
            public int Iteration;
            public boolean IsRepeatingGroup = false;

            private FieldNameTokenItem() {

            }

            FieldNameTokenItem(int fieldOrdinal) {

                this.FieldOrdinal = fieldOrdinal;
            }

            public static FieldNameTokenItem parseRepeatingGroup(String repeatingGroup) {

                FieldNameTokenItem item = null;
                String[] tokens = RepeatingGroupPattern.split(repeatingGroup);

                if ( 2 == tokens.length ) {

                    item = new FieldNameTokenItem();
                    item.FieldOrdinal = Integer.parseInt(tokens[0]);
                    item.Iteration = Integer.parseInt(tokens[1]);
                    item.IsRepeatingGroup = true;
                }

                return item;
            }

            @Override
            public String toString() {
                StringBuffer buffer = new StringBuffer();
                buffer.append(FieldOrdinal);

                if ( IsRepeatingGroup )
                    buffer.append(String.format("V%d-", Iteration));
                return buffer.toString();
            }

        }
    }

    //---------------------------------------------------------------------------
    //---------------------------------------------------------------------------
    //---------------------------------------------------------------------------

    private class RecordNVP {

        private NameValuePair nameValuePair;
        private FieldNameToken  fieldNameTokens;

        private RecordNVP(){
        }
        public RecordNVP(NameValuePair nameValuePair){

            this.nameValuePair = nameValuePair;
            this.setFieldNameTokens(FieldNameToken.parse(nameValuePair.getNameField()));
        }

        private void readNextRecord() throws APIException{

            if ( aapiMessage.hasRemaining() ) {

                NameValuePair recordRead = aapiMessage.nextRecord();


                nextNVP = new RecordNVP(recordRead);
            } else {

                nextNVP = null;
            }
        }

        public int getFieldOrdinal(int level) {

            return null == fieldNameTokens ? 0 : fieldNameTokens.getFieldOrdinal(level);
        }

        @SuppressWarnings("unused")
        public int getDepth() {

            return null == fieldNameTokens ? 0 : fieldNameTokens.getDepth();
        }

        public int getIteration(int level) {

            return null == fieldNameTokens ? 0 : fieldNameTokens.getIteration(level);
        }

        public FieldNameToken getFieldNameTokens() {
            return fieldNameTokens;
        }

        private void setFieldNameTokens(FieldNameToken fieldNameTokens) {
            this.fieldNameTokens = fieldNameTokens;
        }

        @Override
        public String toString() {
            return String.format("RecordNVP: name [%s], value [%s]", this.nameValuePair.getNameField(), this.nameValuePair.getValueField());
        }
    }


}

