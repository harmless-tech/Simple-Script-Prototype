package tech.harmless.simplescript.runtime.instructionext;

import tech.harmless.simplescript.shared.data.TypedData;
import tech.harmless.simplescript.shared.types.EnumArithmeticOperation;

//TODO Maybe allow for implicit casting in the compiler.
public class ExtArithmeticOperation {

    //TODO Implement!
    public static TypedData arithmetic(EnumArithmeticOperation op, TypedData a, TypedData b) {
        assert a.getType() == b.getType();
        // Can only use a "+" on a string.
        return null;
    }
}
