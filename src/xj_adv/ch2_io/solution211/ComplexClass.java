package xj_adv.ch2_io.solution211;

import java.io.*;
import java.util.*;

public class ComplexClass implements Serializable {
    private final static long serialVersionUID = 2;
    private int i;
    private long l;
    private String s;
    private boolean b;
    private Double d;
    private Float f;
    private Collection<Integer> v = new ArrayList<>();

    public ComplexClass(int i, long l, String s, boolean b, Double d, float f) {
        this.i = i;
        this.l = l;
        this.s = s;
        this.b = b;
        this.d = d;
        this.f = f;
    }

    public void add(int val) {
        v.add(val);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComplexClass that = (ComplexClass) o;

        if (b != that.b) return false;
        if (i != that.i) return false;
        if (l != that.l) return false;
        if (d != null ? !d.equals(that.d) : that.d != null) return false;
        if (f != null ? !f.equals(that.f) : that.f != null) return false;
        if (s != null ? !s.equals(that.s) : that.s != null) return false;
        if (v != null ? !v.equals(that.v) : that.v != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = i;
        result = 31 * result + (int) (l ^ (l >>> 32));
        result = 31 * result + (s != null ? s.hashCode() : 0);
        result = 31 * result + (b ? 1 : 0);
        result = 31 * result + (d != null ? d.hashCode() : 0);
        result = 31 * result + (f != null ? f.hashCode() : 0);
        result = 31 * result + (v != null ? v.hashCode() : 0);
        return result;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeInt(i);
        out.writeLong(l);
        out.writeBoolean(s != null);
        if (s != null) {
            out.writeBoolean(s.length() > 0xFFFF);
            if (s.length() > 0xFFFF) {
                out.writeObject(s);
            } else {
                out.writeUTF(s);
            }
        }
        out.writeBoolean(b);
        out.writeBoolean(d != null);
        if (d != null)
            out.writeDouble(d);
        out.writeFloat(f);
        out.writeInt(v.size());
        for (int i : v) {
            out.writeInt(i);
        }
    }

    private void readObject(ObjectInputStream in) throws IOException,
            ClassNotFoundException {
        i = in.readInt();
        l = in.readLong();
        if (in.readBoolean()) {
            if (in.readBoolean()) {
                s = (String) in.readObject();
            } else {
                s = in.readUTF();
            }
        }
        b = in.readBoolean();
        if (in.readBoolean()) {
            d = in.readDouble();
        }
        f = in.readFloat();
        v = new Vector<>();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            v.add(in.readInt());
        }
    }
}
