import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class DFS {
  private static Map<Character, char[]> graph = new HashMap<>();
  private static List<Character> order = new ArrayList<>();

  public static void dfs(char startNode) {
    Set<Character> visited = new HashSet<>();
    Stack<Character> needVisit = new Stack<>();
    needVisit.add(startNode);
    while (needVisit.size() != 0) {
      char node = needVisit.pop();
      if (!visited.contains(node)) {
        order.add(node);
        visited.add(node);
        for (char d : graph.get(node)) {
          needVisit.add(d);
        }
      }
    }
  }

  public static void main(String[] args) {
    graph.put('A', new char[] { 'B', 'C' });
    graph.put('B', new char[] { 'A', 'D' });
    graph.put('C', new char[] { 'A', 'G', 'H', 'I' });
    graph.put('D', new char[] { 'B', 'E', 'F' });
    graph.put('E', new char[] { 'D' });
    graph.put('F', new char[] { 'D' });
    graph.put('G', new char[] { 'C' });
    graph.put('H', new char[] { 'C' });
    graph.put('I', new char[] { 'C', 'J' });
    graph.put('J', new char[] { 'I' });
    dfs('A');
    System.out.println(order);
  }
}
