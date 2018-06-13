package gbe.demoaapi.app.TopicHierarchy;

public class MessageAttribute<T> {
    private boolean isSpecified;
    private T value;

    public MessageAttribute() {
        isSpecified = false;
    }

    public void setValue(T value) {
        this.isSpecified = true;
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public boolean isSpecified() {
        return isSpecified;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MessageAttribute{");
        sb.append("isSpecified=").append(isSpecified);
        sb.append(", value=").append(value);
        sb.append('}');
        return sb.toString();
    }
}
