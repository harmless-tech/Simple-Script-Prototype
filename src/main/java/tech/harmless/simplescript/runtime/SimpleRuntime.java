package tech.harmless.simplescript.runtime;

import tech.harmless.simplescript.shared.CompiledScript;
import tech.harmless.simplescript.shared.instructions.Instruction;
import tech.harmless.simplescript.shared.stack.ScopeStackFrame;
import tech.harmless.simplescript.shared.stack.StackFrame;
import tech.harmless.simplescript.shared.vars.AllocVar;
import tech.harmless.simplescript.shared.vars.EnumType;
import tech.harmless.simplescript.utils.Triplet;
import tech.harmless.simplescript.utils.Tuple;

public class SimpleRuntime {

    private final CompiledScript script;
    private final Register register;

    private boolean running;

    public SimpleRuntime(CompiledScript script) {
        this.script = script;

        register = new Register();
        running = false;
    }

    @SuppressWarnings("unchecked")
    public void run() {
        running = true;

        while(running && script.hasNexFrame()) {
            // Get the current stack frame to process.
            StackFrame cFrame = script.getCurrentFrame();

            while(cFrame != null && cFrame.hasInstruction()) {
                Instruction in = cFrame.nextInstruction();

                //TODO Add more instructions.
                switch(in.getInstruction()) {
                    default -> {
                        assert (false) : "Unsupported instruction.";
                    }

                    // Var Instructions
                    case ALLOC_VAR -> {
                        System.out.println("ALLOC_VAR");

                        Triplet<String, EnumType, Object> data = (Triplet<String, EnumType, Object>) in.getData();
                        cFrame.allocVar(data.x, new AllocVar(data.y, data.z));
                    }
                    case SET_VAR -> {
                        System.out.println("SET_VAR");

                        Tuple<String, Object> data = (Tuple<String, Object>) in.getData(); //TODO Nicer way to cast?
                        script.setVar(data.x, data.y);
                    }
                    case GET_VAR -> {
                        System.out.println("GET_VAR");

                        String data = (String) in.getData();
                        script.getVar(data); //TODO Where to put this???
                    }

                    // Frame Instructions
                    case CREATE_FRAME -> {
                        System.out.println("CREATE_FRAME");

                        Triplet<Boolean, String, Object[]> data = (Triplet<Boolean, String, Object[]>) in.getData();
                        if(data.x) {
                            //TODO Add entry args into stack frame vars.
                            script.pushFrame(data.y);
                        }
                        else
                            script.pushFrame(new ScopeStackFrame(cFrame));

                        cFrame = null;
                    }
                    case DISCARD_FRAME -> {
                        System.out.println("DISCARD_FRAME");

                        // The return register should have already been filled with data.
                        //TODO Manually check return register.

                        script.discardCurrentFrame();
                        cFrame = null;
                    }

                    // Register Instructions
                }
            }

            assert (cFrame == null); // If this is not null, not all instructions were processed in the frame.
        }

        running = false;
    }
}
