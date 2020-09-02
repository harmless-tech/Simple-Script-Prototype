package tech.harmless.simplescript.shared.stack;

import tech.harmless.simplescript.shared.instructions.Instruction;
import tech.harmless.simplescript.shared.vars.EnumType;

public class ScopeStackFrame extends StackFrame {

    private final StackFrame parent;

    public ScopeStackFrame(StackFrame parent) {
        super(EnumType.VOID); //TODO This needs to change for scoped var dec.

        this.parent = parent;
    }

    @Override
    public boolean hasInstruction() {
        return parent.hasInstruction();
    }

    @Override
    public Instruction nextInstruction() {
        return parent.nextInstruction();
    }
}
