package tech.harmless.simplescript;

import tech.harmless.simplescript.compiler.SimpleCompiler;
import tech.harmless.simplescript.runtime.SimpleRuntime;
import tech.harmless.simplescript.shared.CompiledScript;
import tech.harmless.simplescript.systemlib.SimpleLib;

import java.util.Date;

//TODO Rewrite in rust.
public class SimpleScript {

    public static String FLAG_NAME = "Default";
    public static boolean FLAG_DEBUG = true;

    private static final String DEFAULT_BUILD_FILE = "build.simplebuild";

    public SimpleScript(String[] args) {
        assert false;

        System.out.println("Simple Script started at " + new Date());
        System.out.println("I'm a pretty dumb compiler. Please be patient, I'm getting better.");

        //TODO Redo to include args.
        System.out.println("All args are ignored for now!");

        SimpleLib.run("println", "Hello! I'm the lib");
        System.out.println();

        //SimpleCompiler.compile("Entry.simple");
        CompiledScript script = SimpleCompiler.compile("Entry3.simple");
        new SimpleRuntime(script).run();
    }

    public static void main(String[] args) {
        new SimpleScript(args);
    }
}
