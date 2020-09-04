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
                // When converting strings to int count the - signs in the front then convert the rest to an array of
                // chars. Take each char and subtract it by an offset, then add it with a multiple of 10.
            }
            case INT8 -> {
                // Float -> Int: Drop the decimal or round it? Most languages drop it.
            }
            /*case INT16 -> {

            }*/
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
