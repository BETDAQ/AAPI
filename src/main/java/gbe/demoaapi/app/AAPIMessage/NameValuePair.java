package gbe.demoaapi.app.AAPIMessage;

public class NameValuePair {
    private String nameField;
    private String valueField;

    public NameValuePair(String nameField, String valueField) {
        this.nameField = nameField;
        this.valueField = valueField;
    }

    public String getNameField() {
        return nameField;
    }

    public String getValueField() {
        return valueField;
    }
}
