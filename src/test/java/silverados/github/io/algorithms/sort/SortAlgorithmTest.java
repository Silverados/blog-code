package silverados.github.io.algorithms.sort;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SortAlgorithmTest {

    public static final int MIN = -500;
    public static final int MAX = 500;
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
    void insertSort() {
        int[] arr = generateArray();
        SortAlgorithm sortAlgorithm = new InsertSort();
        sortAlgorithm.sort(arr);
        assertTrue(sortAlgorithm.isSorted(arr));
    }

    @Test
    void selectSort() {
        int[] arr = generateArray();
        SortAlgorithm sortAlgorithm = new SelectSort();
        sortAlgorithm.sort(arr);
        assertTrue(sortAlgorithm.isSorted(arr));
    }

    @Test
    void bubbleSort() {
        int[] arr = generateArray();
        SortAlgorithm sortAlgorithm = new BubbleSort();
        sortAlgorithm.sort(arr);
        assertTrue(sortAlgorithm.isSorted(arr));
    }

    @Test
    void mergeSort() {
        int[] arr = generateArray();
        SortAlgorithm sortAlgorithm = new MergeSort();
        sortAlgorithm.sort(arr);
        assertTrue(sortAlgorithm.isSorted(arr));
    }

    @Test
    void quickSort() {
        int[] arr = generateArray();
        SortAlgorithm sortAlgorithm = new QuickSort();
        sortAlgorithm.sort(arr);
        assertTrue(sortAlgorithm.isSorted(arr));
    }

    @Test
    void heapSort() {
        int[] arr = generateArray();
        SortAlgorithm sortAlgorithm = new HeapSort();
        sortAlgorithm.sort(arr);
        assertTrue(sortAlgorithm.isSorted(arr));
    }
}