package silverados.github.io.sort;

public class QuickSort implements SortAlgorithm {

    @Override
    public void sort(int[] arr, int low, int high) {
        if (low + 1 >= high) {
            return;
        }

        int pivot = low + (int) (Math.random() * (high - low));
        swap(arr, high - 1, pivot);

        int[] p = partition(arr, low, high);
        sort(arr, low, p[0]);
        sort(arr, p[1], high);
    }

    private int[] partition(int[] arr, int low, int high) {
        int less = low - 1;
        int more = high - 1;
        while (low < more) {
            if (arr[low] < arr[high - 1]) {
                swap(arr, low++, ++less);
            } else if (arr[low] > arr[high - 1]) {
                swap(arr, low, --more);
            } else {
                low++;
            }
        }
        swap(arr, more, high - 1);
        return new int[]{less + 1, more};
    }
}
