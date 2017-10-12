package xj_adv.ch2_io.solution222;

import java.io.*;
import java.nio.channels.*;
import java.util.*;
import java.util.stream.*;

public class FileChannelSupport {
    private final FileChannel fc;

    public FileChannelSupport(FileChannel fc) {
        this.fc = fc;
    }

    public Stream<LineInFile> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    public Stream<LineInFile> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }

    public Spliterator<LineInFile> spliterator() {
        try {
            return new BigLineSpliterator(fc);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
