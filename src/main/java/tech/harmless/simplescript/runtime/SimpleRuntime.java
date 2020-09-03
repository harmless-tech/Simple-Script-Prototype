package tech.harmless.simplescript.runtime;

import tech.harmless.simplescript.runtime.memory.Register;
import tech.harmless.simplescript.runtime.systemlib.NativeLib;
import tech.harmless.simplescript.shared.CompiledScript;
import tech.harmless.simplescript.shared.data.EnumType;
import tech.harmless.simplescript.shared.data.TypedData;
import tech.harmless.simplescript.shared.instructions.Instruction;
import tech.harmless.simplescript.shared.stack.ScopeStackFrame;
import tech.harmless.simplescript.shared.stack.StackFrame;
import tech.harmless.simplescript.shared.types.RegisterType;
import tech.harmless.simplescript.shared.utils.Triplet;
import tech.harmless.simplescript.shared.utils.Tuple;

// At no time should there be instructions added during the runtime.
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
                //TODO None of these instructions account for the data types not being matched.
                switch(in.getInstruction()) {
                    default -> {
                        assert (false) : "Unsupported instruction.";
                    }

                    // Var Instructions
                    case ALLOC_VAR -> {
                        System.out.println("ALLOC_VAR");

                        Triplet<String, EnumType, Object> data = (Triplet<String, EnumType, Object>) in.getData();
                        cFrame.allocVar(data.x, new TypedData(data.y, data.z));
                    }
                    case SET_VAR -> {
                        System.out.println("SET_VAR");

                        Tuple<String, Object> data = (Tuple<String, Object>) in.getData();
                        script.setVar(data.x, data.y);
                    }
                    case SET_VAR_REG -> {
                        System.out.println("SET_VAR_REG");

                        String name = (String) in.getData();
                        TypedData data = register.getReg(RegisterType.RETURN);

                        script.setVar(name, data.getValue());
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
                        script.discardCurrentFrame();
                        cFrame = null;
                    }

                    // Register Instructions
                    case LOAD_REG -> {
                        System.out.println("LOAD_REG");

                        Tuple<Integer, TypedData> data = (Tuple<Integer, TypedData>) in.getData();
                        register.setReg(data.x, data.y);
                    }
                    case LOAD_REG_VAR -> {
                        System.out.println("LOAD_REG_VAR");

                        Tuple<Integer, String> data = (Tuple<Integer, String>) in.getData();
                        TypedData varData = script.getVar(data.y);

                        register.setReg(data.x, varData);
                    }
                    case TRANSFER_REG -> {
                        System.out.println("TRANSFER_REG");

                        Tuple<Integer, Integer> data = (Tuple<Integer, Integer>) in.getData();
                        register.setReg(data.y, register.getReg(data.x));
                    }

                    // Operation Instructions
                    //TODO Should edit the return register.
                    /*case ARITHMETIC_OPERATION -> {
                        assert false;
                        //TODO Implement!
                    }
                    case COMPARE_OPERATION -> {
                        assert false;
                        //TODO Implement!
                    }
                    case LOGIC_OPERATION -> {
                        assert false;
                        //TODO Implement!
                    }*/

                    // Jumping Instructions
                    //TODO Implement.

                    // System Instructions
                    case CAST -> {
                        EnumType data = (EnumType) in.getData();

                        TypedData tData = RuntimeCast.cast(register.getReg(RegisterType.CAST), data);
                        register.setReg(RegisterType.RETURN, tData);
                    }
                    case CALL_SYSTEM_LIB -> {
                        // If a null is returned then the method is void.
                        Tuple<String, Tuple<Boolean, Object>[]> data =
                                (Tuple<String, Tuple<Boolean, Object>[]>) in.getData();

                        // Process data->data
                        Object[] objs = new Object[data.y.length];
                        for(int i = 0; i < objs.length; i++) {
                            if(data.y[i].x) {
                                // Is Var
                                TypedData var = script.getVar((String) data.y[i].y);
                                objs[i] = var.getValue();
                            }
                            else
                                objs[i] = data.y[i].y;
                        }

                        // Execute
                        TypedData methodReturn = NativeLib.run(data.x, objs);
                        register.setReg(RegisterType.RETURN, methodReturn);
                    }
                    case EXIT -> {
                        running = false;
                        System.exit((int) register.getReg(RegisterType.EXIT_CODE).getValue());
                    }
                }
            }

            assert (cFrame == null); // If this is not null, not all instructions were processed in the frame.
        }

        running = false;
    }
}
