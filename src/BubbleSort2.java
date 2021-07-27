import java.util.Arrays;

public class BubbleSort2 {
  public static void main(String[] args) {
    int[] list = new int[] { 6, 5, 3, 1, 8, 7, 2, 4 };
    for (int i = 0; i < list.length - 1; i++) {
      for (int j = 0; j < list.length - 1 - i; j++) {
        if (list[j] > list[j + 1]) {
          int temp = list[j];
          list[j] = list[j + 1];
          list[j + 1] = temp;
        }
      }
    }
    Arrays.stream(list).forEach(f -> {
      System.out.print(f + " ");
    });
    System.out.println();
  }
}
