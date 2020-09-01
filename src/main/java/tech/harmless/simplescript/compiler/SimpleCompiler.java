package tech.harmless.simplescript.compiler;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SimpleCompiler {
    /*
     * LINE PRE-PROCESS
     * Take in a line.
     * Check it for a // comment. Remove the // comment and everything after it.
     * Check it for ** comment and remove everything after.
     * Check for end of ** comment.
     * Process line.
     * Check for final. (Needs a special compile)
     * If ** comment is still active, check for end of ** comment and remove everything after.
     *
     * NOTES
     * Make sure anything in "" is ignored unless the "" is in a comment.
     * Process #tag commands.
     * This compiler might just ignore a lot of junk information.
     * Finals should just insert the statement into every place that they are declared in.
     * Imports.
     *
     * LINE PROCESS
     * If {, start a new scope. If }, end scope.
     * Check if it is a special statement. (for, while, if, if/else, else, return, switch, case, break,
     *  continue??, throw) "Special Methods"
     * Split by semi-colons.
     *
     * TRUE ORDER
     * Remove all comments.
     * Put all finals into the statements where they are declared.
     *
     * LIST OF DOING
     * Remove all comments from the file.
     * Tag processing.
     * All file data on one line.
     */

    private static final HashMap<String, String> tagMap = new HashMap<>();

    static {
        tagMap.put("SYSTEM_LIB", "import System.simple;");
        tagMap.put("ENTRY", "void main()");
        tagMap.put("ENTRY_STRING", "string ENTRY_STRING = \"Not Yet Supported!\";");
        tagMap.put("FAIL", "throw \"Failed!\";");
    }

    public static boolean compileUsingBuildFile(String buildFile) {
        //TODO Allow for processing of a build file.
        return false;
    }

    public static boolean compile(String entryFile) { //TODO Check for directory info.
        String eFile = importFile(entryFile);
        eFile = removeCommentsAndEmpty(eFile);
        eFile = replacePreTags(eFile);
        eFile = oneLine(eFile);

        System.out.println("Hello!\n" + eFile);

        return true;
    }

    private static String importFile(String fileName) {
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileName));
            byte[] file = in.readAllBytes();
            in.close();

            return new String(file);
        }
        catch(IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return "NULL"; //TODO Handle this!
    }

    private static String removeCommentsAndEmpty(String file) {
        String[] lines = file.split("\n");

        // Process // comments.
        for(int i = 0; i < lines.length; i++) {
            int index = lines[i].indexOf("//");

            if(index != -1)
                lines[i] = lines[i].substring(0, index);
        }

        // Process ** comments.
        boolean inComment = false;
        for(int i = 0; i < lines.length; i++) {
            int startIndex = lines[i].indexOf("/*");
            int endIndex = lines[i].indexOf("*/");

            if(startIndex != -1)
                inComment = true;

            if(inComment) {
                if(startIndex != -1 && endIndex == -1) // Only start.
                    lines[i] = lines[i].substring(0, startIndex);
                else if(startIndex != -1) // Start and End.
                    lines[i] = lines[i].substring(0, startIndex) + lines[i].substring(endIndex + 2);
                else if(endIndex != -1) // Only End.
                    lines[i] = lines[i].substring(endIndex + 2);
                else // Remove Whole Line.
                    lines[i] = "";
            }

            if(inComment && endIndex != -1)
                inComment = false;
        }

        // Remove empty lines.
        StringBuilder reconstruct = new StringBuilder();
        for(String line : lines)
            if(!line.isBlank())
                reconstruct.append(line.trim()).append("\n");

        return reconstruct.toString();
    }

    private static String replacePreTags(String file) {
        /* TAGS:
         * SYSTEM_LIB
         * ENTRY
         * ENTRY_STRING
         * FAIL
         */

        String[] lines = file.split("\n");

        for(int i = 0; i < lines.length; i++)
            if(lines[i].contains("#tag"))
                for(Map.Entry<String, String> tags : tagMap.entrySet())
                    if(lines[i].contains(("#tag " + tags.getKey())))
                        lines[i] = lines[i].replace("#tag " + tags.getKey(), tags.getValue());

        StringBuilder reconstruct = new StringBuilder();
        for(String line : lines)
            reconstruct.append(line.trim()).append("\n");

        return reconstruct.toString();
    }

    private static String oneLine(String file) {
        StringBuilder one = new StringBuilder();

        String[] lines = file.split("\n");
        for(String line : lines)
            one.append(line);

        return one.toString();
    }

    //TODO Change return type.
    private static void implCompile(String line) {

    }
}
