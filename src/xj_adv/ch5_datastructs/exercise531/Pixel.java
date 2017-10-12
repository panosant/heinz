package xj_adv.ch5_datastructs.exercise531;

import java.util.*;

public class Pixel {
    private final int x, y;

    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pixel pixel = (Pixel) o;
        return Objects.equals(x, pixel.x) &&
                Objects.equals(y, pixel.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
