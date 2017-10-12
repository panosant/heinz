package xj_adv.ch2_io.solution222;

import org.junit.*;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

import static org.junit.Assert.*;
import static xj_adv.ch2_io.solution222.GenerateLargeFile.*;

public class BigLineSpliteratorTest {
    @Test
    public void testSplit() {
        checkSplit(1_030_000_000L, 4_000_434_000L, (long) 1_000_000);
        checkSplit(1_000_000_000L, 4_000_000_000L, (long) 1_000_000);
        checkSplit(0, Integer.MAX_VALUE * 4L, Integer.MAX_VALUE);
    }

    private void checkSplit(long lo, long hi, long CHUNK_SIZE) {

        int numberOfChunks = (int) Math.ceil(((double) (hi - lo)) / CHUNK_SIZE);

        System.out.println("(hi-lo) = " + (hi - lo));
        System.out.println("numberOfChunks = " + numberOfChunks);
        assertTrue(numberOfChunks * CHUNK_SIZE >= (hi - lo));
        assertTrue(numberOfChunks * CHUNK_SIZE - (hi - lo) < CHUNK_SIZE);

        long remainingBytes = (hi - lo);
        long[] sizes = new long[numberOfChunks];
        long[] positions = new long[numberOfChunks];

        for (int i = 0; i < numberOfChunks; i++) {
            positions[i] = i * CHUNK_SIZE + lo;
            sizes[i] = i < numberOfChunks - 1 ? CHUNK_SIZE : remainingBytes;
            remainingBytes -= CHUNK_SIZE;
        }

//    for (int i = 0; i < numberOfChunks; i++) {
//      System.out.println(i + ": position = " + positions[i] + ", size = " + sizes[i]);
//    }
        long totalSize = 0;
        for (long size : sizes) {
            totalSize += size;
        }
        assertEquals(totalSize, (hi - lo));

        long previousPosition = positions[0];
        for (int i = 1; i < numberOfChunks; i++) {
            assertEquals(lo + i * CHUNK_SIZE, positions[i]);
        }
    }

    @Test
    public void testSmallByteBuffer() {
        String data = "264 5876 3439899615 5173008066 636 7647 168 \n" +
                "\n" +
                "168 10069752 7 251 187607589 154519 3 6812 \n" +
                "3833 10926 254 6109 126040302 21 1542944722 914 13 \n" +
                "3487 167 3644696 4235269 40022 1506960 \n";
        ByteBuffer test = makeByteBuffer(data);

        List<LineInFile> allSer = StreamSupport.stream(
                new BigLineSpliterator(test), false)
                .filter(l -> Pattern.compile("").matcher(l.getLine()).find())
                .collect(Collectors.toList());
        System.out.println(allSer);
        checkAll(allSer);

        test.rewind();
        List<LineInFile> allPar = StreamSupport.stream(
                new BigLineSpliterator(test), true)
                .filter(l -> Pattern.compile("").matcher(l.getLine()).find())
                .collect(Collectors.toList());
        System.out.println(allPar);
        checkAll(allPar);
    }

    private static void checkAll(List<LineInFile> all) {
        Iterator<LineInFile> it = all.iterator();
        assertEquals("0:264 5876 3439899615 5173008066 636 7647 168 ", it.next().toString());
        assertEquals("45:", it.next().toString());
        assertEquals("46:168 10069752 7 251 187607589 154519 3 6812 ", it.next().toString());
        assertEquals("90:3833 10926 254 6109 126040302 21 1542944722 914 13 ", it.next().toString());
        assertEquals("142:3487 167 3644696 4235269 40022 1506960 ", it.next().toString());
    }

    private static void checkAllOffset(List<LineInFile> all) {
        Iterator<LineInFile> it = all.iterator();
        assertEquals("210000:264 5876 3439899615 5173008066 636 7647 168 ", it.next().toString());
        assertEquals("210045:", it.next().toString());
        assertEquals("210046:168 10069752 7 251 187607589 154519 3 6812 ", it.next().toString());
        assertEquals("210090:3833 10926 254 6109 126040302 21 1542944722 914 13 ", it.next().toString());
        assertEquals("210142:3487 167 3644696 4235269 40022 1506960 ", it.next().toString());
    }

    @Test
    public void testSmallByteBufferWithLargeOffset() {
        String data = "264 5876 3439899615 5173008066 636 7647 168 \n" +
                "\n" +
                "168 10069752 7 251 187607589 154519 3 6812 \n" +
                "3833 10926 254 6109 126040302 21 1542944722 914 13 \n" +
                "3487 167 3644696 4235269 40022 1506960 \n";
        ByteBuffer small = makeByteBuffer(data);

        ByteBuffer test = ByteBuffer.allocate(10 * 1024 * 1024);
        fillWithJunk(test);
        int lo = test.position();
        test.put(small.array());
        int hi = test.position();
        fillWithJunk(test);

        System.out.println("lo = " + lo);
        System.out.println("hi = " + hi);
        List<LineInFile> allSer = StreamSupport.stream(
                new BigLineSpliterator(test, lo, hi), false)
                .filter(l -> Pattern.compile("").matcher(l.getLine()).find())
                .collect(Collectors.toList());
        System.out.println(allSer);
        checkAllOffset(allSer);

        test.rewind();
        List<LineInFile> allPar = StreamSupport.stream(
                new BigLineSpliterator(test, lo, hi), true)
                .filter(l -> Pattern.compile("").matcher(l.getLine()).find())
                .collect(Collectors.toList());
        System.out.println(allPar);
        checkAllOffset(allPar);
    }

    private void fillWithJunk(ByteBuffer test) {
        for (int i = 0; i < 30000; i++) {
            test.put((byte) '0');
            test.put((byte) '1');
            test.put((byte) '2');
            test.put((byte) '3');
            test.put((byte) '4');
            test.put((byte) '5');
            test.put((byte) '\n');
        }
    }

    @Test
    public void testLotsOfEmptyLines() {
        String data =
                "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "168 \n" +
                        "\n" +
                        "\n" +
                        "\n";
        ByteBuffer test = makeByteBuffer(data);

        test.rewind();
        List<LineInFile> mostlyEmptySer = StreamSupport.stream(new BigLineSpliterator(test), false)
                .filter(l -> Pattern.compile("").matcher(l.getLine()).find())
                .collect(Collectors.toList());
        checkMostlyEmpty(mostlyEmptySer);
        System.out.println(mostlyEmptySer);

        test.rewind();
        List<LineInFile> mostlyEmptyPar = StreamSupport.stream(new BigLineSpliterator(test), true)
                .filter(l -> Pattern.compile("").matcher(l.getLine()).find())
                .collect(Collectors.toList());
        checkMostlyEmpty(mostlyEmptyPar);
        System.out.println(mostlyEmptyPar);
    }

    private void checkMostlyEmpty(List<LineInFile> list) {
        assertEquals(11, list.size());
        Iterator<LineInFile> it = list.iterator();
        assertEquals("0:", it.next().toString());
        assertEquals("1:", it.next().toString());
        assertEquals("2:", it.next().toString());
        assertEquals("3:", it.next().toString());
        assertEquals("4:", it.next().toString());
        assertEquals("5:", it.next().toString());
        assertEquals("6:", it.next().toString());
        assertEquals("7:168 ", it.next().toString());
        assertEquals("12:", it.next().toString());
        assertEquals("13:", it.next().toString());
        assertEquals("14:", it.next().toString());
    }

    @Test
    public void testLargeByteBuffer() throws IOException {
        File file = new File(BIGFILE_TXT);
        if (!file.exists()) {
            GenerateLargeFile.generate(BIGFILE_TXT, LINES_IN_BIGFILE);
        }
        RandomAccessFile raf = new RandomAccessFile(
                BIGFILE_TXT, "r"
        );
        FileChannel fc = raf.getChannel();

        Pattern patt = Pattern.compile("12345*");

        long time = System.currentTimeMillis();
        List<LineInFile> ser = StreamSupport.stream(new BigLineSpliterator(fc), false)
                .filter(l -> patt.matcher(l.getLine()).find())
                .collect(Collectors.toList());
        time = System.currentTimeMillis() - time;
        System.out.println("time = " + time);
        checkLarge(ser);

        time = System.currentTimeMillis();
        List<LineInFile> par = StreamSupport.stream(new BigLineSpliterator(fc), true)
                .filter(l -> patt.matcher(l.getLine()).find())
                .collect(Collectors.toList());
        time = System.currentTimeMillis() - time;
        System.out.println("time = " + time);
        checkLarge(par);
    }

    private void checkLarge(List<LineInFile> lines) {
        assertEquals(4681, lines.size());
        Iterator<LineInFile> it = lines.iterator();
        assertEquals("9377:211 2070041400 314615977 12347 11851344 10 88 93349 124085744 7443 124127 130 564 291133282 ", it.next().toString());
        assertEquals("19828:411517446 203 1586816117 11234471 50 644897252 14465 451420128 122398655 13645447 142 663598863 7021 7 105026150 3067952 8004592 ", it.next().toString());
        assertEquals("47314:1234 49277472 ", it.next().toString());
        assertEquals("91173:80226707 225551 4130 1182910505 909468 7 144223 3378741 419512344 29207065 7761 29 382836 63 919 ", it.next().toString());
        assertEquals("111661:13635624 4953 325589854 231765 6 194498535 512349 4511 300 29650727 959 ", it.next().toString());
        assertEquals("121450:326627508 12399968615 49 1793 24 15 7 26 6170 4688813209 673465 12457630769 1267 765 2780837 12344 4 4519533 ", it.next().toString());
        assertEquals("129936:185618 7 1126 23794871 2915 110 59254808 36137 10 4153065 10123485267 ", it.next().toString());
        assertEquals("154602:1759956 4862 32505243 19 4475176 41049 7743174 101170388 101112 3941899549 3565 26841487 23970874 48172427 664934 1508 460 12340266590 ", it.next().toString());
        assertEquals("156941:1671 5 11605570 19 71234 ", it.next().toString());
        assertEquals("165004:123460 149294266 1698568417 6 3316978 225 115391 ", it.next().toString());
    }

    @Test
    public void testBiggerByteBuffer() throws IOException {
        String filename = BIGGERFILE_TXT;
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("Skipping bigger test");
            return;
        }
        RandomAccessFile raf = new RandomAccessFile(
                filename, "r"
        );
        FileChannel fc = raf.getChannel();

        Pattern patt = Pattern.compile("12345*");

        long time = System.currentTimeMillis();
        long serCount = StreamSupport.stream(new BigLineSpliterator(fc), false)

                .unordered()
                .filter(l -> patt.matcher(l.getLine()).find()).count();
        time = System.currentTimeMillis() - time;
        System.out.println("time = " + time);
        assertEquals(476253, serCount);

        time = System.currentTimeMillis();
        long parCount = StreamSupport.stream(new BigLineSpliterator(fc), true).unordered()
                .filter(l -> patt.matcher(l.getLine()).find()).count();
        time = System.currentTimeMillis() - time;
        System.out.println("time = " + time);
        assertEquals(476253, parCount);
    /*
    Normal grep:
real	2m14.403s
user	2m12.808s
sys	0m1.730s
     */
    }

    private ByteBuffer makeByteBuffer(String data) {
        ByteBuffer test = ByteBuffer.allocate(data.length() + 100);
        for (int i = 0; i < data.length(); i++) {
            test.put((byte) data.charAt(i));
        }
        test.flip();
        return test;
    }
}
