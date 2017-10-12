package xj_adv.ch5_datastructs.solution522;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

/**
 * Instead of writing our own Iterators, we can simply delegate
 * to the Files.line(Path) method, which works similarly to our
 * exercise 5.2.1.
 *
 * @author Heinz Kabutz
 * @see TextFileTest
 */
public class TextFile implements Iterable<String> {
    private final Path path;

    public TextFile(String filename) throws NoSuchFileException {
        this.path = Paths.get(filename);
        if (!path.toFile().exists())
            throw new NoSuchFileException(filename);
    }

    /**
     * Convert the stream from Files.lines() to an iterator.
     * If an IOException occurs, make it unchecked and throw it
     */
    public Iterator<String> iterator() {
        try {
            Stream<String> stream = Files.lines(path);
            Iterator<String> it = stream.iterator();
            return new Iterator<String>() {
                public boolean hasNext() {
                    return it.hasNext();
                }

                public String next() {
                    return it.next();
                }

                protected void finalize() throws Throwable {
                    stream.close();
                    super.finalize();
                }
            };
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}