package tech.harmless.simplescript.shared.instructions;

//TODO NULL instructions can share a static instruction class. (To reduce memory.)
public enum EnumInstruction {
    ALLOC_VAR, // new Triplet<String, EnumType, Object>(NAME, TYPE, DEFAULT or DATA); EX: ("hello", EnumType.INT32, 0)
    SET_VAR, // new Tuple<String, Object>(NAME, DATA); EX: ("hello", 0)
    SET_VAR_REG, // new String(NAME); /* Sets the var using the return register. */
    //GET_VAR, // new String(NAME) EX: "hello" /* This is technically set var reg */ TODO Useless?

    CREATE_FRAME, // new Triplet<Boolean, String, Object[]>(IS_METHOD, METHOD_NAME, METHOD_ARGS);
    DISCARD_FRAME, // NULL /* The return register should only be loaded if the frame returns a value. */

    LOAD_REG, // new Tuple<Integer (RegisterType), TypedData>(REG_TYPE, DATA); /* Loads data into a register. */
    LOAD_REG_VAR, // new Tuple<Integer (RegisterType), String>(REG_TYPE, VAR_NAME); /* Loads a var into a register. */
    TRANSFER_REG, // new Tuple<Integer (RegisterType), Integer (RegisterType)>(ORIGINAL_REG, NEW_REG); /* Shifts data in one register into another register. Used if an operation has multiple steps. */
    // CLEAR_REG /* Just don't put anything in the reg. */ TODO Useless?

    //TODO
    ARITHMETIC_OPERATION, // EnumArithmeticOperation /* Does a EnumArithmeticOperation and puts the data into the return register. */
    RELATIONAL_OPERATION, // EnumRelationalOperation /* Does a EnumRelationalOperation and puts the data into the return register. */
    LOGIC_OPERATION, // EnumLogicOperation /* Does a EnumLogicOperation and puts the data into the return register. */

    PUSH_JUMP, // new Integer(JUMP_POINT); /* Jumps to a point in the instructions. */ (Checks the return register)
    POP_JUMP, // NULL /* Returns to 1 + the instruction point before the jump. */

    CAST, // EnumType /* Casts the CAST register to the desired type and puts it into the RETURN register. */
    CALL_NATIVE, // new Tuple<String, Tuple<Boolean, Object>[]>(NAME, (IS_VAR, NAME_OR_DATA[])); TODO Use LOAD_REG_VAR?
	//DUMP, // NULL /* Dumps all the instructions, variables, and the current instructions position to a file. (DATE.simpledump) */ TODO Due to the data safety measures the data can not be collected by the runtime easily.
    EXIT // NULL /* An TypedData of (INT32) should be loaded into the EXIT register. */
}
