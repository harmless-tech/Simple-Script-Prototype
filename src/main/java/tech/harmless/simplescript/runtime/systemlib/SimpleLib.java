package tech.harmless.simplescript.runtime.systemlib;

import tech.harmless.simplescript.shared.data.EnumType;

import java.lang.reflect.Method;

//TODO Move to shared or to runtime??
public class SimpleLib {

    private static final SimpleLib SIMPLE_LIB;
    private static final Method[] methods;

    static {
        SIMPLE_LIB = new SimpleLib();

        // Create an array of all possible methods.
        Class<?> c = SimpleLib.class;
        methods = c.getDeclaredMethods();
    }

    // If the method is a void return type, then this method will return null.
    public static Object run(String methodName, Object... vars) {
        assert !methodName.equals("run");

        try {
            for(Method m : methods) {
                //TODO Check if args are valid??? (In the compiler)

                if(m.getName().equals(methodName)) {
                    Object obj = m.invoke(SIMPLE_LIB, vars);

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

    private void println(Object print) {
        System.out.println(print);
        //TODO More later.
    }

    private void print(Object print) {
        System.out.print(print);
        //TODO More later.
    }

    //TODO Allow for ending of the runtime.
    private void exit(int code) {
        throw new UnsupportedOperationException();
    }

    // Returns a "pointer" to the list.
    private long createList(EnumType type) {
        //TODO Implement.
        return -1;
    }
}
