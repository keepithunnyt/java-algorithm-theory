import java.util.Arrays;

public class MergeSort {
  static int[] dataList = new int[] { 8, 2, 10, 5, 4, 3, 99, 12, 56, 23 };
  static int[] temp = new int[10];

  public static void mergeSort(int l, int r) {
    int mid = (l + r) / 2;
    int rp = mid + 1;
    int lp = l;
    int idx = lp;
    while (lp <= mid && rp <= r) {
      temp[idx++] = dataList[dataList[lp] < dataList[rp] ? lp++ : rp++];
    }
    if (lp > mid) {
      for (int i = idx; i <= r; i++) {
        temp[i] = dataList[rp++];
      }
    } else if (rp > r) {
      for (int i = idx; i <= r; i++) {
        temp[idx] = dataList[lp++];
      }
    }
    for (int i = l; i <= r; i++) {
      dataList[i] = temp[i];
    }
  }

  // 0, 3 -> 1 -> 0,1 | 2,3
  // - 0,1 -> 0 -> 0,0
  // - 2,3 -> 2 -> 3,3
  public static void splitNMerge(int l, int r) {
    if (l == r) {
      return;
    }
    int mid = (l + r) / 2;
    splitNMerge(l, mid);
    splitNMerge(mid + 1, r);
    mergeSort(l, r);
  }

  public static void main(String[] args) {
    splitNMerge(0, dataList.length - 1);
    Arrays.stream(dataList).forEach(f -> {
      System.out.print(f + " ");
    });
    System.out.println();
  }
}
