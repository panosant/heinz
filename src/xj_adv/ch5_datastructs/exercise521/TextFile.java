package xj_adv.ch5_datastructs.exercise521;

import java.io.*;
import java.util.*;

/**
 * Instead of first building up a list and then iterating through
 * it, we iterate through the file. Be careful that hasNext()
 * does not unnecessarily advance the reading of the file.
 *
 * @author Heinz Kabutz
 * @see TextFileTest
 */
public class TextFile implements Iterable<String> {
    private final String filename;

    public TextFile(String filename) throws FileNotFoundException {
        this.filename = filename;
    }

    public Iterator<String> iterator() {
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        }

        // Create an anonymous inner class for the iterator
        return new Iterator<String>() {
            private String nextLine;
            private boolean islineParsed = false;

            /**
             * Calling hasNext() repeatedly should not advance the file.
             */
            public boolean hasNext() {
                try {
                    // Read lines from file
                    if (!islineParsed) {
                        nextLine = in.readLine();
                        islineParsed = true;
                    }

                    // null means you have reached the end
                    if (nextLine == null) {
                        try {
                            in.close();
                        } catch (IOException ex) {
                            throw new UncheckedIOException(ex);
                        }
                    }
                } catch (IOException ioe) {
                    islineParsed = true;
                    nextLine = null;
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return nextLine != null;
            }

            public String next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                islineParsed = false;

                return nextLine;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
