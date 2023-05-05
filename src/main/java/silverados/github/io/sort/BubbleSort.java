package silverados.github.io.sort;

public class BubbleSort implements SortAlgorithm {
    @Override
    public void sort(int[] arr, int low, int high) {
        for (int i = high - 1; i >= low; i--) {
            boolean swapped = false;
            for (int j = low + 1; j <= i; j++) {
                if (arr[j] < arr[j - 1]) {
                    swap(arr, j, j - 1);
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }
    }
}
