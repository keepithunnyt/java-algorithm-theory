import java.util.Arrays;

public class InsertSort {
  public static void main(String[] args) {
    int[] list = new int[] { 6, 5, 3, 1, 8, 7, 2, 4 };
    for (int i = 0; i < list.length - 1; i++) {
      for (int j = i; j > -1; j--) {
        int checkIdx = j + 1;
        if (list[checkIdx] > list[i]) {
          break;
        } else if (list[checkIdx] < list[j]) {
          int temp = list[j];
          list[j] = list[checkIdx];
          list[checkIdx] = temp;
        } else {
          break;
        }
      }
    }
    Arrays.stream(list).forEach(f -> {
      System.out.print(f + " ");
    });
    System.out.println();
  }
}
