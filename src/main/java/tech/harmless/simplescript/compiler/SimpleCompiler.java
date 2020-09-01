package tech.harmless.simplescript.compiler;

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
     */

    public static boolean compileUsingBuildFile(String buildFile) {
        //TODO Allow for processing of a build file.
        return false;
    }

    public static boolean compile(String entryFile) {
        
    }
}
