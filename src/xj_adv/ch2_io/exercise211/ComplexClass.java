package xj_adv.ch2_io.exercise211;

import java.io.*;
import java.util.*;

public class ComplexClass implements Serializable {
    private final static long serialVersionUID = 1;
    private final int i;
    private final long l;
    private final String s;
    private final boolean b;
    private final Double d;
    private final Float f;
    private final Collection<Integer> v = new Vector<>();

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
}
