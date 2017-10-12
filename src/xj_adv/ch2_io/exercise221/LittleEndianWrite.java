package xj_adv.ch2_io.exercise221;

import java.io.*;

public class LittleEndianWrite {
    public static final String FILENAME = "data.out";

    public static void main(String... args) throws IOException {
        long l1 = 0x87654321ABCDEF00L;
        short s1 = 0x0033;
        int i1 = 0x12345678;
        long l2 = 0x0000111122223333L;
        char c1 = 0xFABC;

        RandomAccessFile file = new RandomAccessFile(FILENAME, "rw");

        // Get the FileChannel from a RandomAccessFile

        // Get a mapped byte buffer from the channel

        // Set the buffer to use Little Endian order

        // write the various values and close the channel
    }
}
