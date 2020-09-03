package tech.harmless.simplescript.shared.stack;

import tech.harmless.simplescript.shared.instructions.Instruction;
import tech.harmless.simplescript.shared.data.EnumType;

public class ScopeStackFrame extends StackFrame {

    private final StackFrame parent;

    public ScopeStackFrame(StackFrame parent) {
        /*
         * TODO This needs to change for scoped var dec.
         *
         * int x = {
         *    return 0;
         * };
         */
        super(EnumType.VOID);

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
