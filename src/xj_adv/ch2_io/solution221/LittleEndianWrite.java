package xj_adv.ch2_io.solution221;

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

        try (
                RandomAccessFile file = new RandomAccessFile(FILENAME, "rw");
                FileChannel channel = file.getChannel();
        ) {
            int size = (Long.SIZE + Short.SIZE + Integer.SIZE +
                    Long.SIZE + Character.SIZE) / 8;
            MappedByteBuffer buffer = channel
                    .map(FileChannel.MapMode.READ_WRITE, 0, size);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            buffer.putLong(l1);
            buffer.putShort(s1);
            buffer.putInt(i1);
            buffer.putLong(l2);
            buffer.putChar(c1);
        }
    }
}
