package tech.harmless.simplescript.shared.instructions;

public class CompiledInstruction {

    private final EnumInstruction instruction;
    private final Object data;

    public CompiledInstruction(EnumInstruction instruction, Object data) {
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
