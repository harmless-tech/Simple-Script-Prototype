package tech.harmless.simplescript.shared;

import tech.harmless.simplescript.shared.instructions.CompiledInstruction;
import tech.harmless.simplescript.shared.vars.AllocVar;
import tech.harmless.simplescript.shared.vars.EnumType;

import java.util.HashMap;
import java.util.Stack;

public class StackFrame {

    public final EnumType returnType;

    private final Stack<CompiledInstruction> instructions;
    private final HashMap<String, AllocVar> allocatedVars;

    public StackFrame(Stack<CompiledInstruction> instructions, EnumType returnType) {
        this.instructions = instructions;
        allocatedVars = new HashMap<>();

        this.returnType = returnType;
    }

    public CompiledInstruction nextInstruction() {
        CompiledInstruction in = instructions.pop();

        assert(in != null);
        return in;
    }

    public boolean hasInstructions() {
        return instructions.empty();
    }

    public void allocVar(String name, AllocVar var) {
        assert(!allocatedVars.containsKey(name));
        allocatedVars.put(name, var);
    }

    public AllocVar getVar(String name) {
        AllocVar var = allocatedVars.get(name);

        assert(var != null);
        return var;
    }

    public boolean canReturn() {
        return returnType != EnumType.VOID;
    }
}
