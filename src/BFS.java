import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class BFS {
  private static Map<Character, char[]> graph = new HashMap<>();
  private static List<Character> order = new ArrayList<>();

  public static void bfs(char startNode) {
    Queue<Character> needVisit = new LinkedList<>();
    List<Character> visited = new ArrayList<>();
    needVisit.add(startNode);
    while (needVisit.size() != 0) {
      char node = needVisit.poll();
      if (!visited.contains(node)) {
        visited.add(node);
        order.add(node);
        for (char c : graph.get(node)) {
          needVisit.add(c);
        }
      }
    }
  }

  public static void main(String[] agrs) {
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
    bfs('A');
    System.out.println(order);
  }
}
