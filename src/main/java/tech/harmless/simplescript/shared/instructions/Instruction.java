package tech.harmless.simplescript.shared.instructions;

public class Instruction {

    private final EnumInstruction instruction;
    private final Object data;

    public Instruction(EnumInstruction instruction, Object data) {
        this.instruction = instruction;
        this.data = data;
    }

    public EnumInstruction getInstruction() {
        return instruction;
    }

    public Object getData() {
        return data;
    }
}
