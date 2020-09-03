package tech.harmless.simplescript.shared.data;

public enum EnumType {
    CHAR, // Default: '0'
    STRING, // Default: ""

    INT8, // Default: 0
    //INT16, Default: 0, TODO Not a standard java type. Custom implementation needed.
    INT32, // Default: 0, Alias: int
    INT64, // Default: 0

    FLOAT32, // Default: 0.0
    FLOAT64, // Default: 0, Alias: float

    BYTE, // Default: 0
    BOOLEAN, // Default: false

    OBJECT, // Default: 0
    VOID // Default: "VOID", Only should be used for methods.

    // TODO Possible do TUPLE type. data = new Tuple<TypedData, TypedData>();
    // TODO Possible do ARRAY type. data = new Tuple<EnumType, Object[]>(); Possible to have an array of an array. [][]??
}
