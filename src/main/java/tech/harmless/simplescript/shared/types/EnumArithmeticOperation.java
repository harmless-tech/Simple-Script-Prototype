package tech.harmless.simplescript.shared.types;

// Assignment operations will be compiled down to these operations.
public enum EnumArithmeticOperation {
    MODULUS,
    MULTIPLICATION,
    DIVISION,
    PLUS,
    MINUS // Also does -VAR. (0-VAR)
}
