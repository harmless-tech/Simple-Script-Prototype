package tech.harmless.simplescript.shared.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A logger that adds a header to the logging message to give more details.
 */
//TODO Make async????
public final class Log {

    public static boolean DEBUG = false;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSSS", Locale.ENGLISH);
    private static PrintStream outStream;
    private static PrintStream errStream;

    static {
        try {
            new File("logs/").mkdirs();

            outStream = new PrintStream(new File("logs/simple.log"));
            outStream.println("Out logging init at " + new Date() + ".");
            outStream.flush();

            errStream = new PrintStream(new File("logs/simple-err.log"));
            errStream.println("Err logging init at " + new Date() + ".");
            errStream.flush();
        }
        catch(IOException e) {
            System.err.println("Could not setup loggers!");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    //TODO Move somewhere else?
    public static void script(Object message) {
        String out = header("SCRIPT") + message;

        System.out.println(out);
        outStream.println(out);
        outStream.flush();
    }

    public static void info(Object message) {
        String out = header("INFO") + message;

        System.out.println(out);
        outStream.println(out);
        outStream.flush();
    }

    public static void warn(Object message) {
        String out = header("WARN") + message;

        System.out.println(out);
        outStream.println(out);
        outStream.flush();
    }

    public static void error(Object message) {
        String out = header("ERROR") + message;

        System.err.println(out);
        errStream.println(out);
        errStream.flush();
    }

    public static void exception(Exception e) {
        e.printStackTrace();
        e.printStackTrace(errStream);

        errStream.flush();

        /*String out = "";

        System.err.println(out);
        errStream.println(out);
        errStream.flush();*/
    }

    public static void fatal(int code, Object message) {
        String out = header("FATAL") + message;

        System.err.println(out);
        errStream.println(out);
        errStream.flush();

        outStream.close();
        errStream.close();

        System.exit(code);
    }

    public static void debug(Object message) {
        if(!DEBUG)
            return;

        String out = header("DEBUG") + message;

        System.out.println(out);
        outStream.println(out);
        outStream.flush();
    }

    public static void trace(Object caller, Object message) {
        if(!DEBUG)
            return;

        String out = header("TRACE") + "Message from " + caller + ": " + message;

        System.out.println(out);
        outStream.println(out);
        outStream.flush();
    }

    private static String header(String name) {
        return "[" + dateFormat.format(new Date()) + " / " + name + "]: ";
    }
}
