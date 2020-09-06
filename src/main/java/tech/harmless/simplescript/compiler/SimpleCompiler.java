package tech.harmless.simplescript.compiler;

import tech.harmless.simplescript.shared.CompiledScript;
import tech.harmless.simplescript.shared.utils.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SimpleCompiler {

    private final Map<String, String> buildFileArgs; // Name, Data

    //TODO The buildfile location needs to be recorded.
    public SimpleCompiler(String file, boolean buildFile) {
        buildFileArgs = new HashMap<>(5);
        buildFileArgs.put("Name", "Binary");
        buildFileArgs.put("Version", "1.0.0");
        buildFileArgs.put("Out", "NULL");
        buildFileArgs.put("Source", "src");
        buildFileArgs.put("Entry", "Entry.simple");

        File f = new File(file);
        if(!f.exists())
            Log.fatal(-302, "File " + file + " does not exist!");

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
                            default -> {}
                            case "[Name]" -> buildFileArgs.put("Name", line2);
                            case "[Version]" -> buildFileArgs.put("Version", line2);
                            case "[Out]" -> buildFileArgs.put("Out", line2);
                            case "Source" -> buildFileArgs.put("Source", line2);
                            case "[Entry]" -> buildFileArgs.put("Entry", line2);
                        }
                    }
                }
            }
            catch(IOException e) {
                Log.exception(e);
                Log.fatal(-303, "Could not locate " + file + "!");
            }
        }
    }

    public void generateBinary() {
        // Exports.
        // Output to build folder.
    }

    public CompiledScript generate() {
        // Loads and compiles. Including deps.

        // Debug args
        String argList = "Build args: \n";
        argList += buildFileArgs.keySet().stream().map(key -> "    " + key + ": " + buildFileArgs.get(key) + "\n")
                .collect(Collectors.joining(""));
        Log.debug(argList);

        return null;
    }
}
