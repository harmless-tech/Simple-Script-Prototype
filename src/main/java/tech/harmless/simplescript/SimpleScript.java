package tech.harmless.simplescript;

import tech.harmless.simplescript.systemlib.SimpleLib;

import java.util.Date;

public class SimpleScript {

    public static String FLAG_NAME = "Default";
    public static boolean FLAG_DEBUG = true;

    private static final String DEFAULT_BUILD_FILE = "build.simplebuild";

    public SimpleScript(String[] args) {
        System.out.println("Simple Script started at " + new Date());

        //TODO Redo to include args.
        System.out.println("All args are ignored for now!");

        SimpleLib.run("println", "Hello! I'm the lib");
    }

    public static void main(String[] args) {
        new SimpleScript(args);
    }
}
