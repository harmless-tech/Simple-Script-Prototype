package tech.harmless.simplescript.shared.stack;

import tech.harmless.simplescript.shared.instructions.Instruction;
import tech.harmless.simplescript.shared.data.EnumType;

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

    @SuppressWarnings("CopyConstructorMissesField")
    public MethodStackFrame(MethodStackFrame sf) {
        //TODO Is this 0 array allowed??
        this(sf.instructions.toArray(new Instruction[0]), sf.returnType);
    }

    @Override
    public boolean hasInstruction() {
        return instructions.size() > currentPos;
    }

    @Override
    public Instruction nextInstruction() {
        Instruction in = instructions.get(currentPos);
        currentPos++;
        assert in != null;

        return in;
    }
}
