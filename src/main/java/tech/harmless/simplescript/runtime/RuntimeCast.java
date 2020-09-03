package tech.harmless.simplescript.runtime;

import tech.harmless.simplescript.shared.data.EnumType;
import tech.harmless.simplescript.shared.data.TypedData;

public class RuntimeCast {
    //TODO This class should allow casting of typed data.

    public static TypedData cast(TypedData data, EnumType newType) {
        switch(data.getType()) {
            default -> { // Includes VOID
                assert false;
            }
            case CHAR -> {
            }
            case STRING -> {
            }
            case INT8 -> {
            }
            case INT32 -> {
            }
            case INT64 -> {
            }
            case FLOAT32 -> {
            }
            case FLOAT64 -> {
            }
            case BYTE -> {
            }
            case BOOLEAN -> {
            }
            case OBJECT -> {
            }
        }

        return null; //TODO Finish!
    }
}
