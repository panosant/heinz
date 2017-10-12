package xj_adv.ch5_datastructs.solution521c;

import org.junit.*;

import java.io.*;
import java.util.*;

import static org.junit.Assert.*;

public class TextFileTest {
    private static final String TEST_FILE_NAME = "testFile.txt";
    private final List<String> names = new ArrayList<>();
    private File file;

    @Before
    public void setUp() throws IOException {
        file = new File(TEST_FILE_NAME);

        names.clear();
        names.add("John");
        names.add("Anton");
        names.add("Dirk");
        names.add("Heinz");

        PrintWriter out = new PrintWriter(file);
        names.forEach(out::println);
        out.close();
    }

    @After
    public void tearDown() {
        file.delete();
    }

    @Test
    public void testRemove() throws IOException {
        int i = 0;
        TextFile tf = new TextFile(TEST_FILE_NAME);

        for (String name : tf) {
            System.out.println("::: " + name);
            assertEquals(names.get(i++), name);
        }

        // testing remove()
        for (Iterator<String> it = tf.iterator(); it.hasNext(); ) {
            String name = it.next();
            if ("Dirk".equals(name)) {
                it.remove();
                System.out.println("removing dirk");
            }
        }

        names.remove("Dirk");

        i = 0;
        for (String name : tf) {
            System.out.println("::: " + name);
            assertEquals(names.get(i++), name);
        }
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testConcurrentModificationException() throws IOException {
        TextFile tf = new TextFile(TEST_FILE_NAME);

        Iterator<String> broken = tf.iterator();

        for (Iterator<String> it = tf.iterator(); it.hasNext(); ) {
            String name = it.next();
            if ("Dirk".equals(name)) {
                it.remove();
            }
        }
        broken.next();
    }

    @Test(expected = IllegalStateException.class)
    public void testBadRemove() throws IOException {
        TextFile tf = new TextFile(TEST_FILE_NAME);
        Iterator<String> it = tf.iterator();
        it.remove();
    }

    @Test
    public void testFirstRemove() throws IOException {
        int i = 0;
        TextFile tf = new TextFile(TEST_FILE_NAME);

        // testing remove() on first element
        Iterator<String> it = tf.iterator();
        it.next();
        it.remove();

        names.remove("John");

        i = 0;
        for (String name : tf) {
            System.out.println("::: " + name);
            assertEquals(names.get(i++), name);
        }
    }

    @Test
    public void testLastRemove() throws IOException {
        int i = 0;
        TextFile tf = new TextFile(TEST_FILE_NAME);

        // testing remove() on first element
        Iterator<String> it = tf.iterator();
        while (it.hasNext()) {
            it.next();
        }
        it.remove();

        names.remove("Heinz");

        i = 0;
        for (String name : tf) {
            System.out.println("::: " + name);
            assertEquals(names.get(i++), name);
        }
    }

    @Test
    public void testAllRemove() throws IOException {
        TextFile tf = new TextFile(TEST_FILE_NAME);

        // testing remove() on first element
        Iterator<String> it = tf.iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }

        it = tf.iterator();
        assertFalse(it.hasNext());
    }

    @Test
    public void testRead() throws FileNotFoundException {
        TextFile tf = new TextFile(
                "src/xj_adv/ch5_datastructs/exercise521/"
                        + "hello.txt");
        Iterator<String> it = tf.iterator();
        assertEquals("hello", it.next());
        assertTrue(it.hasNext());
        assertTrue(it.hasNext());
        assertTrue(it.hasNext());
        assertEquals("goodbye", it.next());
        assertEquals("world", it.next());
        assertFalse(it.hasNext());

        try {
            it.next();
            fail("at the end, next() should throw an exception");
        } catch (NoSuchElementException expected) {
        } catch (Exception unexpected) {
            fail("expected a NoSuchElementException, not a " +
                    unexpected.getClass().getSimpleName());
        }
    }
}
