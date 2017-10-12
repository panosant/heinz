package xj_adv.ch4_reflection.solution422b;

import java.lang.reflect.*;

public class ArraysWithReflectionMagic {
    public static <A> A deepClone(A source) {
        if (source == null || !source.getClass().isArray()) {
            return source;
        }
        @SuppressWarnings("unchecked")
        A clone = (A) deepClone0(source);
        if (source.getClass().getComponentType().isArray()) {
            for (int i = 0, length = Array.getLength(source); i < length; i++) {
                Array.set(clone, i, deepClone(Array.get(source, i)));
            }
        }
        return clone;
    }

    private static Object deepClone0(Object source) {
        assert source != null;
        if (source instanceof Object[]) return ((Object[]) source).clone();
        else if (source instanceof byte[]) return ((byte[]) source).clone();
        else if (source instanceof short[]) return ((short[]) source).clone();
        else if (source instanceof int[]) return ((int[]) source).clone();
        else if (source instanceof long[]) return ((long[]) source).clone();
        else if (source instanceof char[]) return ((char[]) source).clone();
        else if (source instanceof float[]) return ((float[]) source).clone();
        else if (source instanceof double[]) return ((double[]) source).clone();
        else if (source instanceof boolean[])
            return ((boolean[]) source).clone();
        else throw new AssertionError("Not an array");
    }
}