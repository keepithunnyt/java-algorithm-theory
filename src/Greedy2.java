import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Greedy2 {
  private static Map<Integer, Integer> map = new HashMap<>();

  public static List<Integer[]> getMaxValue(int capacity) {
    List<Integer[]> result = new ArrayList<>();
    List<Map.Entry<Integer, Integer>> sortedMap = map.entrySet().stream()
        .sorted((c1, c2) -> c1.getValue() != c2.getValue() ? Integer.compare(c2.getValue(), c1.getValue())
            : Integer.compare(c1.getKey(), c2.getKey())

        ).collect(Collectors.toList());
    for (Map.Entry<Integer, Integer> data : sortedMap) {
      if (capacity >= data.getKey()) {
        capacity -= data.getKey();
        result.add(new Integer[] { data.getKey(), data.getValue() });
      } else {
        break;
      }
    }
    return result;
  }

  public static void main(String[] args) {
    map.put(10, 10);
    map.put(15, 12);
    map.put(20, 10);
    map.put(25, 8);
    map.put(30, 5);
    List<Integer[]> result = getMaxValue(30);
    result.stream().forEach(list -> {
      for (int data : list) {
        System.out.print(data + " ");
      }
      System.out.println();
    });
  }
}
