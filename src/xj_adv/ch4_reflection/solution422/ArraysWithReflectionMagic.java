package xj_adv.ch4_reflection.solution422;

import java.lang.reflect.*;

public class ArraysWithReflectionMagic {
    public static <A> A deepClone(A source) {
        if (source == null || !source.getClass().isArray()) return source;
        int length = Array.getLength(source);
        @SuppressWarnings("unchecked")
        A clone = (A) Array.newInstance(
                source.getClass().getComponentType(), length);
        for (int i = 0; i < length; i++) {
            Array.set(clone, i, deepClone(Array.get(source, i)));
        }
        return clone;
    }
}
