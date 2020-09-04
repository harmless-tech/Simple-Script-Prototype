package tech.harmless.simplescript.runtime.instructionext;

import tech.harmless.simplescript.shared.data.TypedData;
import tech.harmless.simplescript.shared.types.EnumRelationalOperation;

public class ExtRelationalOperation {

    //TODO Implement!
    public static TypedData relational(EnumRelationalOperation op, TypedData a, TypedData b) {
        assert a.getType() == b.getType();
        // Should compare two values and then return a TypedData of boolean.
        return null;
    }
}
