package tech.harmless.simplescript.shared.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A logger that adds a header to the logging message to give more details.
 */
public final class Log {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSSS", Locale.ENGLISH);

    //TODO Move somewhere else?
    public static void script(Object message) {
        System.out.println(header("SCRIPT") + message);
    }

    public static void info(Object message) {
        System.out.println(header("INFO") + message);
    }

    public static void warn(Object message) {
        System.out.println(header("WARN") + message);
    }

    public static void error(Object message) {
        System.err.println(header("ERROR") + message);
    }

    public static void fatal(int code, Object message) {
        System.err.println(header("FATAL") + message);
        System.exit(code);
    }

    public static void debug(Object message) {
        System.out.println(header("DEBUG") + message);
    }

    public static void trace(Object caller, Object message) {
        System.out.println(header("TRACE") + "Message from " + caller + ": " + message);
    }

    private static String header(String name) {
        return "[" + dateFormat.format(new Date()) + " / " + name + "]: ";
    }
}
