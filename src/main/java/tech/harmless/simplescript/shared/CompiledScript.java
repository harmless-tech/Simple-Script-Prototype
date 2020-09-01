package tech.harmless.simplescript.shared;

import tech.harmless.simplescript.shared.vars.AllocVar;

import java.util.ArrayList;

//TODO This will be the part that the runtime can use.
public class CompiledScript {
	
	private final String entryMethod; // EntryClassName.MethodName
	private final ArrayList<StackFrame> frames;
	private final ArrayList<StackFrame> methodFrames;

	private int currentFrame;

	/*
	 * Focus on classes, methods, and vars.
	 * Structure
	 * - classes
	 *  - global vars (global scope)
	 *  - methods
	 *      - vars (method scope)
	 *      - call to other methods.
	 */

	/*
	 * Internal methods are marked with the name of the class.
	 */

	public CompiledScript(String entryMethod, StackFrame initStack, ) {
		this.entryMethod = entryMethod;

		frames = new ArrayList<>();
		frames.add(initStack);
		currentFrame = 0;
	}

	public int currentFrame() {
		return currentFrame;
	}

	public StackFrame getCurrentFrame() {
		return frames.get(currentFrame);
	}

	public void nextFrame() {
		currentFrame++;
	}

	public void removeCurrentFrame() {
		frames.remove(currentFrame);
		currentFrame--;
	}

	public AllocVar getVar(String name) {
		AllocVar var = null;

		for(int i = frames.size() - 1; i >= 0; i--) {
			var = frames.get(i).getVar(name);

			if(var != null)
				break;
		}

		return var;
	}
}
