package xj_adv.ch2_io.exercise211;

import java.io.*;
import java.util.*;

public class ComplexClass implements Serializable {

    private final static long serialVersionUID = 1;

    private int i;
    private long l;
    private String s;
    private boolean b;
    private double d;
    private double dd;
    private Collection<Integer> list = new ArrayList<>();

    public ComplexClass(int i, long l, String s, boolean b, double d, double dd) {
        list = new ArrayList<>();
        this.i = i;
        this.l = l;
        this.s = s;
        this.b = b;
        this.d = d;
        this.dd = dd;
    }

    public void add(int val) {
        list.add(val);
    }

    /**
     *  Old serializaation performane: cpu=6779,user=6740,elapsed=12587,memory=774.0MB,bytes streamed=125.4MB
     *  New serialization performance: cpu=4963,user=4940,elapsed=5506,memory=545.2MB,bytes streamed=104.8MB
     *  New serialization performance:
     */
    private void writeObject(ObjectOutputStream ous) throws IOException {
        //ous.defaultWriteObject();
        ous.writeInt(i);
        ous.writeLong(l);
        ous.writeUTF(s);
        ous.writeBoolean(b);
        ous.writeDouble(d);
        ous.writeDouble(dd);

        ous.write(list.size());
        for (Integer i : list) {
            ous.writeInt(i);
        }
    }

    public void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        // TODO: In case of DeserializeTest!
        // ois.defaultReadObject();

        i = ois.readInt();
        l = ois.readLong();

        if (ois.readBoolean()) {
            s = ois.readUTF();
        }


        b = ois.readBoolean();
        if (ois.readBoolean()) {
            d = ois.readDouble();
        }

        dd = ois.readDouble();

        list = new ArrayList<>();
        int size = ois.readInt();
        for (int j = 0 ; j < size ; j++) {
            list.add(ois.readInt());
        }
    }

    public void writeRaw(DataOutput data) throws IOException {
        //ous.defaultWriteObject();
        data.writeInt(i);
        data.writeLong(l);
        data.writeUTF(s);
        data.writeBoolean(b);
        data.writeDouble(d);
        data.writeDouble(dd);

        data.write(list.size());
        for (Integer i : list) {
            data.writeInt(i);
        }
    }

    public void readRaw(DataInput data) throws IOException {
        // TODO: In case of DeserializeTest!
        // ois.defaultReadObject();

        i = data.readInt();
        l = data.readLong();
        s = data.readUTF();
        b = data.readBoolean();
        d = data.readDouble();
        dd = data.readDouble();

        list = new ArrayList<>();
        int size = data.readInt();
        for (int j = 0 ; j < size ; j++) {
            list.add(data.readInt());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComplexClass that = (ComplexClass) o;

        if (i != that.i) return false;
        if (l != that.l) return false;
        if (b != that.b) return false;
        if (Double.compare(that.d, d) != 0) return false;
        if (Double.compare(that.dd, dd) != 0) return false;
        if (s != null ? !s.equals(that.s) : that.s != null) return false;
        if (list != null ? !list.equals(that.list) : that.list != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = i;
        result = 31 * result + (int) (l ^ (l >>> 32));
        result = 31 * result + (s != null ? s.hashCode() : 0);
        result = 31 * result + (b ? 1 : 0);
        temp = Double.doubleToLongBits(d);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(dd);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (list != null ? list.hashCode() : 0);
        return result;
    }
}
