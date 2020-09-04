package tech.harmless.simplescript;

import tech.harmless.simplescript.compiler.SimpleCompiler;
import tech.harmless.simplescript.runtime.SimpleRuntime;
import tech.harmless.simplescript.runtime.systemlib.NativeLib;
import tech.harmless.simplescript.shared.CompiledScript;
import tech.harmless.simplescript.shared.data.EnumType;
import tech.harmless.simplescript.shared.utils.Log;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

//TODO Rewrite in a lower level language.
//TODO Add tests.
//TODO Fix confusion between args and params.
public class SimpleScript {

    private static SimpleScript INSTANCE; // Class instance for allowing access to flags.

    //TODO Figure out flags and defaults.
    public final String FLAG_NAME = "Default"; //TODO Init in constructor.
    public final boolean FLAG_DEBUG = true; //TODO Init in constructor.

    private final String DEFAULT_BUILD_FILE = "build.simplebuild";

    //TODO Needs a major rewrite.
    public SimpleScript(String[] args) {
		// Possible Args:
		// --unsafe, --entryfile, --out, --compile-run

        Log.info("Simple Script started.");
        Log.debug("Args: " + Arrays.toString(args));
        //System.out.println("I'm a pretty dumb compiler. Please be patient, I'm getting better."); TODO Move to compiler.

        //TODO Redo to include args and make dynamic.

        //SimpleCompiler.compile("Entry.simple");
        CompiledScript script = SimpleCompiler.compile("test/src2/Entry3.simple");
        new SimpleRuntime(script).run();
    }

    public static SimpleScript getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) {
        // Logger setup.
        try {
            PrintStream outStream = new PrintStream(new File("simple.log"));
            System.setOut(outStream);
            System.out.println("Out logging init.");

            PrintStream errStream = new PrintStream(new File("simple-err.log"));
            System.setErr(errStream);
            System.err.println("Err logging init.");
        }
        catch(IOException e) {
            System.err.println("Could not setup loggers!");
            e.printStackTrace();
            System.exit(-1);
        }

        // Instance setup.
        INSTANCE = new SimpleScript(args);
    }
}
