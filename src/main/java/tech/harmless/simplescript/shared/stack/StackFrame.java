package tech.harmless.simplescript.shared.stack;

import tech.harmless.simplescript.shared.instructions.Instruction;
import tech.harmless.simplescript.shared.vars.AllocVar;
import tech.harmless.simplescript.shared.vars.EnumType;

import java.util.HashMap;

public abstract class StackFrame {

    public final EnumType returnType;

    private final HashMap<String, AllocVar> allocatedVars;

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
