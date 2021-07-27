import java.util.Arrays;

public class SelectSort {
  public static void main(String[] args) throws Exception {
    int[] list = new int[] { 2, 8, 10, 3, 99, 45, 13, 56, 100, 23, 7, 12 };
    for (int i = 0; i < list.length - 1; i++) {
      int minIdx = -1;
      for (int j = i + 1; j < list.length; j++) {
        if (minIdx == -1) {
          if (list[i] > list[j]) {
            minIdx = j;
          }
        } else if (list[minIdx] > list[j]) {
          minIdx = j;
        }
      }
      if (minIdx != -1) {
        int temp = list[i];
        list[i] = list[minIdx];
        list[minIdx] = temp;
      }
    }
    Arrays.stream(list).forEach(f -> {
      System.out.print(f + " ");
    });
    System.out.println();
  }
}
