package tech.harmless.simplescript.shared.instructions;

public enum EnumInstruction {
    ALLOC_VAR, // new Triplet<String, EnumType, Object>(NAME, TYPE, DEFAULT or DATA); EX: ("hello", EnumType.INT32, 0)
    SET_VAR, // new Tuple<String, Object>(NAME, DATA); EX: ("hello", 0)
    SET_VAR_REG, // new String(NAME); /* Sets the var using the return register. */
    GET_VAR, // new String(NAME) EX: "hello" TODO Is this needed?
    
    CREATE_FRAME, // new Triplet<Boolean, String, Object[]>(IS_METHOD, METHOD_NAME, METHOD_ARGS);
    DISCARD_FRAME, // NULL

    LOAD_REG, // new Tuple<RegisterType, Object>(REG_TYPE, DATA); /* Loads data into a register. */
    LOAD_REG_VAR, // new Tuple<RegisterType, String>(REG_TYPE, VAR_NAME); /* Loads a var into a register. */
    SHIFT_REG, // new Tuple<RegisterType, RegisterType>(ORIGINAL_REG, NEW_REG); /* Shifts data in one register into another register. */
    OPERATION, // NULL /* Does a EnumOperation and puts the data into the return register. */
    COMPARE, // NULL /* Does a EnumCompare and puts the data into the return register. */

    JUMP, // new Integer(JUMP_POINT); /* Jumps to a point in the instructions. */ (Checks the jump register. Loops use this.)
    RETURN_JUMP, // NULL /* Returns to 1 + the instruction point before the jump. */

    CALL_SYSTEM_LIB,
    END // new Integer(EXIT_CODE);

    //TODO Figure out vars and operations.
}
