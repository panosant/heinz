package xj_adv.ch5_datastructs.solution523;

import org.junit.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

import static org.junit.Assert.*;

public class ObjectStreamSpliteratorTest {
    @Test
    public void testEmptyObjectInputStream() throws IOException {
        ObjectInputStream oin = createObjectInputStream();
        Stream<Object> stream = StreamSupport.stream(
                new ObjectStreamSpliterator<>(oin), false
        );
        stream.forEach(s -> { // should be empty
            throw new AssertionError();
        });
    }

    @Test
    public void testEOFPredicateObjectInputStream() throws IOException {
        Stream<String> stream = StreamSupport.stream(
                new ObjectStreamSpliterator<>(createObjectInputStream("John", "Anton", "Heinz"), Objects::isNull), false
        );
        List<String> expected = Arrays.asList("John", "Anton", "Heinz");
        assertEquals(expected, stream.collect(Collectors.toList()));

        stream = StreamSupport.stream(
                new ObjectStreamSpliterator<>(createObjectInputStream("John", "Anton", "Heinz"), s -> s.equals("Heinz")), false
        );
        assertEquals(Arrays.asList("John", "Anton"), stream.collect(Collectors.toList()));

        stream = StreamSupport.stream(
                new ObjectStreamSpliterator<>(createObjectInputStream("John", "Anton", "Heinz"), s -> s.equals("Anton")), false
        );
        assertEquals(Arrays.asList("John"), stream.collect(Collectors.toList()));

        stream = StreamSupport.stream(
                new ObjectStreamSpliterator<>(createObjectInputStream("John", "Anton", "Heinz"), s -> s.equals("John")), false
        );
        assertEquals(Arrays.asList(), stream.collect(Collectors.toList()));
    }

    @Test
    public void testStringObjectInputStream() throws IOException {
        ObjectInputStream oin = createObjectInputStream("John", "Anton", "Heinz");
        Stream<String> stream = StreamSupport.stream(
                new ObjectStreamSpliterator<>(oin), false
        );
        List<String> expected = Arrays.asList("John", "Anton", "Heinz");
        assertEquals(expected, stream.collect(Collectors.toList()));
    }

    @Test
    public void testSortedStringObjectInputStream() throws IOException {
        ObjectInputStream oin = createObjectInputStream("John", "Anton", "Heinz");
        Stream<String> stream = StreamSupport.stream(
                new ObjectStreamSpliterator<>(oin), false
        );
        List<String> expected = Arrays.asList("Anton", "Heinz", "John");
        assertEquals(expected, stream.sorted().collect(Collectors.toList()));
    }

    @Test
    public void testUpperCaseStringObjectInputStream() throws IOException {
        ObjectInputStream oin = createObjectInputStream("John", "Anton", "Heinz");
        Stream<String> stream = StreamSupport.stream(
                new ObjectStreamSpliterator<>(oin), false
        );
        List<String> expected = Arrays.asList("JOHN", "ANTON", "HEINZ");
        assertEquals(expected, stream.map(String::toUpperCase).collect(Collectors.toList()));
    }

    @Test
    public void testCharacteristics() throws IOException {
        ObjectInputStream oin = createObjectInputStream("John", "Anton", "Heinz");
        ObjectStreamSpliterator<Object> spliterator = new ObjectStreamSpliterator<>(oin);
        assertEquals(16, spliterator.characteristics());
    }

    @Test
    public void testTrySplit() throws IOException {
        ObjectInputStream oin = createObjectInputStream("John", "Anton", "Heinz");
        ObjectStreamSpliterator<Object> spliterator = new ObjectStreamSpliterator<>(oin);
        assertNull(spliterator.trySplit());
    }

    @Test
    public void testEstimateSize() throws IOException {
        ObjectInputStream oin = createObjectInputStream("John", "Anton", "Heinz");
        ObjectStreamSpliterator<Object> spliterator = new ObjectStreamSpliterator<>(oin);
        assertEquals(Long.MAX_VALUE, spliterator.estimateSize());
    }

    @Test
    public void testTryAdvancePastEnd() throws IOException {
        ObjectInputStream oin = createObjectInputStream("John", "Anton", "Heinz");
        ObjectStreamSpliterator<Object> spliterator = new ObjectStreamSpliterator<>(oin);
        AtomicInteger actionCalls = new AtomicInteger();
        for (int i = 0; i < 100; i++) {
            spliterator.tryAdvance(s -> actionCalls.incrementAndGet());
        }
        assertFalse(spliterator.tryAdvance(s -> { }));
    }

    @SafeVarargs
    private static <T extends Serializable> ObjectInputStream createObjectInputStream(
            T... objects) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        for (T object : objects) {
            oos.writeObject(object);
        }
        oos.writeObject(null);
        oos.close();
        return new ObjectInputStream(
                new ByteArrayInputStream(baos.toByteArray())
        );
    }
}
