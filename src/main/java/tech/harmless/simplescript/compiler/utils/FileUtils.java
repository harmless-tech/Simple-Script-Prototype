package tech.harmless.simplescript.compiler.utils;

import tech.harmless.simplescript.shared.utils.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class FileUtils {

    public static List<File> getAllFilesInDirR(String dir) {
        List<File> simpleFiles = new ArrayList<>();
        File rootDir = new File(dir);

        if(!rootDir.exists())
            Log.fatal(-204, "Directory " + dir + " does not exist!");

        try {
            File[] dirs = rootDir.listFiles();

            for(File d : dirs)
                if(d.isDirectory())
                    simpleFiles.addAll(getAllFilesInDirR(d.getAbsolutePath()));
                else
                    simpleFiles.add(d);
        }
        catch(Exception e) {
            Log.exception(e);
            Log.fatal(-203, "Failed collection of files in " + dir + "!");
        }

        return simpleFiles;
    }

    public static List<File> getAllFilesInDirR(String dir, String ext) {
        List<File> simpleFiles = getAllFilesInDirR(dir);
        simpleFiles.removeIf(f -> !f.getAbsolutePath().endsWith(ext));

        return simpleFiles;
    }
}
