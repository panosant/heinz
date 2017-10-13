package xj_adv.ch2_io.exercise221;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class LittleEndianWrite {
    public static final String FILENAME = "data.out";

    public static void main(String... args) throws IOException {
        long l1 = 0x87654321ABCDEF00L;
        short s1 = 0x0033;
        int i1 = 0x12345678;
        long l2 = 0x0000111122223333L;
        char c1 = 0xFABC;

        // Get the FileChannel from a RandomAccessFile
        try (RandomAccessFile file = new RandomAccessFile(FILENAME, "rw");
             FileChannel channel = file.getChannel()
        ) {
            // Get a mapped byte buffer from the channel
            int length = (Long.SIZE + Short.SIZE + Integer.SIZE + Long.SIZE + Character.SIZE) / 8;  // # bytes the buffer will write
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, length);
            // for (int i = 0; i < length; i++) buffer.put((byte) 42);

            // Set the buffer to use Little Endian order
            buffer.order(ByteOrder.LITTLE_ENDIAN);

            // write the various values and close the channel
            buffer.putLong(l1);
            buffer.putShort(s1);
            buffer.putInt(i1);
            buffer.putLong(l2);
            buffer.putChar(c1);
        }
    }
}
