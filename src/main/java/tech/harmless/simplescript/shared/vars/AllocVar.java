package tech.harmless.simplescript.shared.vars;

public class AllocVar {

    private final EnumType type;
    private Object value;

    public AllocVar(EnumType type, Object value) {
        this.type = type;
        this.value = value;
    }

    public EnumType getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }
}
