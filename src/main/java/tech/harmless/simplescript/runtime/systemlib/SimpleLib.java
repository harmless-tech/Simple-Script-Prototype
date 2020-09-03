package tech.harmless.simplescript.runtime.systemlib;

import tech.harmless.simplescript.shared.data.EnumType;

import java.lang.reflect.Method;

//TODO Move to shared or to runtime??
public class SimpleLib {

    private static final SimpleLib SIMPLE_LIB;
    private static final Method[] methods;

    //TODO Add a heap somewhere. Later though.

    static {
        SIMPLE_LIB = new SimpleLib();

        // Create an array of all possible methods.
        Class<?> c = SimpleLib.class;
        methods = c.getDeclaredMethods();
    }

    // If the method is a void return type, then this method will return null.
    public static Object run(String methodName, Object... args) {
        assert !methodName.equals("run");
        assert !methodName.equals("parseArg");

        try {
            for(Method m : methods) {
                //TODO Check if args are valid??? (In the compiler)

                if(m.getName().equals(methodName)) {
                    assert args != null;
                    Object obj = m.invoke(SIMPLE_LIB, args); //TODO Account for args.

                    assert m.getReturnType().equals(Void.TYPE) == (obj == null);
                    return obj;
                }
            }
        }
        catch(Exception e) {
            System.err.println(methodName + " call did not work!");
            e.printStackTrace();
            assert false;
        }

        assert false; // This should never be hit, because the compiler will check everything beforehand.
        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T> T parseArg(int pos, Object[] args) {
        assert pos < 0 && pos > args.length;

        return (T) args[pos];
    }

    private void println(Object print) {
        System.out.println(print);
        //TODO More later?
    }

    private void print(Object print) {
        System.out.print(print);
        //TODO More later?
    }

    private void exit(int code) {
        //TODO Not super ideal.
        System.exit(code);
    }

    private long getMemoryInBytes() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    // Returns a "pointer" to the list.
    private long createList(EnumType type) {
        // Check for an EnumType.
        //TODO Implement.
        return -1;
    }

    private boolean addToList(long pointer, Object data) {
        //TODO Implement.
        return false;
    }
}
