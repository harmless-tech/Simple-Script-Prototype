package tech.harmless.simplescript.runtime;

import tech.harmless.simplescript.runtime.instructionext.ExtArithmeticOperation;
import tech.harmless.simplescript.runtime.instructionext.ExtCast;
import tech.harmless.simplescript.runtime.instructionext.ExtLogicOperation;
import tech.harmless.simplescript.runtime.instructionext.ExtRelationalOperation;
import tech.harmless.simplescript.runtime.memory.Cache;
import tech.harmless.simplescript.runtime.systemlib.NativeLib;
import tech.harmless.simplescript.shared.CompiledScript;
import tech.harmless.simplescript.shared.data.EnumType;
import tech.harmless.simplescript.shared.data.TypedData;
import tech.harmless.simplescript.shared.instructions.Instruction;
import tech.harmless.simplescript.shared.stack.ScopeStackFrame;
import tech.harmless.simplescript.shared.stack.StackFrame;
import tech.harmless.simplescript.shared.types.EnumArithmeticOperation;
import tech.harmless.simplescript.shared.types.EnumLogicOperation;
import tech.harmless.simplescript.shared.types.EnumRelationalOperation;
import tech.harmless.simplescript.shared.types.CacheType;
import tech.harmless.simplescript.shared.utils.Log;
import tech.harmless.simplescript.shared.utils.Triplet;
import tech.harmless.simplescript.shared.utils.Tuple;

import java.util.Stack;

// At no time should there be instructions added during the runtime.
public class SimpleRuntime {

    private final CompiledScript script;
    private final Cache cache;
    private final Stack<Integer> jump;

    private boolean running;

    public SimpleRuntime(CompiledScript script) {
        this.script = script;

        cache = new Cache();
        jump = new Stack<>();
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
                        Log.debug("ALLOC_VAR");

                        Triplet<String, EnumType, Object> data = (Triplet<String, EnumType, Object>) in.getData();
                        cFrame.allocVar(data.x, new TypedData(data.y, data.z));
                    }
                    case SET_VAR -> {
                        Log.debug("SET_VAR");

                        Tuple<String, Object> data = (Tuple<String, Object>) in.getData();
                        script.setVar(data.x, data.y);
                    }
                    case SET_VAR_REG -> {
                        Log.debug("SET_VAR_REG");

                        String name = (String) in.getData();
                        TypedData data = cache.getReg(CacheType.RETURN);

                        script.setVar(name, data.getValue());
                    }
                    /*case GET_VAR -> { TODO Remove.
                        Log.debug("GET_VAR");

                        String data = (String) in.getData();
                        script.getVar(data);
                    }*/

                    // Frame Instructions
                    case CREATE_FRAME -> {
                        Log.debug("CREATE_FRAME");

                        Triplet<Boolean, String, Object[]> data = (Triplet<Boolean, String, Object[]>) in.getData();
                        if(data.x) {
                            //TODO Add entry args into stack frame vars. (method(param 1, param 2)) (In compiler)
                            // Something like CALL_NATIVE
                            script.pushFrame(data.y);
                        }
                        else
                            script.pushFrame(new ScopeStackFrame(cFrame));

                        cFrame = null;
                    }
                    case DISCARD_FRAME -> {
                        Log.debug("DISCARD_FRAME");

                        // The return register should have already been filled with data.
                        script.discardCurrentFrame();
                        cFrame = null;
                    }

                    // Register Instructions
                    case LOAD_REG -> {
                        Log.debug("LOAD_REG");

                        Tuple<Integer, TypedData> data = (Tuple<Integer, TypedData>) in.getData();
                        cache.setReg(data.x, data.y);
                    }
                    case LOAD_REG_VAR -> {
                        Log.debug("LOAD_REG_VAR");

                        Tuple<Integer, String> data = (Tuple<Integer, String>) in.getData();
                        TypedData varData = script.getVar(data.y);

                        cache.setReg(data.x, varData);
                    }
                    case TRANSFER_REG -> {
                        Log.debug("TRANSFER_REG");

                        Tuple<Integer, Integer> data = (Tuple<Integer, Integer>) in.getData();
                        cache.setReg(data.y, cache.getReg(data.x));
                    }

                    // Operation Instructions
                    case ARITHMETIC_OPERATION -> {
                        Log.debug("ARITHMETIC_OPERATION");

                        EnumArithmeticOperation data = (EnumArithmeticOperation) in.getData();
                        TypedData dat1 = cache.getReg(CacheType.OPERATION_ONE);
                        TypedData dat2 = cache.getReg(CacheType.OPERATION_TWO);

                        TypedData rData = ExtArithmeticOperation.arithmetic(data, dat1, dat2);
                        cache.setReg(CacheType.RETURN, rData);
                    }
                    case RELATIONAL_OPERATION -> {
                        Log.debug("COMPARE_OPERATION");

                        EnumRelationalOperation data = (EnumRelationalOperation) in.getData();
                        TypedData dat1 = cache.getReg(CacheType.OPERATION_ONE);
                        TypedData dat2 = cache.getReg(CacheType.OPERATION_TWO);

                        TypedData rData = ExtRelationalOperation.relational(data, dat1, dat2);
                        cache.setReg(CacheType.RETURN, rData);
                    }
                    case LOGIC_OPERATION -> {
                        Log.debug("LOGIC_OPERATION");

                        EnumLogicOperation data = (EnumLogicOperation) in.getData();
                        TypedData dat1 = cache.getReg(CacheType.OPERATION_ONE);
                        TypedData dat2 = cache.getReg(CacheType.OPERATION_TWO);

                        TypedData rData = ExtLogicOperation.logic(data, dat1, dat2);
                        cache.setReg(CacheType.RETURN, rData);
                    }

                    // Jumping Instructions
                    case PUSH_JUMP -> {
                        Log.debug("PUSH_JUMP");

                        int data = (int) in.getData();
                        TypedData reg = cache.getReg(CacheType.RETURN);
                        assert reg.getType() == EnumType.BOOLEAN && reg.getValue() != null;

                        if((boolean) reg.getValue())
                            jump.add(data);

                        cFrame.jumpInstructionPos(data);
                    }
                    case POP_JUMP -> {
                        Log.debug("POP_JUMP");

                        assert jump.size() > 0;

                        int data = jump.pop();
                        cFrame.jumpInstructionPos(data);
                    }

                    // System Instructions
                    case CAST -> {
                        Log.debug("CAST");

                        EnumType data = (EnumType) in.getData();

                        TypedData tData = ExtCast.cast(cache.getReg(CacheType.CAST), data);
                        cache.setReg(CacheType.RETURN, tData);
                    }
                    case CALL_NATIVE -> {
                        Log.debug("CALL_NATIVE");

                        //TODO Allow calling of other natives.

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
                        cache.setReg(CacheType.RETURN, methodReturn);
                    }
                    case EXIT -> {
                        Log.debug("EXIT");

                        running = false;
                        System.exit((int) cache.getReg(CacheType.EXIT_CODE).getValue());
                    }
                }
            }

            assert (cFrame == null); // If this is not null, not all instructions were processed in the frame.
        }

        running = false;
    }
}
