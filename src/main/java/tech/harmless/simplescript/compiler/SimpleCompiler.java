package tech.harmless.simplescript.compiler;

import tech.harmless.simplescript.SimpleScript;
import tech.harmless.simplescript.misc.Triplet;
import tech.harmless.simplescript.misc.Tuple;
import tech.harmless.simplescript.shared.CompiledScript;
import tech.harmless.simplescript.shared.instructions.EnumInstruction;
import tech.harmless.simplescript.shared.instructions.Instruction;
import tech.harmless.simplescript.shared.stack.MethodStackFrame;
import tech.harmless.simplescript.shared.stack.StackFrame;
import tech.harmless.simplescript.shared.vars.EnumType;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    private static final Set<String> reservedWords = new HashSet<>(); //TODO Use this!
    private static final HashMap<String, String> tagMap = new HashMap<>();

    static {
        // reservedWords
        reservedWords.add("if");
        reservedWords.add("if else");
        reservedWords.add("else");
        reservedWords.add("for");
        reservedWords.add("while");
        reservedWords.add("return");
        reservedWords.add("switch");
        reservedWords.add("case");
        reservedWords.add("break");

        reservedWords.add("import");
        reservedWords.add("static");
        reservedWords.add("final");

        reservedWords.add("void");
        reservedWords.add("object");
        reservedWords.add("boolean");
        reservedWords.add("byte");
        reservedWords.add("float");
        reservedWords.add("float32");
        reservedWords.add("float64");
        reservedWords.add("int");
        reservedWords.add("int8");
        reservedWords.add("int16");
        reservedWords.add("int32");
        reservedWords.add("int64");
        reservedWords.add("char");
        reservedWords.add("string");

        // tagMap
        tagMap.put("SYSTEM_LIB", "import System.simple;");
        tagMap.put("ENTRY", "void main()");
        tagMap.put("ENTRY_STRING", "string ENTRY_STRING = \"Not Yet Supported!\";");
        tagMap.put("FAIL", "throw \"Failed!\";");
    }

    public static CompiledScript compileUsingBuildFile(String buildFile) {
        //TODO Allow for processing of a build file.
        return null;
    }

    public static CompiledScript compile(String entryFile) { //TODO Check for directory info.
        String eFile = importFile(entryFile);
        eFile = removeCommentsAndEmpty(eFile);
        eFile = replacePreTags(eFile);
        eFile = oneLine(eFile);

        System.out.println("Hello!\n" + eFile);

        return implCompile(eFile);
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

    //TODO Change return type?
    private static CompiledScript implCompile(String line) {
        //TODO Add a lot of missing features.
        //TODO Compile automatically, not by hand.

        // void main() {{}{{meme();}}meme();}void meme() {{}}
        //TODO Remove!
        Map<String, StackFrame> methods = new HashMap<>();

        // Frame: main
        {
            Instruction[] ins = {
                    new Instruction(EnumInstruction.CREATE_SCOPE, null),
                    new Instruction(EnumInstruction.END_SCOPE, null),
                    new Instruction(EnumInstruction.CREATE_SCOPE, null),
                    new Instruction(EnumInstruction.CREATE_SCOPE, null),
                    new Instruction(EnumInstruction.INVOKE_METHOD, new Tuple<>("meme", new Object[0])),
                    new Instruction(EnumInstruction.END_SCOPE, null),
                    new Instruction(EnumInstruction.END_SCOPE, null),
                    new Instruction(EnumInstruction.INVOKE_METHOD, new Tuple<>("meme", new Object[0])),
                    new Instruction(EnumInstruction.ALLOC_VAR, new Triplet<>("return", EnumType.VOID, null)),
                    new Instruction(EnumInstruction.RETURN_METHOD, null)
            };
            MethodStackFrame msf = new MethodStackFrame(ins, EnumType.VOID);
            methods.put("Entry3.main", msf);
        }

        // Frame: meme
        {
            Instruction[] ins = {
                    new Instruction(EnumInstruction.CREATE_SCOPE, null),
                    new Instruction(EnumInstruction.END_SCOPE, null),
                    new Instruction(EnumInstruction.ALLOC_VAR, new Triplet<>("return", EnumType.VOID, null)),
                    new Instruction(EnumInstruction.RETURN_METHOD, null)
            };
            MethodStackFrame msf = new MethodStackFrame(ins, EnumType.VOID);
            methods.put("Entry3.meme", msf);
        }
        // Remove!

        //CompiledScript script = new CompiledScript();
        return new CompiledScript("Entry3.main", methods);
    }

    /*private static MethodStackFrame compileMethod(String line) {

    }*/
}
