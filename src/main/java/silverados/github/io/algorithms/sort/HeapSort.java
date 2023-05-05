package silverados.github.io.algorithms.sort;

public class HeapSort implements SortAlgorithm {
    @Override
    public void sort(int[] arr, int low, int high) {
        for (int i = low; i < high; i++) {
            heapInsert(arr, low, i);
        }

        int size = high - low;
        swap(arr, low, --size);

        while (size > 0) {
            heapify(arr, low, size);
            swap(arr, 0, --size);
        }
    }

    private void heapify(int[] arr, int i, int size) {
        int left = 2 * i + 1;
        while (left < size) {
            int largest = left + 1 < size && arr[left + 1] > arr[left] ? left + 1 : left;
            largest = arr[largest] > arr[i] ? largest : i;
            if (largest == i) {
                return;
            }

            swap(arr, largest, i);
            i = largest;
            left = 2 * i + 1;
        }
    }

    private void heapInsert(int[] arr, int low, int i) {
        while ((i - 1) / 2 >= low && arr[i] > arr[(i - 1) / 2]) {
            swap(arr, i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }
}
