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

    private boolean running;

    public SimpleRuntime(CompiledScript script) {
        this.script = script;

        running = false;
    }

    @SuppressWarnings("unchecked")
    public void run() {
        running = true;

        //TODO Data Cache.
        while(running && script.hasNexFrame()) {
            // Get the current stack frame to process.
            StackFrame cFrame = script.getCurrentFrame();
            //TODO One loop cache of data???
            //TODO Add registers that store data.
            while(cFrame != null && cFrame.hasInstruction()) {
                Instruction in = cFrame.nextInstruction();
                switch(in.getInstruction()) {
                    default -> {
                        assert (false) : "Unsupported instruction.";
                    }
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
                    case INVOKE_METHOD -> {
                        System.out.println("INVOKE_METHOD");

                        Tuple<String, Object[]> data = (Tuple<String, Object[]>) in.getData();
                        script.pushFrame(data.x); //TODO What to do with data???
                        cFrame = null;
                    }
                    case RETURN_METHOD -> {
                        System.out.println("RETURN_METHOD");

                        // A var called return should be allocated before the return.
                        //TODO Don't assume all methods are void.
                        assert (cFrame.returnType == EnumType.VOID);

                        //TODO Change return var to return cache/register.

                        // Check for return var
                        AllocVar var = script.getVar("return"); //TODO Do something with this???
                        script.discardCurrentFrame(); //TODO Account for bool.
                        cFrame = null;
                    }
                    case CREATE_SCOPE -> {
                        System.out.println("CREATE_SCOPE");

                        script.pushFrame(new ScopeStackFrame(cFrame));
                        cFrame = script.getCurrentFrame();
                    }
                    case END_SCOPE -> {
                        System.out.println("END_SCOPE");

                        //TODO This needs to change for scoped var dec.
                        assert (cFrame.returnType == EnumType.VOID);

                        script.discardCurrentFrame(); //TODO Account for bool.
                    }
                }
            }

            assert (cFrame == null); // If this is not null, not all instructions were processed in the frame.
        }

        running = false;
    }
}
