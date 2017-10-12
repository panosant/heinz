package xj_adv.ch2_io.solution222;

/**
 * DO NOT CHANGE.
 */
public class LineInFile implements CharSequence {
    private final CharSequence line;
    private final long positionInFile;

    public LineInFile(long positionInFile, CharSequence line) {
        this.positionInFile = positionInFile;
        this.line = line;
    }

    public CharSequence getLine() {
        return line;
    }

    @Override
    public int length() {
        return line.length();
    }

    @Override
    public char charAt(int index) {
        return line.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return null;
    }

    public String toString() {
        return positionInFile + ":" + line;
    }
}
