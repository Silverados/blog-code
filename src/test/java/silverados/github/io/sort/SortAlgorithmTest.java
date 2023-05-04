package silverados.github.io.sort;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SortAlgorithmTest {

    public static final int MIN = -50;
    public static final int MAX = 50;
    public static final int COUNT = 200;

    public static int[] generateArray() {
        int[] arr = new int[COUNT];
        Random random = new Random();
        for (int i = 0; i < COUNT; i++) {
            arr[i] = random.nextInt(MAX - MIN) + MIN;
        }
        return arr;
    }

    @Test
    void sort() {
        int[] arr = generateArray();
        SortAlgorithm sortAlgorithm = new InsertSort();
        sortAlgorithm.sort(arr);
        assertTrue(sortAlgorithm.isSorted(arr));
    }

}