package tech.harmless.simplescript.shared.stack;

import tech.harmless.simplescript.shared.instructions.Instruction;

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
        super();

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

    @Override
    public void jumpInstructionPos(int pos) {
        parent.jumpInstructionPos(pos);
    }
}
