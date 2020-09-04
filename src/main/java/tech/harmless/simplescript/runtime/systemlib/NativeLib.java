package tech.harmless.simplescript.runtime.systemlib;

import tech.harmless.simplescript.shared.data.EnumType;
import tech.harmless.simplescript.shared.data.TypedData;

import java.lang.reflect.Method;

//TODO Flesh this out.
public class NativeLib {

    private static final NativeLib SIMPLE_LIB;
    private static final Method[] methods;

    //TODO Add a heap with pointer system for more complex items.

    static {
        SIMPLE_LIB = new NativeLib();

        // Create an array of all possible methods.
        Class<?> c = NativeLib.class;
        methods = c.getDeclaredMethods();
    }

    // If the method is a void return type, then this method will return null.
    public static TypedData run(String methodName, Object... args) {
        assert !methodName.equals("run");
        assert !methodName.equals("parseArg");

        try {
            for(Method m : methods) {
                //TODO Check if args are valid??? (In the compiler)

                if(m.getName().equals(methodName)) {
                    assert args != null;
                    TypedData data = (TypedData) m.invoke(SIMPLE_LIB, args);

                    assert data != null;
                    return data;
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

    private TypedData exit(int code) {
        //TODO Not super ideal.
        System.exit(code);
        return TypedData.VOID;
    }

    private TypedData getMemoryInBytes() {
        long mem =  Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        return new TypedData(EnumType.INT64, mem);
    }

    //region <ConsoleIO>

    private TypedData println(Object print) {
        System.out.println(print);
        return TypedData.VOID;
        //TODO More later?
    }

    private TypedData print(Object print) {
        System.out.print(print);
        return TypedData.VOID;
        //TODO More later?
    }

    //endregion

    //region <Math>

    private TypedData round(double d) {
        //TODO Implement.
        return new TypedData(EnumType.INT32, 0);
    }

    //endregion

    //region <List>

    // Returns a "pointer" to the list.
    private TypedData createList(EnumType type) {
        // Check for an EnumType.
        //TODO Implement.
        return new TypedData(EnumType.INT64, -1);
    }

    private TypedData addToList(long pointer, Object data) {
        //TODO Implement.
        return new TypedData(EnumType.BOOLEAN, false);
    }

    //endregion

    //region <Network>
    //endregion

    //region <FileIO>
    //endregion

    //region <StringUtils>
    //endregion
}
