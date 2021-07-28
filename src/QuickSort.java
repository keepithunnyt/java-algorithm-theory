import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuickSort {
  public static List<Integer> qSort(List<Integer> dataList) {
    if (dataList.size() <= 1) {
      return dataList;
    }
    int pivotIdx = dataList.size() / 2;
    int pivotValue = dataList.get(pivotIdx);
    dataList.remove(pivotIdx);
    List<Integer> left = new ArrayList<>();
    List<Integer> right = new ArrayList<>();
    for (int data : dataList) {
      if (data < pivotValue) {
        left.add(data);
      } else {
        right.add(data);
      }
    }
    List<Integer> resultL = qSort(left);
    List<Integer> resultR = qSort(right);
    List<Integer> res = new ArrayList<>();
    for (int data : resultL) {
      res.add(data);
    }
    res.add(pivotValue);
    for (int data : resultR) {
      res.add(data);
    }
    return res;
  }

  public static void main(String[] args) {
    List<Integer> test = new ArrayList<>(Arrays.asList(8, 2, 10, 5, 4, 3, 99, 12, 56, 23));
    System.out.println(qSort(test));
  }
}
