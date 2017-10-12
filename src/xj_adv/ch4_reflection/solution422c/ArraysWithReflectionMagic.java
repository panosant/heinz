package xj_adv.ch4_reflection.solution422c;

import java.lang.reflect.*;

public class ArraysWithReflectionMagic {
    private static final Method cloneMethod;

    static {
        try {
            cloneMethod = Object.class.getDeclaredMethod("clone");
            cloneMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static <A> A deepClone(A source) {
        if (source == null || !source.getClass().isArray()) {
            return source;
        }
        try {
            @SuppressWarnings("unchecked")
            A clone = (A) cloneMethod.invoke(source);
            if (source.getClass().getComponentType().isArray()) {
                for (int i = 0, length = Array.getLength(source); i < length; i++) {
                    Array.set(clone, i, deepClone(Array.get(source, i)));
                }
            }
            return clone;
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}
