package xj_adv.ch5_datastructs.solution521b;

import org.junit.*;

import java.io.*;
import java.util.*;

import static org.junit.Assert.*;

public class TextFileTest {
    public static void main(String... args) throws IOException {
        System.out.println("Windows test file:");
        System.out.println("------------------");
        TextFile tfw = new TextFile(
                "src/xj_adv/ch5_datastructs/solution521b/"
                        + "TestFileWindows");
        for (String line : tfw) {
            System.out.println("> " + line);
        }

        System.out.println("Unix test file:");
        System.out.println("---------------");
        TextFile tfu = new TextFile(
                "src/xj_adv/ch5_datastructs/solution521b/"
                        + "TestFileUnix");
        for (String line : tfu) {
            System.out.println("> " + line);
        }
        TextFileTest tft = new TextFileTest();
        tft.testRead();
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
