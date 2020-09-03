package tech.harmless.simplescript.shared.data;

public class TypedData {

    public static final TypedData VOID = new TypedData(EnumType.VOID, null);

    private final EnumType type; //TODO Is this needed?
    private Object value;

    public TypedData(EnumType type, Object value) {
        this.type = type;
        this.value = value;
    }

    public void setValue(Object value) {
        assert value != null;

        this.value = value;
    }

    public EnumType getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }
}
