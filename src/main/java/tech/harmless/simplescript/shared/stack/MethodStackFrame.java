package tech.harmless.simplescript.shared.stack;

import tech.harmless.simplescript.shared.instructions.Instruction;
import tech.harmless.simplescript.shared.vars.EnumType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MethodStackFrame extends StackFrame {

    private final List<Instruction> instructions;

    private int currentPos;

    public MethodStackFrame(Instruction[] instructions, EnumType returnType) {
        super(returnType);

        this.instructions = new ArrayList<>();
        this.instructions.addAll(Arrays.asList(instructions));

        currentPos = 0;
    }

    public MethodStackFrame(MethodStackFrame sf) {
        this((Instruction[]) sf.instructions.toArray(), sf.returnType);
    }

    @Override
    public boolean hasInstruction() {
        return instructions.size() == currentPos;
    }

    @Override
    public Instruction nextInstruction() {
        Instruction in = instructions.get(currentPos);
        currentPos++;
        assert(in != null);

        return in;
    }
}
