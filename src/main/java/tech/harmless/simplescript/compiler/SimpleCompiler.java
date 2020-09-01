package tech.harmless.simplescript.compiler;

public class SimpleCompiler {
    /*
     * LINE PRE-PROCESS
     * Take in a line.
     * Check it for a // comment. Remove the // comment and everything after it.
     * Check it for ** comment and remove everything after.
     * Check for end of ** comment.
     * Process line.
     * If ** comment is still active, check for end of ** comment and remove everything after.
     *
     * NOTES
     * Make sure anything in "" is ignored unless the "" is in a comment.
     * Process #tag commands.
     * This compiler might just ignore a lot of junk information.
     *
     * LINE PROCESS
     * If {, start a new scope. If }, end scope.
     * Check if it is a special statement. (for, while, if, if/else, else, return) "Special Methods"
     * Split by semi-colons.
     */
}
