package tech.harmless.simplescript.compiler;

import tech.harmless.simplescript.compiler.utils.FileUtils;
import tech.harmless.simplescript.shared.CompiledScript;
import tech.harmless.simplescript.shared.data.TypedData;
import tech.harmless.simplescript.shared.stack.MethodStackFrame;
import tech.harmless.simplescript.shared.utils.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//TODO Allow for single file compiling.
//TODO Safety.
//TODO Account for string.
/*
 * Take everything up to a semi-colon or a open/close bracket.
 * If semi-colon pass to statement processor, otherwise if open/close bracket pass to scope processor.
 * Process and add instructions.
 */
public class SimpleCompiler {

    private final Map<String, String> buildFileArgs; // Name, Data

    // These will be exported out to the CompiledScript.
    private Map<String, TypedData> globals;
    private Map<String, MethodStackFrame> methods;

    public SimpleCompiler(String file, boolean buildFile) {
        buildFileArgs = new HashMap<>(5);
        buildFileArgs.put("Name", "Binary");
        buildFileArgs.put("Version", "1.0.0");
        buildFileArgs.put("Out", "NULL");
        buildFileArgs.put("Source", "src");
        buildFileArgs.put("Entry", "Entry.simple");

        File f = new File(file);
        if(!f.exists())
            Log.fatal(-201, "File " + file + " does not exist!");

        if(buildFile) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(f));
                List<String> list = new ArrayList<>();

                String in;
                while((in = reader.readLine()) != null)
                    list.add(in);
                reader.close();

                String[] lines = list.toArray(new String[0]);

                for(int i = 0; i < lines.length; i++) {
                    String line = lines[i];

                    if(!line.startsWith("#") && !line.isBlank() && line.startsWith("[")) {
                        line = line.trim();
                        String line2 = i + 1 < lines.length ? lines[i + 1].trim() : "";
                        i++;

                        switch(line) {
                            case "[Name]" -> buildFileArgs.put("Name", line2);
                            case "[Version]" -> buildFileArgs.put("Version", line2);
                            case "[Out]" -> buildFileArgs.put("Out", line2);
                            case "[Source]" -> buildFileArgs.put("Source", line2);
                            case "[Entry]" -> buildFileArgs.put("Entry", line2);
                        }
                    }
                }

                buildFileArgs.put("Source", f.getAbsolutePath().replace(f.getName(), "") + buildFileArgs.get("Source"));
            }
            catch(IOException e) {
                Log.exception(e);
                Log.fatal(-202, "Could not locate " + file + "!");
            }
        }
    }

    public void generateBinary() {
        // Exports.
        // Output to build folder.
    }

    //TODO Scan source directory and collect all of the .simple files and try to process them.
    //TODO Multi-threaded processing.
    public CompiledScript generate() {
        // Loads and compiles. Including deps.

        // Debug args.
        String argList = "Build args: \n";
        argList += buildFileArgs.keySet().stream().map(key -> "    " + key + ": " + buildFileArgs.get(key) + "\n")
                .collect(Collectors.joining(""));
        Log.debug(argList);

        // Load all .simple file(s) in the src directory.
        List<File> simpleFiles = FileUtils.getAllFilesInDirR(buildFileArgs.get("Source"), ".simple");
        Log.debug("Files to compile: " + Arrays.toString(simpleFiles.toArray()));

        // Multi-threaded file processing.
        ThreadGroup group = new ThreadGroup("Compiler");
        Thread[] threads = new Thread[simpleFiles.size()];
        for(int i = 0; i < threads.length; i++) {
            final int finalI = i;
            threads[i] = new Thread(group, () -> compile(simpleFiles.get(finalI)));
        }

        for(Thread t : threads)
            t.start();

        try {
            for(Thread t : threads)
                t.join();
        }
        catch(InterruptedException e) {
            Log.exception(e);
            Log.fatal(-205, "A compile thread was interrupted!");
        }

        // Check Imports???

        //globals.putAll();
        //methods.putAll();
        //return new CompiledScript();
        return null;
    }

    private void compile(File file) {
        String content = importFile(file);
        content = preProcess(content);
    }

    private String importFile(File file) {
        StringBuilder content = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String in;
            while((in = reader.readLine()) != null)
                content.append(in).append("\n");
        }
        catch(IOException e) {
            Log.exception(e);
            Log.fatal(-206, "Failed to import file " + file.getAbsolutePath() + "!");
        }

        return content.toString();
    }

    //region <PreProcess>

    private String preProcess(String content) {
        // Remove Comments and Empty Lines.
        content = preCleanUp(content);
        content = preOneLine(content);
        //content = preRemoveSpaces(content);

        Log.debug("PreProcessed: " + content);

        return content;
    }

    private String preCleanUp(String content) {
        // // Comments.
        String[] lines = content.split("\n");
        for(int i = 0; i < lines.length; i++) {
            int index = lines[i].indexOf("//");

            if(index != -1)
                lines[i] = lines[i].substring(0, index);
        }

        // ** Comments.
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

        // Remove Empty Lines.
        StringBuilder reconstruct = new StringBuilder();
        for(String line : lines)
            if(!line.isBlank())
                reconstruct.append(line.trim()).append("\n");

        return reconstruct.toString();
    }

    private String preOneLine(String content) {
        StringBuilder one = new StringBuilder();

        String[] lines = content.split("\n");
        for(String line : lines)
            one.append(line);

        Log.debug(one.toString()); //TODO Remove!

        return one.toString();
    }

    //TODO Needed?
    /*private String preRemoveSpaces(String content) {
        // Removes bad spaces and tabs.
        //TODO Remove useless spaces.
        //Log.debug(content);

        // Remove: Space before { - Space after , - Space before and after = + - / * %

        boolean inString = false; // There should always be a char before a ".


        return "NULL";
    }*/

    //endregion

    //region <Process>

    private void processor(String content) {
        // Find ;, {, or }.
        // If ; process statement.
        // If {} process scope.

        while(content != null) {
            int semiIndex = content.indexOf(';');
            int openIndex = content.indexOf('{');
            int closeIndex = content.indexOf('}');

            if(semiIndex == -1 && openIndex == -1)
                content = null;
            else if(semiIndex < openIndex || openIndex == -1) {
                // Should only be imports and globals.
            }
            else if(closeIndex != -1) {
                // Method
                //TODO What about scopes and control statements...
            }
            else
                Log.fatal(-207, "Missing semi-colon, open brace, or close brace!");
        }
    }

    private void processStatement() {

    }

    private void processMethod() {

    }

    private void processScope() {

    }

    //endregion

    //TODO PreProcess
    //TODO Process
    //TODO PostProcess??
    //TODO Cleanup?? /\??
}
