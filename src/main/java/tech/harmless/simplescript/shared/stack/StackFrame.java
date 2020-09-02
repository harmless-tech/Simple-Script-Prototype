package tech.harmless.simplescript.shared.stack;

import tech.harmless.simplescript.shared.instructions.Instruction;
import tech.harmless.simplescript.shared.vars.AllocVar;
import tech.harmless.simplescript.shared.vars.EnumType;

import java.util.HashMap;
import java.util.Map;

public abstract class StackFrame {

    public final EnumType returnType;

    private final Map<String, AllocVar> allocatedVars;

    // public final boolean method

    public StackFrame(EnumType returnType) {
        allocatedVars = new HashMap<>();

        this.returnType = returnType;
    }

    public abstract boolean hasInstruction();

    public abstract Instruction nextInstruction();

    public void allocVar(String name, AllocVar var) {
        assert(!allocatedVars.containsKey(name));
        allocatedVars.put(name, var);
    }

    public AllocVar getVar(String name) {
        return allocatedVars.get(name); // This is allowed to be null.
    }
}
