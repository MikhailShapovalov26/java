import java.util.Iterator;
import java.util.Random;


public class Randoms implements Iterable<Integer> {
    protected int min, max;
    protected Random random = new Random();

    public Randoms(int min, int max) {
        this.min = min;
        this.max = max + 1;

    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            @Override
            public boolean hasNext() {
                return true; //так как вечный
            }

            @Override
            public Integer next() {
                return random.nextInt(max - min) + min;

            };
        };
    }
}