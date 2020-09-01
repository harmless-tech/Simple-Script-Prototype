package tech.harmless.simplescript.systemlib;

import java.lang.reflect.Method;

public class SimpleLib {

    private static final SimpleLib SIMPLE_LIB = new SimpleLib();

    public static boolean run(String methodName, Object... vars) {
        if(methodName.equals("run"))
            return false;

        //TODO This only needs to run on startup.

        try {
            Class<?> c = SimpleLib.class;
            Method[] methods = c.getDeclaredMethods();

            for(Method m : methods) {
                //TODO Add support for variable args.
                //TODO Add support for return types.

                if(m.getName().equals(methodName))
                    m.invoke(SIMPLE_LIB, vars);
            }
        }
        catch(Exception e) {
            System.err.println(methodName + "call did not work!");
            e.printStackTrace();
        }

        return false;
    }

    private void println(Object print) {
        System.out.println(print);
        //TODO More later.
    }

}
