package tech.harmless.simplescript;

import tech.harmless.simplescript.compiler.SimpleCompiler;
import tech.harmless.simplescript.shared.utils.Log;

import java.util.Arrays;

//TODO Rewrite in a lower level language.
//TODO Add tests.
//TODO Fix confusion between args and params.
public class SimpleScript {

    private static SimpleScript INSTANCE; // Singleton instance, probably not needed.

    //TODO Needs a major rewrite.
    public SimpleScript(String[] args) {
        // SimpleScript compile [BUILDFILE or ENTRY FILE] --debug --run [ARGS[]]
        // SimpleScript run [BINARY] --debug

        //TODO Remove
        Log.DEBUG = true;
        //

        Log.info("Simple Script started.");
        Log.debug("Args: " + Arrays.toString(args));
        //System.out.println("I'm a pretty dumb compiler. Please be patient, I'm getting better."); TODO Move to compiler.

        //TODO Redo to include args and make dynamic.

        SimpleCompiler compiler = new SimpleCompiler("test/build3.simplebuild", true);
        compiler.generate();

        //SimpleCompiler.compile("Entry.simple");
        //CompiledScript script = OLDSimpleCompiler.compile("test/src2/Entry3.simple");
        //new SimpleRuntime(script).run();
    }

    public static SimpleScript getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) {
        // Instance setup.
        INSTANCE = new SimpleScript(args);
    }
}
