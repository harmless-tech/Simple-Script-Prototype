package tech.harmless.simplescript.runtime;

import tech.harmless.simplescript.shared.CompiledScript;
import tech.harmless.simplescript.shared.StackFrame;
import tech.harmless.simplescript.shared.instructions.CompiledInstruction;
import tech.harmless.simplescript.shared.instructions.EnumInstructionDataType;

public class SimpleRuntime {

    private final CompiledScript script;

    private boolean running;

    public SimpleRuntime(CompiledScript script) {
        this.script = script;

        running = false;
    }

    public void run() {
        running = true;

        while(running) {
            StackFrame cFrame = script.getCurrentFrame();
            if(cFrame != null) {
                while(cFrame.hasInstructions()) {
                    CompiledInstruction in = cFrame.nextInstruction();
                    switch(in.getInstruction()) {
                        default -> {
                            assert(false);
                        }
                        case ALLOC_VAR -> {
                            /*CompiledVar var = createVar(in.getInstruction());
                            cFrame.allocVar();*/
                        }
                        case CALL_METHOD -> {
                            script.
                        }
                        case NEW_SCOPE -> {
                            script.nextFrame();
                            cFrame = script.getCurrentFrame();
                        }
                        case END_SCOPE -> {
                            //TODO
                        }
                    }
                }

                //TODO
            }

            running = false;
        }
    }

    private StackFrame createFrame(EnumInstructionDataType type, Object data) {

    }
}
