package tech.harmless.simplescript.shared.stack;

import tech.harmless.simplescript.shared.data.EnumType;
import tech.harmless.simplescript.shared.data.TypedData;
import tech.harmless.simplescript.shared.instructions.Instruction;

import java.util.HashMap;
import java.util.Map;

//TODO Merge of method frame and scope frame?
public abstract class StackFrame {

    private final Map<String, TypedData> allocatedVars;

    //TODO This will be needs for a merge of the method and scope instructions.
    // public final boolean methodStackFrame

    public StackFrame() {
        allocatedVars = new HashMap<>();
    }

    public abstract boolean hasInstruction();

    public abstract Instruction nextInstruction();

    public void allocVar(String name, TypedData var) {
        assert !allocatedVars.containsKey(name);
        allocatedVars.put(name, var);
    }

    public TypedData getVar(String name) {
        return allocatedVars.get(name); // This is allowed to be null, since CompiledScript checks it.
    }
}
