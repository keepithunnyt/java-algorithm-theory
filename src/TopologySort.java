import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TopologySort {
  // 정점의 갯수+1 만큼 생성
  static int SIZE = 8;
  static List<Integer>[] dataList = new ArrayList[SIZE];
  static int[] indegreeList = new int[SIZE];
  static Queue<Integer> q = new LinkedList<>();

  public static void init() {
    for (int i = 1; i < SIZE; i++) {
      dataList[i] = new ArrayList<>();
    }
    dataList[1].add(2);
    indegreeList[2]++;
    dataList[1].add(5);
    indegreeList[5]++;
    dataList[2].add(3);
    indegreeList[3]++;
    dataList[3].add(4);
    indegreeList[4]++;
    dataList[4].add(6);
    indegreeList[6]++;
    dataList[5].add(6);
    indegreeList[6]++;
    dataList[6].add(7);
    indegreeList[7]++;
  }

  public static void addOnIndegreeZero() {
    for (int i = 1; i < SIZE; i++) {
      if (indegreeList[i] == 0) {
        q.add(i);
      }
    }
  }

  public static void main(String[] args) {
    List<Integer> result = new ArrayList<>();
    init();
    addOnIndegreeZero();
    while (!q.isEmpty()) {
      int polledData = q.poll();
      result.add(polledData);
      for (int data : dataList[polledData]) {
        indegreeList[data] -= 1;
        if (indegreeList[data] == 0) {
          q.add(data);
        }
      }
    }
    System.out.println(result);
  }
}
