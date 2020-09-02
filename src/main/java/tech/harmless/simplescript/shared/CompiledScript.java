package tech.harmless.simplescript.shared;

import tech.harmless.simplescript.shared.stack.ScopeStackFrame;
import tech.harmless.simplescript.shared.stack.StackFrame;
import tech.harmless.simplescript.shared.vars.AllocVar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CompiledScript {

    private final Map<String, StackFrame> methods; // Methods names: ClassName.MethodName

    private final List<StackFrame> frames;
    private int currentFrame;

    public CompiledScript(String entryMethod /* EntryClassName.MethodName */, Map<String, StackFrame> methods) {
        this.methods = methods;

        frames = new ArrayList<>();
        currentFrame = 0;

        assert(methods.containsKey(entryMethod));
        frames.add(methods.get(entryMethod));

        assert(frames.size() >= 1);
    }

    public int currentFrameIndex() {
        assert(currentFrame >= 0);

        return currentFrame;
    }

    public StackFrame getCurrentFrame() {
        assert(currentFrame >= 0);

        return frames.get(currentFrame);
    }

    public void pushFrame(String methodName) {
        assert(methods.containsKey(methodName));

        frames.add(methods.get(methodName));
        currentFrame++;
    }

    public void pushFrame(ScopeStackFrame ssf) {
        assert(ssf != null);

        frames.add(ssf);
        currentFrame++;
    }

    /**
     * Discards the current frame.
     * @return {@code true} if the program is out of frames.
     */
    public boolean discardCurrentFrame() {
        frames.remove(currentFrame);
        currentFrame--;

        return currentFrame == -1;
    }

    public void setVar(String name, Object data) {
        assert(data != null);

        AllocVar var = getVar(name);
        var.setValue(data);
    }

    public AllocVar getVar(String name) {
        AllocVar var = null;

        for(int i = frames.size() - 1; i >= 0; i--) {
            var = frames.get(i).getVar(name);

            if(var != null)
                break;
        }

        assert(var != null);
        return var;
    }
}
