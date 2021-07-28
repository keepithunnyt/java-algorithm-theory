import java.util.ArrayList;
import java.util.List;

public class fibonacci {
  static List<Integer> cache = new ArrayList<>();

  public static int recursive(int num) {
    if (num == 0) {
      return 0;
    } else if (num == 1) {
      return 1;
    }
    return recursive(num - 1) + recursive(num - 2);
  }

  public static int dp(int num) {
    int idx = 2;
    while (idx != num + 1) {
      cache.add(cache.get(idx - 1) + cache.get(idx - 2));
      idx += 1;
    }
    return cache.get(num);
  }

  public static void main(String[] args) {
    cache.add(0);
    cache.add(1);
    System.out.println(recursive(9));
    System.out.println(dp(9));
  }
}
