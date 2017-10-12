package xj_adv.ch2_io.solution222;

import java.io.*;
import java.math.*;
import java.util.*;

public class GenerateLargeFile {
    public static final String BIGFILE_TXT = "bigfile.txt";
    public static final String BIGGERFILE_TXT = "biggerfile.txt";
    public static final int LINES_IN_BIGFILE = 1_000_000;
    public static final int LINES_IN_BIGGERFILE = 100_000_000;

    public static void main(String... args) throws IOException {
        generate(BIGFILE_TXT, LINES_IN_BIGFILE);
//        generate(BIGGERFILE_TXT, 100 * LINES_IN_BIGFILE);
    }

    public static void generate(String filename, int linesInFile) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            PrintWriter p = new PrintWriter(
                    new BufferedWriter(
                            new FileWriter(file), 1_000_000));
            Random r = new Random(0); // always the same result
            for (int i = 0; i < linesInFile; i++) {
                int wordCount = r.nextInt(20);
                for (int j = 0; j < wordCount; j++) {
                    p.print(new BigInteger(r.nextInt(32) + 3, r));
                    p.print(' ');
                }
                p.println();
            }
            p.close();
        }
    }
}
