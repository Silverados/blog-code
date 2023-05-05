package silverados.github.io.algorithms.sort;

public interface SortAlgorithm {
    default void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        sort(arr, 0, arr.length);
    }

    /**
     * @param low the index of the first element, inclusive, to be sorted
     * @param high the index of the last element, exclusive, to be sorted
     */
    void sort(int[] arr, int low, int high);

    default void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    default boolean isSorted(int[] arr) {
        if (arr == null || arr.length < 2) {
            return true;
        }

        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) {
                return false;
            }
        }
        return true;
    }
}
