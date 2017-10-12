package xj_adv.ch5_datastructs.solution521d;

import org.junit.*;

import java.io.*;
import java.util.*;

import static org.junit.Assert.*;

public class TextFileTest2 {
    public static void main(String... args) throws IOException {
        TextFile tf = new TextFile("src/"
                + "xj_adv/ch5_datastructs/solution521d/TextFileTest2.java");
        for (String s : tf) {
            System.out.println(">>> " + s);
        }
        TextFileTest2 tft = new TextFileTest2();
        tft.testRead();
    }

    @Test
    public void testRead() throws IOException {
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
