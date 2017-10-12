package xj_adv.ch5_datastructs.solution523;

import java.io.*;
import java.util.*;
import java.util.function.*;

public class ObjectStreamSpliterator<T> implements Spliterator<T> {
    private boolean finished = false;
    private final ObjectInputStream in;
    private final Predicate<T> isEOF;

    public ObjectStreamSpliterator(ObjectInputStream in) {
        this(in, Objects::isNull);
    }

    public ObjectStreamSpliterator(ObjectInputStream in, Predicate<T> isEOF) {
        this.in = in;
        this.isEOF = isEOF;
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        if (finished) return false;
        try {
            T next = (T) in.readObject();
            if (isEOF.test(next)) {
                finished = true;
                return false;
            }
            action.accept((T) next);
            return true;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Spliterator<T> trySplit() {
        // never split
        return null;
    }

    @Override
    public long estimateSize() {
        return Long.MAX_VALUE;
    }

    @Override
    public int characteristics() {
        return ORDERED;
    }
}
