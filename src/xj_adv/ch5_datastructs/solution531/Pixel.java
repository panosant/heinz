package xj_adv.ch5_datastructs.solution531;

public class Pixel {
    private final int x, y;

    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int hashCode() {
        return x * 3001 + y;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pixel pixel = (Pixel) o;

        if (x != pixel.x) return false;
        if (y != pixel.y) return false;

        return true;
    }
}