package tech.harmless.simplescript.shared;

import tech.harmless.simplescript.shared.data.EnumType;
import tech.harmless.simplescript.shared.data.TypedData;
import tech.harmless.simplescript.shared.instructions.EnumInstruction;
import tech.harmless.simplescript.shared.instructions.Instruction;
import tech.harmless.simplescript.shared.stack.MethodStackFrame;
import tech.harmless.simplescript.shared.stack.ScopeStackFrame;
import tech.harmless.simplescript.shared.stack.StackFrame;
import tech.harmless.simplescript.shared.types.CacheType;
import tech.harmless.simplescript.shared.utils.Triplet;
import tech.harmless.simplescript.shared.utils.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CompiledScript {

    private final Map<String, MethodStackFrame> methods; // Methods names: ClassName.MethodName

    private final List<StackFrame> frames;
    private int currentFrame;

    //TODO Possible refactor of var passing!
    public CompiledScript(String entryMethod, Map<String, MethodStackFrame> methods, Map<String, TypedData> globals) {
        this.methods = methods;
        assert methods.containsKey(entryMethod);
        //TODO Add in precompiled system lib.

        frames = new ArrayList<>();
        currentFrame = 0;

        // Top level stack.
        Instruction[] topIns = {
                new Instruction(EnumInstruction.CREATE_FRAME, new Triplet<>(false, null, null)),
                new Instruction(EnumInstruction.CREATE_FRAME, new Triplet<>(true, entryMethod, null)),
                new Instruction(EnumInstruction.LOAD_REG, new Tuple<>(
                        CacheType.EXIT_CODE, new TypedData(EnumType.INT32, 0))),
                new Instruction(EnumInstruction.EXIT, null),
                new Instruction(EnumInstruction.DISCARD_FRAME, null)
        };
        StackFrame topStack = new MethodStackFrame(topIns);

        for(Map.Entry<String, TypedData> global : globals.entrySet())
            topStack.allocVar(global.getKey(), global.getValue());

        frames.add(topStack);
        assert frames.size() >= 1;
    }

    public boolean hasNexFrame() {
        return currentFrame >= 0;
    }

    public int currentFrameIndex() {
        assert currentFrame >= 0;

        return currentFrame;
    }

    public StackFrame getCurrentFrame() {
        assert currentFrame >= 0;

        return frames.get(currentFrame);
    }

    public void pushFrame(String methodName) {
        assert methods.containsKey(methodName);

        frames.add(new MethodStackFrame((MethodStackFrame) methods.get(methodName)));
        currentFrame++;
    }

    public void pushFrame(ScopeStackFrame ssf) {
        assert ssf != null;

        frames.add(ssf);
        currentFrame++;
    }

    /**
     * Discards the current frame.
     *
     * @return {@code true} if the program is out of frames.
     */
    public boolean discardCurrentFrame() {
        frames.remove(currentFrame);
        currentFrame--;

        return currentFrame == -1;
    }

    public void setVar(String name, Object data) {
        assert data != null;

        TypedData var = getVar(name);
        var.setValue(data);
    }

    public TypedData getVar(String name) {
        TypedData var = null;

        for(int i = frames.size() - 1; i >= 0; i--) {
            var = frames.get(i).getVar(name);

            if(var != null)
                break;
        }

        assert var != null;
        return var;
    }
}
