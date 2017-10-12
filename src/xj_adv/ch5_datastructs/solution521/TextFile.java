package xj_adv.ch5_datastructs.solution521;

import java.io.*;
import java.util.*;

/**
 * Instead of first building up a list and then iterating through
 * it, we iterate through the file. Be careful that hasNext()
 * does not unnecessarily advance the reading of the file.
 *
 * @author Heinz Kabutz
 */
public class TextFile implements Iterable<String> {
    private final String filename;

    public TextFile(String filename) {
        this.filename = filename;
    }

    public Iterator<String> iterator() {
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
        return new Iterator<String>() {
            private String nextLine;
            private boolean lineReadFromFile = false;

            /**
             * Calling hasNext() repeatedly should not advance the file.
             */
            public boolean hasNext() {
                try {
                    if (!lineReadFromFile) {
                        nextLine = in.readLine();
                        lineReadFromFile = true;
                    }
                    if (nextLine == null) {
                        try {
                            in.close();
                        } catch (IOException ex) {
                            throw new UncheckedIOException(ex);
                        }
                    }
                    return nextLine != null;
                } catch (IOException e) {
                    lineReadFromFile = true;
                    nextLine = null;
                    try {
                        in.close();
                    } catch (IOException ex) {
                        e.addSuppressed(ex);
                    }
                    throw new UncheckedIOException(e);
                }
            }

            private void closeIfEnd() {
                if (nextLine == null) {
                    try {
                        in.close();
                    } catch (IOException ex) {
                        throw new UncheckedIOException(ex);
                    }
                }
            }

            public String next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                lineReadFromFile = false;
                return nextLine;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
