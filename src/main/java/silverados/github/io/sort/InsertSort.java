package silverados.github.io.sort;

public class InsertSort implements SortAlgorithm {
    @Override
    public void sort(int[] arr, int low, int high) {
        for (int i = low + 1; i < high; i++) {
            int base = arr[i];
            int j = i - 1;
            for (; j >= 0 && arr[j] > base; j--) {
                arr[j + 1] = arr[j];
            }

            if (j >= 0 && j != i - 1) {
                arr[j] = base;
            }
        }
    }
}
