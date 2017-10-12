package xj_adv.ch4_reflection.solution421;

import java.lang.reflect.*;
import java.util.*;

public class MagicClassInstantiator {
    public static void main(String... args) throws Exception {
        System.out.println("Invoked with arguments " + Arrays.toString(args));

        if (args.length < 2) {
            System.err.println("Usage: MagicClassInstantiator className "
                    + "[param1 param2 param3 ...] methodName");
            return;
        }

        String className = args[0];
        Class<?>[] parameterTypes = new Class<?>[args.length - 2];
        Arrays.fill(parameterTypes, String.class);
        Object[] parameters = Arrays.copyOfRange(
                args, 1, args.length - 1, Object[].class);
        String methodName = args[args.length - 1];

        // 1. Use the "className" to load the class with Class.forName()
        Class<?> clazz = Class.forName(className);

        // 2. Use the "parameterTypes" to find the Constructor for the class
        // Hint: Use the getDeclaredConstructor() method on the class you loaded
        // to find the constructor, even if it is non-public
        Constructor<?> constructor = clazz.getDeclaredConstructor(parameterTypes);

        // 3. Set the constructor to be "accessible" so it can be called even if
        // it is private
        constructor.setAccessible(true);

        // 4. Construct the object by calling the newInstance() method on the
        // Constructor that you have just created, using the "parameters"
        Object object = constructor.newInstance(parameters);

        // 5. Find the method on the class given by "methodName".  You can
        // assume that the method does not have any parameters.
        Method method = clazz.getMethod(methodName);

        // 6. Set method to be "accessible" if you used getDeclaredMethod()
        // - not necessary, we used getMethod()

        // 7. Call the method with "invoke()", passing in the object you created
        // as the first parameter.
        Object result = method.invoke(object);

        // 8. If the return type of the method is not void, print the result to
        // the console with System.out.println()
        if (method.getReturnType() != void.class) {
            System.out.println("result = " + result);
        }

    }
}
