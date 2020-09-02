package tech.harmless.simplescript.shared.vars;

public enum EnumType {
    CHAR,
    STRING,

    INT8,
    //INT16, TODO Not a standard java type have to add later.
    INT32, // Default (INT)
    INT64,

    FLOAT32,
    FLOAT64, // Default (FLOAT)

    BYTE,
    BOOLEAN,

    OBJECT,
    VOID // Only should be used for methods.
}
