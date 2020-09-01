package weetest;

import org.junit.jupiter.api.Test;
import tech.harmless.simplescript.systemlib.SimpleLib;

public class TestLib {

    @Test
    public void testPrintln() {
        SimpleLib.run("println", "Hello");
    }
}
