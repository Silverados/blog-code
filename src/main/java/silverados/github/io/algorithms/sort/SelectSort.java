package silverados.github.io.algorithms.sort;

public class SelectSort implements SortAlgorithm{
    @Override
    public void sort(int[] arr, int low, int high) {
        for (int i = low; i < high; i++) {
            int minIndex = i;
            for (int j = i + 1; j < high; j++) {
                if (arr[minIndex] > arr[j]) {
                    minIndex = j;
                }
            }
            swap(arr, minIndex, i);
        }
    }
}
