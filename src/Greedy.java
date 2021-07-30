import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Greedy {
  private static Integer[] coinList = { 1, 100, 50, 500 };

  public static List<Integer[]> minCoinCount(int value) {
    List<Integer[]> result = new ArrayList<>();
    Arrays.sort(coinList, Collections.reverseOrder());
    for (int coin : coinList) {
      if (value == 0) {
        break;
      }
      Integer[] temp = new Integer[] { coin, value / coin };
      result.add(temp);
      value -= coin * temp[1];
      System.out.println(temp[1]);
    }
    return result;
  }

  public static void main(String[] args) {
    List<Integer[]> result = minCoinCount(4721);
    result.stream().forEach(list -> {
      for (int d : list) {
        System.out.print(d + " ");
      }
      System.out.println();
    });
  }
}
