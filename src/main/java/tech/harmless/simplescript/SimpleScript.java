package tech.harmless.simplescript;

import tech.harmless.simplescript.compiler.SimpleCompiler;
import tech.harmless.simplescript.runtime.SimpleRuntime;
import tech.harmless.simplescript.shared.CompiledScript;
import tech.harmless.simplescript.runtime.systemlib.SimpleLib;

import java.util.Arrays;
import java.util.Date;

//TODO Rewrite in a lower level language.
//TODO Add tests.
public class SimpleScript {

    private static SimpleScript INSTANCE; // Class instance for allowing access to flags.
    //TODO Add log4j and format it.

    //TODO Figure out flags and defaults.
    public final String FLAG_NAME = "Default"; //TODO Init in constructor.
    public final boolean FLAG_DEBUG = true; //TODO Init in constructor.

    private final String DEFAULT_BUILD_FILE = "build.simplebuild";

    //TODO Needs a major rewrite.
    public SimpleScript(String[] args) {
		// Possible Args:
		// --unsafe, --entryfile, --out, 
		
        System.out.println("Simple Script started at " + new Date());
        System.out.println("Args: " + Arrays.toString(args));
        //System.out.println("I'm a pretty dumb compiler. Please be patient, I'm getting better."); TODO Move to compiler.

        //TODO Redo to include args and make dynamic.
        System.out.println("All args are ignored for now!");

        SimpleLib.run("println", "Hello! I'm the lib");
        System.out.println();

        //SimpleCompiler.compile("Entry.simple");
        CompiledScript script = SimpleCompiler.compile("test/src2/Entry3.simple");
        new SimpleRuntime(script).run();
    }

    public static SimpleScript getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) {
        INSTANCE = new SimpleScript(args);
    }
}
