package tech.harmless.simplescript.shared.instructions;

public enum EnumInstruction {
    ALLOC_VAR, // new Triplet<String, EnumType, Object>(NAME, TYPE, DEFAULT); EX: ("hello", EnumType.INT32, 0)
    SET_VAR, // new Tuple<String, Object>(NAME, DATA); EX: ("hello", 0)
    GET_VAR, // new String(NAME) EX: "hello"
    INVOKE_METHOD, // new Tuple<String, Object[]>(NAME, DATA[]); EX: ("Entry.hello", new int[] { 0, 0, 0 })
    RETURN_METHOD, // NULL
    CREATE_SCOPE, // NULL
    END_SCOPE, // NULL

    CALL_SYSTEM
}
