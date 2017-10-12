package xj_adv.ch4_reflection.exercise422;

public class ArraysWithReflectionMagic {
    public static <A> A deepClone(A source) {
        // Returns a deep clone of the array source, but does not clone the
        // actual elements of the array (unless they are also arrays)
        //
        // If the source is not an array, simply return it
        return source;
    }
}
