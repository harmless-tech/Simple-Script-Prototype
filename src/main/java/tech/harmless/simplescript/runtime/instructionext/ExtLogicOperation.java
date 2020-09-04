package tech.harmless.simplescript.runtime.instructionext;

import tech.harmless.simplescript.shared.data.EnumType;
import tech.harmless.simplescript.shared.data.TypedData;
import tech.harmless.simplescript.shared.types.EnumLogicOperation;

public class ExtLogicOperation {

    //TODO Implement!
    public static TypedData logic(EnumLogicOperation op, TypedData a, TypedData b) {
        assert a.getType() == EnumType.BOOLEAN;
        assert b.getType() == EnumType.BOOLEAN;
        // Should compare two values and then return a TypedData of boolean.
        return null;
    }
}
