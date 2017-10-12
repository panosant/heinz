package xj_adv.ch5_datastructs.exercise523;

import java.io.*;
import java.util.*;
import java.util.function.*;

public class ObjectStreamSpliterator<T> implements Spliterator<T> {
    private final ObjectInputStream in;
    private final Predicate<T> isEOF;

    public ObjectStreamSpliterator(ObjectInputStream in) {
        this(in, Objects::isNull);
    }

    public ObjectStreamSpliterator(ObjectInputStream in, Predicate<T> isEOF) {
        this.in = in;
        this.isEOF = isEOF;
    }

    public boolean tryAdvance(Consumer<? super T> action) {
        throw new UnsupportedOperationException("TODO");
    }

    public Spliterator<T> trySplit() {
        // never split
        throw new UnsupportedOperationException("TODO");
    }

    public long estimateSize() {
        throw new UnsupportedOperationException("TODO");
    }

    public int characteristics() {
        throw new UnsupportedOperationException("TODO");
    }
}
