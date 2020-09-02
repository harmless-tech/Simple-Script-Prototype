package tech.harmless.simplescript.shared.instructions;

public enum EnumInstruction {
    ALLOC_VAR, // new Tuple<String, EnumType>(NAME, TYPE); EX: ("hello", EnumType.INT32)
    SET_VAR, // new Tuple<String, Object>(NAME, DATA); EX: ("hello", 0)
    GET_VAR, // new String(NAME) EX: "hello"
    CALL_METHOD, // new Tuple<String, Object[]>(NAME, DATA[]); EX: ("hello", new int[] { 0, 0, 0 })
    RETURN_METHOD, // NULL
    NEW_SCOPE, // NULL
    END_SCOPE // NULL
}
