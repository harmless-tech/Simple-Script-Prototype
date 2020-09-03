package tech.harmless.simplescript.shared.data;

public final class RegisterType {
    // Registers for comparing.
    public static final int COMPARE_ONE = 0;
    public static final int COMPARE_TWO = 1;
    public static final int JUMP = 2;

    // Registers for operations.
    public static final int OPERATION_ONE = 3;
    public static final int OPERATION_TWO = 4;

    // Register for returning the results of methods, comparing, and operations.
    public static final int RETURN = 5;
    public static final int EXIT_CODE = 6;

}
