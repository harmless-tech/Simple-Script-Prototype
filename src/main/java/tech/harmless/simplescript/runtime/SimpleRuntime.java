package tech.harmless.simplescript.runtime;

import tech.harmless.simplescript.shared.CompiledScript;
import tech.harmless.simplescript.shared.stack.StackFrame;

/*
 * return info is through the return var, since it is special.
 */
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
            //TODO Fix

            // Get the current stack frame to process.
            StackFrame cFrame = script.getCurrentFrame();

            /*StackFrame cFrame = script.getCurrentFrame();
            if(cFrame != null) {
                while(cFrame.hasInstructions()) {
                    CompiledInstruction in = cFrame.nextInstruction();
                    switch(in.getInstruction()) {
                        default -> {
                            assert(false);
                        }
                        case ALLOC_VAR -> {
                            *//*CompiledVar var = createVar(in.getInstruction());
                            cFrame.allocVar();*//*
                        }
                        case CALL_METHOD -> {
                            script.
                        }
                        case NEW_SCOPE -> {
                            script.nextFrame();
                            cFrame = script.getCurrentFrame();
                        }
                        case END_SCOPE -> {
                        }
                    }
                }
                }*/
        }

        running = false;
    }
}
